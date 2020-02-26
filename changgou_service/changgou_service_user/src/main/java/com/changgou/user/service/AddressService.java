package com.changgou.user.service;


import com.changgou.user.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:41
 * @Description: 地址服务
 **/
public interface AddressService {
    /**
     * 查询所有地址
     *
     * @return 地址集合
     */
    List<Address> findAll();

    /**
     * 根据id查询地址数据
     *
     * @param id 地址id
     * @return 地址数据
     */
    Address findById(Integer id);

    /**
     * 新增地址
     *
     * @param address 地址数据
     */
    void addAddress(Address address);

    /**
     * 修改地址信息
     *
     * @param address 地址数据
     */
    void updateAddress(Address address);

    /**
     * 根据id删除地址信息
     *
     * @param id 地址id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索地址数据
     *
     * @param searchMap 条件集合
     * @return 地址数据
     */
    List<Address> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Address> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}