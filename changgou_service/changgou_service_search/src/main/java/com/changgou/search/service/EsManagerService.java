package com.changgou.search.service;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:18
 * @Description: es 服务
 */
public interface EsManagerService {
    /**
     * 导入全部数据进入es
     */
    void importAll();

    /**
     * 根据spuid查询skuList,再导入索引库
     *
     * @param spuId 商品id
     */
    void importDataBySpuId(String spuId);
}