package com.changgou.search.controller;

import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/19 19:22
 * @Description: 搜索访问接口
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 搜索接口
     *
     * @param searchMap 搜索条件
     * @return 数据集合
     */
    @GetMapping
    public Map<String, Object> search(@RequestParam Map<String, String> searchMap) {
        return searchService.search( searchMap );
    }

}