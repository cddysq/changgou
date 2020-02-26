package com.changgou.user.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 11:29
 * @Description: 省份接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/provinces")
public class ProvincesController {
    @Autowired
    private ProvincesService provincesService;

    /**
     * 查询全部省份数据
     *
     * @return 所有省份信息
     */
    @GetMapping
    public Result<List<Provinces>> findAll() {
        return Result.<List<Provinces>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( provincesService.findAll() ).build();
    }

    /**
     * 根据ID查询省份数据
     *
     * @param id 省份 id
     * @return 省份信息
     */
    @GetMapping("/{id}")
    public Result<Provinces> findById(@PathVariable("id") String id) {
        return Result.<Provinces>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( provincesService.findById( id ) ).build();
    }

    /**
     * 新增省份数据
     *
     * @param provinces 新省份数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addProvinces(@RequestBody Provinces provinces) {
        provincesService.addProvinces( provinces );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改省份数据
     *
     * @param id        省份 id
     * @param provinces 新省份数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateProvinces(@PathVariable("id") String id, @RequestBody Provinces provinces) {
        provinces.setProvinceid( id );
        provincesService.updateProvinces( provinces );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除省份数据
     *
     * @param id 省份 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        provincesService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索省份数据
     *
     * @param searchMap 搜索条件
     * @return 省份数据
     */
    @GetMapping("/search")
    public Result<List<Provinces>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Provinces>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( provincesService.findList( searchMap ) ).build();
    }

    /**
     * 分页搜索实现
     *
     * @param searchMap 搜索条件
     * @param page      当前页码
     * @param size      每页显示条数
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageResult<Provinces>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Provinces> pageList = provincesService.findPage( searchMap, page, size );
        return Result.<PageResult<Provinces>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Provinces>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}