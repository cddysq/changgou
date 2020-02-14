package com.changgou.service.system.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.system.service.MenuService;
import com.changgou.system.pojo.Menu;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:55
 * @Description: 菜单接口
 */
@RestController
@CrossOrigin
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询全部菜单数据
     *
     * @return 所有菜单信息
     */
    @GetMapping
    public Result<List<Menu>> findAll() {
        return Result.<List<Menu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( menuService.findAll() ).build();
    }

    /**
     * 根据ID查询菜单数据
     *
     * @param id 菜单id
     * @return 菜单信息
     */
    @GetMapping("/{id}")
    public Result<Menu> findById(@PathVariable("id") String id) {
        return Result.<Menu>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( menuService.findById( id ) ).build();
    }

    /**
     * 新增菜单数据
     *
     * @param Menu 新菜单数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addMenu(@RequestBody Menu Menu) {
        menuService.addMenu( Menu );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改菜单数据
     *
     * @param id   菜单id
     * @param menu 新菜单数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateMenu(@PathVariable("id") String id, @RequestBody Menu menu) {
        menu.setId( id );
        menuService.updateMenu( menu );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除菜单数据
     *
     * @param id 菜单id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        menuService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索菜单数据
     *
     * @param searchMap 搜索条件
     * @return 菜单数据
     */
    @GetMapping("/search")
    public Result<List<Menu>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Menu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( menuService.findList( searchMap ) ).build();
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
    public Result<PageResult<Menu>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Menu> pageList = menuService.findPage( searchMap, page, size );
        return Result.<PageResult<Menu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Menu>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}