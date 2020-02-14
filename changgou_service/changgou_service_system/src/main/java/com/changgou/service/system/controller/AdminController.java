package com.changgou.service.system.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.system.service.AdminService;
import com.changgou.system.pojo.Admin;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:55
 * @Description: 管理员接口
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 查询全部管理员数据
     *
     * @return 所有管理员信息
     */
    @GetMapping
    public Result<List<Admin>> findAll() {
        return Result.<List<Admin>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( adminService.findAll() ).build();
    }

    /**
     * 根据ID查询管理员数据
     *
     * @param id 管理员id
     * @return 管理员信息
     */
    @GetMapping("/{id}")
    public Result<Admin> findById(@PathVariable("id") Integer id) {
        return Result.<Admin>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( adminService.findById( id ) ).build();
    }

    /**
     * 新增管理员数据
     *
     * @param admin 新管理员数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin( admin );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改管理员数据
     *
     * @param id    管理员id
     * @param admin 新管理员数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateAdmin(@PathVariable("id") Integer id, @RequestBody Admin admin) {
        admin.setId( id );
        adminService.updateAdmin( admin );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除管理员数据
     *
     * @param id 管理员id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        adminService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索管理员数据
     *
     * @param searchMap 搜索条件
     * @return 管理员数据
     */
    @GetMapping("/search")
    public Result<List<Admin>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Admin>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( adminService.findList( searchMap ) ).build();
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
    public Result<PageResult<Admin>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Admin> pageList = adminService.findPage( searchMap, page, size );
        return Result.<PageResult<Admin>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Admin>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}