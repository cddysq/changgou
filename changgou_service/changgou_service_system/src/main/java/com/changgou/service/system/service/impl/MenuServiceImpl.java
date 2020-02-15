package com.changgou.service.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.service.system.dao.MenuMapper;
import com.changgou.service.system.service.MenuService;
import com.changgou.system.pojo.Menu;
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
 * @Description: 菜单服务实现
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

    @Override
    public Menu findById(String id) {
        return menuMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(Menu menu) {
        menuMapper.insertSelective( menu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective( menu );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        menuMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Menu> findList(@NotNull Map<String, Object> searchMap) {
        return menuMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Menu> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> menuMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Menu.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 菜单ID
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andLike( "id", "%" + id + "%" );
            }
            // 菜单名称
            String name = Convert.toStr( searchMap.get( "name" ) );
            if (StrUtil.isNotEmpty( name )) {
                criteria.andLike( "name", "%" + name + "%" );
            }
            // 图标
            String icon = Convert.toStr( searchMap.get( "icon" ) );
            if (StrUtil.isNotEmpty( icon )) {
                criteria.andLike( "icon", "%" + icon + "%" );
            }
            // URL
            String url = Convert.toStr( searchMap.get( "url" ) );
            if (StrUtil.isNotEmpty( url )) {
                criteria.andLike( "url", "%" + url + "%" );
            }
            // 上级菜单ID
            String parentId = Convert.toStr( searchMap.get( "parent_id" ) );
            if (StrUtil.isNotEmpty( parentId )) {
                criteria.andLike( "parent_id", "%" + parentId + "%" );
            }
        }
        return example;
    }
}