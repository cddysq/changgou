package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.changgou.goods.pojo.Spec;
import com.changgou.service.goods.dao.SpecMapper;
import com.changgou.service.goods.service.SpecService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/25 17:29
 * @Description: 规格服务逻辑
 */
@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional
    public void addSpec(Spec spec) {
        specMapper.insertSelective( spec );
    }

    @Override
    @Transactional
    public void updateSpec(Spec spec) {
        specMapper.updateByPrimaryKey( spec );
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        specMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Spec> findList(Map<String, Object> searchMap) {
        return specMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Spec> findPage(Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> specMapper.selectAll() );
    }

    @Override
    public Page<Spec> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> specMapper.selectByExample( getExample( searchMap ) ) );
    }

    @Override
    public List<Map<String, Object>> findSpecListByCategoryName(String categoryName) {
        List<Map<String, Object>> list = specMapper.findSpecListByCategoryName( categoryName );
        for (Map<String, Object> map : list) {
            String[] options = Convert.toStr( map.get( "options" ) ).split( "," );
            map.put( "options", options );
        }
        return list;
    }

    /**
     * 构建查询对象
     *
     * @param searchMap 查询条件
     * @return 条件对象
     */
    private Example getExample(Map<String, Object> searchMap) {
        Example example = new Example( Spec.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 名称
            if (ObjectUtil.isNotEmpty( searchMap.get( "name" ) )) {
                criteria.andLike( "name", "%" + searchMap.get( "name" ) + "%" );
            }
            // 规格选项
            if (ObjectUtil.isNotEmpty( searchMap.get( "options" ) )) {
                criteria.andLike( "options", "%" + searchMap.get( "options" ) + "%" );
            }
            // ID
            if (ObjectUtil.isNotEmpty( searchMap.get( "id" ) )) {
                criteria.andEqualTo( "id", searchMap.get( "id" ) );
            }
            // 排序
            if (ObjectUtil.isNotEmpty( searchMap.get( "seq" ) )) {
                criteria.andEqualTo( "seq", searchMap.get( "seq" ) );
            }
            // 模板ID
            if (ObjectUtil.isNotEmpty( searchMap.get( "templateId" ) )) {
                criteria.andEqualTo( "templateId", searchMap.get( "templateId" ) );
            }
        }
        return example;
    }
}