package com.changgou.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.user.dao.AddressMapper;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
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
 * @Date: 2020/2/26 10:55
 * @Description: 地址服务实现
 **/
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }

    @Override
    public Address findById(Integer id) {
        return addressMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(Address address) {
        addressMapper.insertSelective( address );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(Address address) {
        addressMapper.updateByPrimaryKeySelective( address );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        addressMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Address> findList(@NotNull Map<String, Object> searchMap) {
        return addressMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Address> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> addressMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Address.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            String username = Convert.toStr( searchMap.get( "username" ) );
            if (StrUtil.isNotEmpty( username )) {
                criteria.andEqualTo( "username", username );
            }
            // 省
            String provinceId = Convert.toStr( searchMap.get( "provinceid" ) );
            if (StrUtil.isNotEmpty( provinceId )) {
                criteria.andLike( "provinceid", "%" + provinceId + "%" );
            }
            // 市
            String cityId = Convert.toStr( searchMap.get( "cityid" ) );
            if (StrUtil.isNotEmpty( cityId )) {
                criteria.andLike( "cityid", "%" + cityId + "%" );
            }
            // 县/区
            String areaId = Convert.toStr( searchMap.get( "areaid" ) );
            if (StrUtil.isNotEmpty( areaId )) {
                criteria.andLike( "areaid", "%" + areaId + "%" );
            }
            // 电话
            String phone = Convert.toStr( searchMap.get( "phone" ) );
            if (StrUtil.isNotEmpty( phone )) {
                criteria.andLike( "phone", "%" + phone + "%" );
            }
            // 详细地址
            String address = Convert.toStr( searchMap.get( "address" ) );
            if (StrUtil.isNotEmpty( address )) {
                criteria.andLike( "address", "%" + address + "%" );
            }
            // 联系人
            String contact = Convert.toStr( searchMap.get( "contact" ) );
            if (StrUtil.isNotEmpty( contact )) {
                criteria.andLike( "contact", "%" + contact + "%" );
            }
            // 是否是默认 1默认 0否
            String isDefault = Convert.toStr( searchMap.get( "isDefault" ) );
            if (StrUtil.isNotEmpty( isDefault )) {
                criteria.andEqualTo( "isDefault", isDefault );
            }
            // 别名
            String alias = Convert.toStr( searchMap.get( "alias" ) );
            if (StrUtil.isNotEmpty( alias )) {
                criteria.andLike( "alias", "%" + alias + "%" );
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