package com.changgou.service.goods.service;

import com.changgou.goods.pojo.Sku;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 22:16
 * @Description: Sku服务
 **/
public interface SkuService {
    /**
     * 查询所有Sku
     *
     * @return Sku集合
     */
    List<Sku> findAll();

    /**
     * 根据id查询Sku数据
     *
     * @param id Sku id
     * @return Sku数据
     */
    Sku findById(String id);

    /**
     * 新增Sku
     *
     * @param sku Sku数据
     */
    void addSku(Sku sku);

    /**
     * 更新Sku信息
     *
     * @param sku Sku数据
     */
    void updateSku(Sku sku);

    /**
     * 根据id删除Sku信息
     *
     * @param id Skuid
     */
    void deleteById(String id);

    /**
     * 多条件搜索Sku数据
     *
     * @param searchMap 条件集合
     * @return Sku数据
     */
    List<Sku> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Sku> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 扣减库存，增加销量
     *
     * @param username 用户名
     */
    void decrCount(String username);

    /**
     * 回滚库存，扣减销量
     *
     * @param skuId  商品id
     * @param number 商品数量
     */
    void resumeStockNumber(String skuId, Integer number);
}