package com.changgou.service.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Spu服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:00
 **/
public interface SpuService {
    /**
     * 查询所有Spu
     *
     * @return Spu集合
     */
    List<Spu> findAll();

    /**
     * 根据ID查询SPU
     *
     * @param id Spu id
     * @return Spu信息
     */
    Spu findById(String id);

    /**
     * 根据id查询Spu和sku数据
     *
     * @param id Spu id
     * @return Spu和sku数据集合
     */
    Goods findGoodsById(String id);

    /**
     * 新增Spu
     *
     * @param goods Spu与Sku关联数据
     */
    void addSpu(Goods goods);

    /**
     * 更新Spu信息
     *
     * @param goods Spu与Sku关联数据
     */
    void updateSpu(Goods goods);

    /**
     * 根据id删除Spu信息，逻辑删除
     *
     * @param id Spuid
     */
    void deleteById(String id);

    /**
     * 多条件搜索Spu数据
     *
     * @param searchMap 条件集合
     * @return Spu数据
     */
    List<Spu> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Spu> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 商品审核并自动上架
     *
     * @param id 商品id
     */
    void audit(String id);

    /**
     * 商品下架
     *
     * @param id 商品id
     */
    void pull(String id);

    /**
     * 商品上架
     *
     * @param id 商品id
     */
    void put(String id);

    /**
     * 还原商品
     *
     * @param id 商品id
     */
    void restore(String id);

    /**
     * 物理删除商品
     *
     * @param id 商品id
     */
    void realDel(String id);
}