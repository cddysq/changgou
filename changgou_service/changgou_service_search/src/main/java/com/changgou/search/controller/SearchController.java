package com.changgou.search.controller;

import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2020/2/19 19:22
 * @Description: 搜索访问接口
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 搜索结果页面静态化
     *
     * @param searchMap 搜索条件
     * @param model     封装数据模型
     * @return 搜索条件+符合条件数据
     */
    @GetMapping("/list")
    public String list(@RequestParam Map<String, String> searchMap, Model model) {
        //获取查询结果
        Map<String, Object> resultMap = searchService.search( searchMap );
        //封装查询条件返回
        model.addAttribute( "searchMap", searchMap );
        //封装查询数据返回
        model.addAttribute( "resultMap", resultMap );
        return "search";
    }

    /**
     * 搜索接口
     *
     * @param searchMap 搜索条件
     * @return 数据集合
     */
    @GetMapping
    @ResponseBody
    public Map<String, Object> search(@RequestParam Map<String, String> searchMap) {
        //特殊符号处理
        /*this.handleSearchMap( searchMap );*/
        return searchService.search( searchMap );
    }

    /**
     * 处理前端多个规格之间进行拼接的+号
     *
     * @param searchMap 搜搜天剑
     */
    private void handleSearchMap(Map<String, String> searchMap) {
        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith( "spec_" )) {
                searchMap.put( entry.getKey(), entry.getValue().replace( "+", "%2B" ) );
            }
        }
    }

}