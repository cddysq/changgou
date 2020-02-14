package com.changgou.service.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.service.system.dao.AdminMapper;
import com.changgou.service.system.service.AdminService;
import com.changgou.system.pojo.Admin;
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
 * @Description: 管理员服务实现
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }

    @Override
    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAdmin(Admin admin) {
        adminMapper.insertSelective( admin );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdmin(Admin admin) {
        adminMapper.updateByPrimaryKeySelective( admin );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        adminMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Admin> findList(@NotNull Map<String, Object> searchMap) {
        return adminMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Admin> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> adminMapper.selectByExample( getExample( searchMap ) ) );
    }

    @Override
    public boolean login(Admin admin) {
        return false;
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Admin.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            String loginName = Convert.toStr( searchMap.get( "loginName" ) );
            if (StrUtil.isNotEmpty( loginName )) {
                criteria.andLike( "loginName", "%" + loginName + "%" );
            }
            // 密码
            String password = Convert.toStr( searchMap.get( "password" ) );
            if (StrUtil.isNotEmpty( password )) {
                criteria.andLike( "password", "%" + password + "%" );
            }
            // 状态
            String status = Convert.toStr( searchMap.get( "status" ) );
            if (StrUtil.isNotEmpty( status )) {
                criteria.andEqualTo( "status", status );
            }
            // id
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andEqualTo( "id", id );
            }
        }
        return example;
    }
}