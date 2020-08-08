package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
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
 * 分类服务实现
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:03
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
            // 分类名称
            String name = Convert.toStr( searchMap.get( "name" ) );
            if (StrUtil.isNotEmpty( name )) {
                criteria.andLike( "name", "%" + name + "%" );
            }
            // 是否显示
            String isShow = Convert.toStr( searchMap.get( "isShow" ) );
            if (StrUtil.isNotEmpty( isShow )) {
                criteria.andEqualTo( "isShow", isShow );
            }
            // 是否导航
            String isMenu = Convert.toStr( searchMap.get( "isMenu" ) );
            if (StrUtil.isNotEmpty( isMenu )) {
                criteria.andLike( "isMenu", "%" + isMenu + "%" );
            }
            // 分类ID
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andEqualTo( "id", id );
            }
            // 商品数量
            String goodsNum = Convert.toStr( searchMap.get( "goodsNum" ) );
            if (StrUtil.isNotEmpty( goodsNum )) {
                criteria.andEqualTo( "goodsNum", goodsNum );
            }
            // 排序
            String seq = Convert.toStr( searchMap.get( "seq" ) );
            if (StrUtil.isNotEmpty( seq )) {
                criteria.andEqualTo( "seq", seq );
            }
            // 上级ID
            String parentId = Convert.toStr( searchMap.get( "parentId" ) );
            if (StrUtil.isNotEmpty( parentId )) {
                criteria.andEqualTo( "parentId", parentId );
            }
            // 模板ID
            String templateId = Convert.toStr( searchMap.get( "templateId" ) );
            if (StrUtil.isNotEmpty( templateId )) {
                criteria.andEqualTo( "templateId", templateId );
            }
        }
        return example;
    }
}