package com.changgou.service.goods.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
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
 * 规格服务逻辑
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:03
 **/
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
    @Transactional(rollbackFor = Exception.class)
    public void addSpec(Spec spec) {
        specMapper.insertSelective( spec );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpec(Spec spec) {
        specMapper.updateByPrimaryKey( spec );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
            String name = Convert.toStr( searchMap.get( "name" ) );
            if (StrUtil.isNotEmpty( name )) {
                criteria.andLike( "name", "%" + name + "%" );
            }
            // 规格选项
            String options = Convert.toStr( searchMap.get( "options" ) );
            if (StrUtil.isNotEmpty( options )) {
                criteria.andLike( "options", "%" + options + "%" );
            }
            // ID
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andEqualTo( "id", id );
            }
            // 排序
            String seq = Convert.toStr( searchMap.get( "seq" ) );
            if (StrUtil.isNotEmpty( seq )) {
                criteria.andEqualTo( "seq", seq );
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