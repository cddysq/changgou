package com.changgou.service.goods.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.goods.dao.BrandMapper;
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
    @Transactional
    public void addBrand(Brand brand) {
        brandMapper.insertSelective( brand );
    }


    @Override
    @Transactional
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective( brand );
    }

    @Override
    @Transactional
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
        if (ObjectUtil.isNotEmpty( searchMap.get( "name" ) )) {
            criteria.andLike( "name", "%" + searchMap.get( "name" ) + "%" );
        }
        // 品牌的首字母
        if (ObjectUtil.isNotEmpty( searchMap.get( "letter" ) )) {
            criteria.andEqualTo( "letter", searchMap.get( "letter" ) );
        }
        return example;
    }
}