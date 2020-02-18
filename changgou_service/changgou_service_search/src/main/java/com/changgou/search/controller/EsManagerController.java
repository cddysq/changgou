package com.changgou.search.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.search.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:37
 * @Description: es访问接口
 */
@RestController
@RequestMapping("/manager")
public class EsManagerController {
    @Autowired
    private EsManagerService esManagerService;

    /**
     * 导入全部数据
     */
    @GetMapping("/importAll")
    public Result<Object> importAll() {
        esManagerService.importAll();
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "导入全部数据成功" ).build();
    }
}