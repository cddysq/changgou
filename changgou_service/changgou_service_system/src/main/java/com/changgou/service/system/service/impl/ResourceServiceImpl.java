package com.changgou.service.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.service.system.dao.ResourceMapper;
import com.changgou.service.system.service.ResourceService;
import com.changgou.system.pojo.Resource;
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
 * @Date: 2020/2/14 19:40
 * @Description: 资源服务实现
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> findAll() {
        return resourceMapper.selectAll();
    }

    @Override
    public Resource findById(Integer id) {
        return resourceMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addResource(Resource resource) {
        resourceMapper.insertSelective( resource );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateResource(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective( resource );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        resourceMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Resource> findList(@NotNull Map<String, Object> searchMap) {
        return resourceMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Resource> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> resourceMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Resource.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // res_key
            String res_key = Convert.toStr( searchMap.get( "res_key" ) );
            if (StrUtil.isNotEmpty( res_key )) {
                criteria.andLike( "res_key", "%" + res_key + "%" );
            }
            // res_name
            String res_name = Convert.toStr( searchMap.get( "res_name" ) );
            if (StrUtil.isNotEmpty( res_name )) {
                criteria.andLike( "res_name", "%" + res_name + "%" );
            }
            // id
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andEqualTo( "id", id );
            }
            String parentId = Convert.toStr( searchMap.get( "parentId" ) );
            // parent_id
            if (StrUtil.isNotEmpty( parentId )) {
                criteria.andEqualTo( "parentId", parentId );
            }
        }
        return example;
    }
}