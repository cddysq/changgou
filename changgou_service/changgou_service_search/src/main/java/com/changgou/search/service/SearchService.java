package com.changgou.search.service;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/19 18:21
 * @Description: 搜索服务
 */
public interface SearchService {
    /**
     * 按照查询条件进行数据查询
     *
     * @param searchMap 搜索条件
     * @return 满足条件的数据
     */
    Map<String, Object> search(Map<String, String> searchMap);
}