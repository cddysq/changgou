package com.changgou.service.system.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.system.service.ResourceService;
import com.changgou.system.pojo.Resource;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:55
 * @Description: 资源接口
 */
@RestController
@CrossOrigin
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 查询全部资源数据
     *
     * @return 所有资源信息
     */
    @GetMapping
    public Result<List<Resource>> findAll() {
        return Result.<List<Resource>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( resourceService.findAll() ).build();
    }

    /**
     * 根据ID查询资源数据
     *
     * @param id 资源id
     * @return 资源信息
     */
    @GetMapping("/{id}")
    public Result<Resource> findById(@PathVariable("id") Integer id) {
        return Result.<Resource>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( resourceService.findById( id ) ).build();
    }

    /**
     * 新增资源数据
     *
     * @param resource 新资源数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addResource(@RequestBody Resource resource) {
        resourceService.addResource( resource );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改资源数据
     *
     * @param id       资源id
     * @param resource 新资源数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateResource(@PathVariable("id") Integer id, @RequestBody Resource resource) {
        resource.setId( id );
        resourceService.updateResource( resource );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除资源数据
     *
     * @param id 资源id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        resourceService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索资源数据
     *
     * @param searchMap 搜索条件
     * @return 资源数据
     */
    @GetMapping("/search")
    public Result<List<Resource>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Resource>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( resourceService.findList( searchMap ) ).build();
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
    public Result<PageResult<Resource>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Resource> pageList = resourceService.findPage( searchMap, page, size );
        return Result.<PageResult<Resource>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Resource>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}