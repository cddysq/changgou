package com.changgou.service.system.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.system.service.RoleService;
import com.changgou.system.pojo.Role;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:55
 * @Description: 角色接口
 */
@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询全部角色数据
     *
     * @return 所有角色信息
     */
    @GetMapping
    public Result<List<Role>> findAll() {
        return Result.<List<Role>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( roleService.findAll() ).build();
    }

    /**
     * 根据ID查询角色数据
     *
     * @param id 角色id
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public Result<Role> findById(@PathVariable("id") Integer id) {
        return Result.<Role>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( roleService.findById( id ) ).build();
    }

    /**
     * 新增角色数据
     *
     * @param role 新角色数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addRole(@RequestBody Role role) {
        roleService.addRole( role );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改角色数据
     *
     * @param id   角色id
     * @param role 新角色数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateRole(@PathVariable("id") Integer id, @RequestBody Role role) {
        role.setId( id );
        roleService.updateRole( role );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除角色数据
     *
     * @param id 角色id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        roleService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索角色数据
     *
     * @param searchMap 搜索条件
     * @return 角色数据
     */
    @GetMapping("/search")
    public Result<List<Role>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Role>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( roleService.findList( searchMap ) ).build();
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
    public Result<PageResult<Role>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Role> pageList = roleService.findPage( searchMap, page, size );
        return Result.<PageResult<Role>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Role>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}