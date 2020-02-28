package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.goods.pojo.Sku;
import com.changgou.order.pojo.OrderItem;
import com.changgou.service.goods.constant.GoodsStatusEnum;
import com.changgou.service.goods.dao.SkuMapper;
import com.changgou.service.goods.exception.GoodsException;
import com.changgou.service.goods.service.SkuService;
import com.changgou.service.goods.util.Condition;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 22:26
 * @Description: sku服务实现
 **/
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Sku> findAll() {
        return skuMapper.selectAll();
    }

    @Override
    public Sku findById(String id) {
        return skuMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSku(Sku sku) {
        skuMapper.insertSelective( sku );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSku(Sku sku) {
        skuMapper.updateByPrimaryKeySelective( sku );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        skuMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Sku> findList(@NotNull Map<String, Object> searchMap) {
        return skuMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Sku> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> skuMapper.selectByExample( getExample( searchMap ) ) );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrCount(String username) {
        //1.获取购物车的数据
        List<OrderItem> orderItemList = redisTemplate.boundHashOps( "cart_" + username ).values();
        //2.循环扣减库存增加销量
        for (OrderItem orderItem : orderItemList) {
            int count = skuMapper.decrCount( orderItem );
            if (count <= 0) {
                throw new GoodsException( GoodsStatusEnum.ORDER_ERROR );
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
        Example example = new Example( Sku.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            Condition.share( searchMap, criteria );
            // SPUID
            String spuId = Convert.toStr( searchMap.get( "spuId" ) );
            if (StrUtil.isNotEmpty( spuId )) {
                criteria.andEqualTo( "spuId", spuId );
            }
            // 类目名称
            String categoryName = Convert.toStr( searchMap.get( "categoryName" ) );
            if (StrUtil.isNotEmpty( categoryName )) {
                criteria.andLike( "categoryName", "%" + categoryName + "%" );
            }
            // 品牌名称
            String brandName = Convert.toStr( searchMap.get( "brandName" ) );
            if (StrUtil.isNotEmpty( brandName )) {
                criteria.andLike( "brandName", "%" + brandName + "%" );
            }
            // 规格
            String spec = Convert.toStr( searchMap.get( "spec" ) );
            if (StrUtil.isNotEmpty( spec )) {
                criteria.andLike( "spec", "%" + spec + "%" );
            }
            // 商品状态 1-正常，2-下架，3-删除
            String status = Convert.toStr( searchMap.get( "status" ) );
            if (StrUtil.isNotEmpty( status )) {
                criteria.andEqualTo( "status", status );
            }
            // 价格（分）
            String price = Convert.toStr( searchMap.get( "price" ) );
            if (StrUtil.isNotEmpty( price )) {
                criteria.andEqualTo( "price", price );
            }
            // 库存数量
            String num = Convert.toStr( searchMap.get( "num" ) );
            if (StrUtil.isNotEmpty( num )) {
                criteria.andEqualTo( "num", num );
            }
            // 库存预警数量
            String alertNum = Convert.toStr( searchMap.get( "alertNum" ) );
            if (StrUtil.isNotEmpty( alertNum )) {
                criteria.andEqualTo( "alertNum", alertNum );
            }
            // 重量（克）
            String weight = Convert.toStr( searchMap.get( "weight" ) );
            if (StrUtil.isNotEmpty( weight )) {
                criteria.andEqualTo( "weight", weight );
            }
            // 类目ID
            String categoryId = Convert.toStr( searchMap.get( "categoryId" ) );
            if (StrUtil.isNotEmpty( categoryId )) {
                criteria.andEqualTo( "categoryId", categoryId );
            }
        }
        return example;
    }
}