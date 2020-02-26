package com.changgou.user.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 11:29
 * @Description: 地区接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/areas")
public class AreasController {
    @Autowired
    private AreasService areasService;

    /**
     * 查询全部地区数据
     *
     * @return 所有地区信息
     */
    @GetMapping
    public Result<List<Areas>> findAll() {
        return Result.<List<Areas>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( areasService.findAll() ).build();
    }

    /**
     * 根据ID查询地区数据
     *
     * @param id 地区 id
     * @return 地区信息
     */
    @GetMapping("/{id}")
    public Result<Areas> findById(@PathVariable("id") String id) {
        return Result.<Areas>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( areasService.findById( id ) ).build();
    }

    /**
     * 新增地区数据
     *
     * @param areas 新地区数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addAreas(@RequestBody Areas areas) {
        areasService.addAreas( areas );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改地区数据
     *
     * @param id    地区 id
     * @param areas 新地区数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateAreas(@PathVariable("id") String id, @RequestBody Areas areas) {
        areas.setAreaid( id );
        areasService.updateAreas( areas );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除地区数据
     *
     * @param id 地区 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        areasService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索地区数据
     *
     * @param searchMap 搜索条件
     * @return 地区数据
     */
    @GetMapping("/search")
    public Result<List<Areas>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Areas>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( areasService.findList( searchMap ) ).build();
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
    public Result<PageResult<Areas>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Areas> pageList = areasService.findPage( searchMap, page, size );
        return Result.<PageResult<Areas>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Areas>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}