package com.changgou.service.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.service.system.constant.AdminStatusEnum;
import com.changgou.service.system.dao.AdminMapper;
import com.changgou.service.system.exception.AdminException;
import com.changgou.service.system.service.AdminService;
import com.changgou.system.pojo.Admin;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        //用户存在禁止添加
        String loginName = admin.getLoginName();
        Admin oneAdmin = adminMapper.selectOne( Admin.builder().loginName( loginName ).build() );
        if (oneAdmin != null) {
            throw new AdminException( AdminStatusEnum.ADMIN_EXIST );
        }
        //用户名不存在正常添加
        bCryptPassword( admin );
        adminMapper.insertSelective( admin );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdmin(Admin admin) {
        bCryptPassword( admin );
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
        String loginName = admin.getLoginName();
        if (StrUtil.isEmpty( loginName )) {
            throw new AdminException( AdminStatusEnum.INSERT_NULL );
        }
        String password = admin.getPassword();
        if (StrUtil.isEmpty( password )) {
            throw new AdminException( AdminStatusEnum.INSERT_NULL );
        }
        //只使用户名查询信息进行详细判断
        Admin oneAdmin = adminMapper.selectOne( Admin.builder().loginName( loginName ).build() );
        if (oneAdmin == null) {
            return false;
        } else if (!"1".equals( oneAdmin.getStatus() )) {
            throw new AdminException( AdminStatusEnum.ADMIN_DISABLE );
        } else {
            //对密码进行校验 第一个参数为明文密码, 第二个参数为密文密码
            return BCrypt.checkpw( admin.getPassword(), oneAdmin.getPassword() );
        }
    }

    /**
     * 加密密码
     *
     * @param admin 用户信息
     */
    private void bCryptPassword(Admin admin) {
        //获取盐
        String salt = BCrypt.gensalt();
        //对用户的密码进行加密
        String newPassword = BCrypt.hashpw( admin.getPassword(), salt );
        admin.setPassword( newPassword );
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