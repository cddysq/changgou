package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.common.util.ChineseCharToEn;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.constant.BrandStatusEnum;
import com.changgou.service.goods.exception.GoodsException;
import com.changgou.service.goods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/24 14:30
 * @Description: 品牌服务逻辑
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBrand(Brand brand) {
        if (brandMapper.selectOne( brand ) != null) {
            throw new GoodsException( BrandStatusEnum.BRAND_REPEAT );
        }
        String brandLetter = brand.getLetter();
        //品牌首字母不存在时，自动获取品牌名第一个首字母
        if (StrUtil.isEmpty( brandLetter )) {
            ChineseCharToEn chineseCharToEn = ChineseCharToEn.getInstance();
            String firstCapitalLetter = chineseCharToEn.getFirstCapitalLetter( brand.getName() );
            brand.setLetter( firstCapitalLetter );
        }
        brandMapper.insertSelective( brand );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective( brand );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        brandMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Brand> findList(@NotNull Map<String, Object> searchMap) {
        return brandMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Brand> findPage(Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> brandMapper.selectAll() );
    }

    @Override
    public Page<Brand> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> brandMapper.selectByExample( getExample( searchMap ) ) );
    }

    @Override
    public List<Map<String, Object>> findBrandListByCategoryName(String categoryName) {
        return brandMapper.findBrandListByCategoryName( categoryName );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Brand.class );
        Example.Criteria criteria = example.createCriteria();
        // 品牌名称
        String name = Convert.toStr( searchMap.get( "name" ) );
        if (StrUtil.isNotEmpty( name )) {
            criteria.andLike( "name", "%" + name + "%" );
        }

        // 品牌的首字母
        String letter = Convert.toStr( searchMap.get( "letter" ) );
        if (StrUtil.isNotEmpty( letter )) {
            criteria.andEqualTo( "letter", letter );
        }
        return example;
    }
}