package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.goods.pojo.*;
import com.changgou.service.goods.constant.GoodsStatusEnum;
import com.changgou.service.goods.dao.*;
import com.changgou.service.goods.exception.GoodsException;
import com.changgou.service.goods.service.SpuService;
import com.changgou.service.goods.util.Condition;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 22:26
 * @Description: Spu服务实现
 **/
@Service
public class SpuServiceImpl implements SpuService {
    private static Snowflake snowflake = IdUtil.createSnowflake( 1, 1 );
    private static final String START_USING = "0";
    private static final String FORBIDDEN = "1";
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    @Override
    public Goods findGoodsById(String id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey( id );

        Example example = new Example( Sku.class );
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo( "spuId", id );
        //根据spu id进行sku列表的查询
        List<Sku> skuList = skuMapper.selectByExample( example );

        return Goods.builder().spu( spu ).skuList( skuList ).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSpu(Goods goods) {
        // 1.获取 spu 设置参数
        Spu spu = goods.getSpu();
        //设置分布式id
        spu.setId( snowflake.nextIdStr() );
        //设置删除状态.
        spu.setIsDelete( "0" );
        //上架状态
        spu.setIsMarketable( "0" );
        //审核状态
        spu.setStatus( "0" );
        spuMapper.insertSelective( spu );
        // 2.获取 sku 设置参数
        this.saveSkuList( goods );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpu(Goods goods) {
        Spu spu = goods.getSpu();
        //修改spu
        spuMapper.updateByPrimaryKey( spu );
        //修改sku,先删后添
        Example example = new Example( Sku.class );
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo( "spuId", spu.getId() );
        skuMapper.deleteByExample( example );
        this.saveSkuList( goods );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Spu spu = spuMapper.selectByPrimaryKey( id );
        //判断当前商品是否处于下架状态
        if (!SpuServiceImpl.FORBIDDEN.equals( spu.getIsMarketable() )) {
            throw new GoodsException( GoodsStatusEnum.GOODS_NOT_OFFLINE );
        }
        //商品下架，进行逻辑删除
        spu.setIsDelete( "1" );
        spu.setStatus( "0" );
        spuMapper.updateByPrimaryKeySelective( spu );
    }

    @Override
    public List<Spu> findList(@NotNull Map<String, Object> searchMap) {
        return spuMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Spu> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> spuMapper.selectByExample( getExample( searchMap ) ) );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(String id) {
        //查询spu对象
        Spu spu = spuMapper.selectByPrimaryKey( id );
        //判断当前spu是否存在
        if (spu == null) {
            throw new GoodsException( GoodsStatusEnum.GOODS_VANISH );
        }
        //判断当前spu是否处于删除状态
        if (SpuServiceImpl.START_USING.equals( spu.getIsDelete() )) {
            throw new GoodsException( GoodsStatusEnum.THE_GOODS_ARE_BEING_DELETED );
        }
        //商品正常，修改审核状态为1,上下架状态为1
        spu.setStatus( "1" );
        spu.setIsMarketable( "1" );
        //执行修改操作
        spuMapper.updateByPrimaryKeySelective( spu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pull(String id) {
        Spu spu = spuMapper.selectByPrimaryKey( id );
        if (spu == null) {
            throw new GoodsException( GoodsStatusEnum.GOODS_VANISH );
        }
        if (SpuServiceImpl.START_USING.equals( spu.getIsDelete() )) {
            throw new GoodsException( GoodsStatusEnum.THE_GOODS_ARE_BEING_DELETED );
        }
        //商品正常,修改上下架状态为已下架(0)
        spu.setIsMarketable( "0" );
        spuMapper.updateByPrimaryKeySelective( spu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void put(String id) {
        Spu spu = spuMapper.selectByPrimaryKey( id );
        if (spu == null) {
            throw new GoodsException( GoodsStatusEnum.GOODS_VANISH );
        }
        //商品审核状态必须为已审核(1)
        if (!SpuServiceImpl.START_USING.equals( spu.getStatus() )) {
            throw new GoodsException( GoodsStatusEnum.GOODS_NOT_AUDITED );
        }
        //商品正常，上架处理
        spu.setIsMarketable( "1" );
        spuMapper.updateByPrimaryKeySelective( spu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restore(String id) {
        Spu spu = spuMapper.selectByPrimaryKey( id );
        //判断当前商品必须处于已删除状态
        if (!SpuServiceImpl.FORBIDDEN.equals( spu.getIsDelete() )) {
            throw new GoodsException( GoodsStatusEnum.GOODS_NOT_DELETE );
        }
        //修改相关的属性字段进行保存操作
        spu.setIsDelete( "0" );
        spu.setStatus( "0" );
        spuMapper.updateByPrimaryKeySelective( spu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void realDel(String id) {
        Spu spu = spuMapper.selectByPrimaryKey( id );
        //判断当前商品是否处于已删除状态
        if (!SpuServiceImpl.FORBIDDEN.equals( spu.getIsDelete() )) {
            throw new GoodsException( GoodsStatusEnum.GOODS_NOT_DELETE );
        }
        //执行删除操作
        spuMapper.deleteByPrimaryKey( id );
    }

    /**
     * 添加sku数据
     *
     * @param goods 商品信息
     */
    private void saveSkuList(Goods goods) {
        Spu spu = goods.getSpu();
        //查询分类对象
        Category category = categoryMapper.selectByPrimaryKey( spu.getCategory3Id() );
        //查询品牌对象
        Brand brand = brandMapper.selectByPrimaryKey( spu.getBrandId() );

        //设置品牌与分类的关联关系
        CategoryBrand categoryBrand = CategoryBrand.builder()
                .brandId( spu.getBrandId() )
                .categoryId( spu.getCategory3Id() ).build();
        //查询关联表
        int count = categoryBrandMapper.selectCount( categoryBrand );
        if (count == 0) {
            //品牌与分类还没有关联关系
            categoryBrandMapper.insert( categoryBrand );
        }

        //获取sku集合
        List<Sku> skuList = goods.getSkuList();
        if (ObjectUtil.isNotEmpty( skuList )) {
            //遍历sku集合,循环填充数据并添加到数据库中
            for (Sku sku : skuList) {
                //设置skuId
                sku.setId( snowflake.nextIdStr() );
                //设置sku规格数据
                String skuSpec = sku.getSpec();
                if (StrUtil.isEmpty( skuSpec )) {
                    sku.setSpec( "{}" );
                }
                //设置sku名称(spu名称+规格)
                StringBuilder spuName = new StringBuilder( spu.getName() );
                Map<String, String> specMap = JSON.parseObject( skuSpec, Map.class );
                if (ObjectUtil.isNotEmpty( specMap )) {
                    for (String value : specMap.values()) {
                        spuName.append( " " ).append( value );
                    }
                }
                sku.setName( spuName.toString() );
                //设置spuId
                sku.setSpuId( spu.getId() );
                //设置创建与修改时间
                //FIXME: 2020/2/16 18:04 此处时间应采用数据库时间函数
                sku.setCreateTime( new Date() );
                sku.setUpdateTime( new Date() );
                //设置商品分类id
                sku.setCategoryId( category.getId() );
                //设置商品分类名称
                sku.setCategoryName( category.getName() );
                //设置品牌名称
                sku.setBrandName( brand.getName() );

                //将sku添加到数据库
                skuMapper.insertSelective( sku );
            }
        }
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Spu.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            Condition.share( searchMap, criteria );
            // 副标题
            String caption = Convert.toStr( searchMap.get( "caption" ) );
            if (StrUtil.isNotEmpty( caption )) {
                criteria.andLike( "caption", "%" + caption + "%" );
            }
            // 售后服务
            String saleService = Convert.toStr( searchMap.get( "saleService" ) );
            if (StrUtil.isNotEmpty( saleService )) {
                criteria.andLike( "saleService", "%" + saleService + "%" );
            }
            // 介绍
            String introduction = Convert.toStr( searchMap.get( "introduction" ) );
            if (StrUtil.isNotEmpty( introduction )) {
                criteria.andLike( "introduction", "%" + introduction + "%" );
            }
            // 规格列表
            String specItems = Convert.toStr( searchMap.get( "specItems" ) );
            if (StrUtil.isNotEmpty( specItems )) {
                criteria.andLike( "specItems", "%" + specItems + "%" );
            }
            // 参数列表
            String paraItems = Convert.toStr( searchMap.get( "paraItems" ) );
            if (StrUtil.isNotEmpty( paraItems )) {
                criteria.andLike( "paraItems", "%" + paraItems + "%" );
            }
            // 是否上架
            String isMarketable = Convert.toStr( searchMap.get( "isMarketable" ) );
            if (StrUtil.isNotEmpty( isMarketable )) {
                criteria.andEqualTo( "isMarketable", isMarketable );
            }
            // 是否启用规格
            String isEnableSpec = Convert.toStr( searchMap.get( "isEnableSpec" ) );
            if (StrUtil.isNotEmpty( isEnableSpec )) {
                criteria.andEqualTo( "isEnableSpec", isEnableSpec );
            }
            // 是否删除
            String isDelete = Convert.toStr( searchMap.get( "isDelete" ) );
            if (StrUtil.isNotEmpty( isDelete )) {
                criteria.andEqualTo( "isDelete", isDelete );
            }
            // 审核状态
            String status = Convert.toStr( searchMap.get( "status" ) );
            if (StrUtil.isNotEmpty( status )) {
                criteria.andEqualTo( "status", status );
            }
            // 品牌ID
            String brandId = Convert.toStr( searchMap.get( "brandId" ) );
            if (StrUtil.isNotEmpty( brandId )) {
                criteria.andEqualTo( "brandId", brandId );
            }
            // 一级分类
            String category1Id = Convert.toStr( searchMap.get( "category1Id" ) );
            if (StrUtil.isNotEmpty( category1Id )) {
                criteria.andEqualTo( "category1Id", category1Id );
            }
            // 二级分类
            String category2Id = Convert.toStr( searchMap.get( "category2Id" ) );
            if (StrUtil.isNotEmpty( category2Id )) {
                criteria.andEqualTo( "category2Id", category2Id );
            }
            // 三级分类
            String category3Id = Convert.toStr( searchMap.get( "category3Id" ) );
            if (StrUtil.isNotEmpty( category3Id )) {
                criteria.andEqualTo( "category3Id", category3Id );
            }
            // 模板ID
            String templateId = Convert.toStr( searchMap.get( "templateId" ) );
            if (StrUtil.isNotEmpty( templateId )) {
                criteria.andEqualTo( "templateId", templateId );
            }
            // 运费模板id
            String freightId = Convert.toStr( searchMap.get( "freightId" ) );
            if (StrUtil.isNotEmpty( freightId )) {
                criteria.andEqualTo( "freightId", freightId );
            }
        }
        return example;
    }
}