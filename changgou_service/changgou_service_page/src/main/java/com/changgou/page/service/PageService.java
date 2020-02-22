package com.changgou.page.service;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 18:37
 * @Description: 页面生成服务
 */
public interface PageService {
    /**
     * 生成静态h化页面
     *
     * @param spuId sup id
     */
    void generateHtml(String spuId);
}
