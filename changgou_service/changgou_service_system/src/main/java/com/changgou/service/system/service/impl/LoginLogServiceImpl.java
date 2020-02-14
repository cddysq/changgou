package com.changgou.service.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.service.system.dao.LoginLogMapper;
import com.changgou.service.system.service.LoginLogService;
import com.changgou.system.pojo.LoginLog;
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
 * @Description: 登录日志服务实现
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public List<LoginLog> findAll() {
        return loginLogMapper.selectAll();
    }

    @Override
    public LoginLog findById(Integer id) {
        return loginLogMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLoginLog(LoginLog loginLog) {
        loginLogMapper.insertSelective( loginLog );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginLog(LoginLog loginLog) {
        loginLogMapper.updateByPrimaryKeySelective( loginLog );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        loginLogMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<LoginLog> findList(@NotNull Map<String, Object> searchMap) {
        return loginLogMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<LoginLog> findPage(Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> loginLogMapper.selectAll() );
    }

    @Override
    public Page<LoginLog> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> loginLogMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( LoginLog.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // login_name
            String loginName = Convert.toStr( searchMap.get( "login_name" ) );
            if (StrUtil.isNotEmpty( loginName )) {
                criteria.andLike( "login_name", "%" + loginName + "%" );
            }
            // ip
            String ip = Convert.toStr( searchMap.get( "ip" ) );
            if (StrUtil.isNotEmpty( ip )) {
                criteria.andLike( "ip", "%" + ip + "%" );
            }
            // browser_name
            String browserName = Convert.toStr( searchMap.get( "browser_name" ) );
            if (StrUtil.isNotEmpty( browserName )) {
                criteria.andLike( "browser_name", "%" + browserName + "%" );
            }
            // 地区
            String location = Convert.toStr( searchMap.get( "location" ) );
            if (StrUtil.isNotEmpty( location )) {
                criteria.andLike( "location", "%" + location + "%" );
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