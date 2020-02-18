package com.changgou.service.goods.service.impl;

import com.changgou.goods.pojo.Category;
import com.changgou.service.goods.dao.CategoryMapper;
import com.changgou.service.goods.service.CategoryService;
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
 * @Date: 2020/2/15 22:26
 * @Description: 分类服务实现
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCategory(Category category) {
        categoryMapper.insertSelective( category );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective( category );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        categoryMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Category> findList(@NotNull Map<String, Object> searchMap) {
        return categoryMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Category> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> categoryMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Category.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            //TODO: 2020/2/18 21:46  代码优化

            // 分类名称
            if (searchMap.get( "name" ) != null && !"".equals( searchMap.get( "name" ) )) {
                criteria.andLike( "name", "%" + searchMap.get( "name" ) + "%" );
            }
            // 是否显示
            if (searchMap.get( "isShow" ) != null && !"".equals( searchMap.get( "isShow" ) )) {
                criteria.andEqualTo( "isShow", searchMap.get( "isShow" ) );
            }
            // 是否导航
            if (searchMap.get( "isMenu" ) != null && !"".equals( searchMap.get( "isMenu" ) )) {
                criteria.andLike( "isMenu", "%" + searchMap.get( "isMenu" ) + "%" );
            }
            // 分类ID
            if (searchMap.get( "id" ) != null) {
                criteria.andEqualTo( "id", searchMap.get( "id" ) );
            }
            // 商品数量
            if (searchMap.get( "goodsNum" ) != null) {
                criteria.andEqualTo( "goodsNum", searchMap.get( "goodsNum" ) );
            }
            // 排序
            if (searchMap.get( "seq" ) != null) {
                criteria.andEqualTo( "seq", searchMap.get( "seq" ) );
            }
            // 上级ID
            if (searchMap.get( "parentId" ) != null) {
                criteria.andEqualTo( "parentId", searchMap.get( "parentId" ) );
            }
            // 模板ID
            if (searchMap.get( "templateId" ) != null) {
                criteria.andEqualTo( "templateId", searchMap.get( "templateId" ) );
            }
        }
        return example;
    }
}