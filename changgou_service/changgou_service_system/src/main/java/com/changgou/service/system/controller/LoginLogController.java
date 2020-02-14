package com.changgou.service.system.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.system.service.LoginLogService;
import com.changgou.system.pojo.LoginLog;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:55
 * @Description: 登录日志接口
 */
@RestController
@CrossOrigin
@RequestMapping("/loginLog")
public class LoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 查询全部登录日志数据
     *
     * @return 所有登录日志信息
     */
    @GetMapping
    public Result<List<LoginLog>> findAll() {
        return Result.<List<LoginLog>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( loginLogService.findAll() ).build();
    }

    /**
     * 根据ID查询登录日志数据
     *
     * @param id 登录日志id
     * @return 登录日志信息
     */
    @GetMapping("/{id}")
    public Result<LoginLog> findById(@PathVariable("id") Integer id) {
        return Result.<LoginLog>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( loginLogService.findById( id ) ).build();
    }

    /**
     * 新增登录日志数据
     *
     * @param loginLog 新登录日志数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addLoginLog(@RequestBody LoginLog loginLog) {
        loginLogService.addLoginLog( loginLog );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改登录日志数据
     *
     * @param id       登录日志id
     * @param loginLog 新登录日志数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateLoginLog(@PathVariable("id") Integer id, @RequestBody LoginLog loginLog) {
        loginLog.setId( id );
        loginLogService.updateLoginLog( loginLog );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除登录日志数据
     *
     * @param id 登录日志id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        loginLogService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索登录日志数据
     *
     * @param searchMap 搜索条件
     * @return 登录日志数据
     */
    @GetMapping("/search")
    public Result<List<LoginLog>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<LoginLog>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( loginLogService.findList( searchMap ) ).build();
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
    public Result<PageResult<LoginLog>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<LoginLog> pageList = loginLogService.findPage( searchMap, page, size );
        return Result.<PageResult<LoginLog>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<LoginLog>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}