package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Spec;
import com.changgou.service.goods.service.SpecService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/25 17:51
 * @Description: 规格接口
 */
@RestController
@CrossOrigin
@RequestMapping("/spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    /**
     * 查询全部规格数据
     *
     * @return 所有规格信息
     */
    @GetMapping
    public Result<List<Spec>> findAll() {
        return Result.<List<Spec>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( specService.findAll() ).build();
    }

    /**
     * 根据ID查询规格数据
     *
     * @param id 规格id
     * @return 规格信息
     */
    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable("id") Integer id) {
        return Result.<Spec>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( specService.findById( id ) ).build();
    }

    /**
     * 新增规格数据
     *
     * @param Spec 新规格数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addSpec(@RequestBody Spec Spec) {
        specService.addSpec( Spec );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改规格数据
     *
     * @param id   规格id
     * @param Spec 新规格数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateSpec(@PathVariable("id") Integer id, @RequestBody Spec Spec) {
        Spec.setId( id );
        specService.updateSpec( Spec );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除规格数据
     *
     * @param id 规格id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        specService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索规格数据
     *
     * @param searchMap 搜索条件
     * @return 规格数据
     */
    @GetMapping("/search")
    public Result<List<Spec>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Spec>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( specService.findList( searchMap ) ).build();
    }

    /**
     * 分页查询
     *
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageResult<Spec>> findPage(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Spec> pageList = specService.findPage( page, size );
        return Result.<PageResult<Spec>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Spec>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

    /**
     * 分页搜索实现
     *
     * @param searchMap 搜索条件
     * @param page      当前页码
     * @param size      每页显示条数
     * @return 分页结果
     */
    @GetMapping("/searchPage/{page}/{size}")
    public Result<PageResult<Spec>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Spec> pageList = specService.findPage( searchMap, page, size );
        return Result.<PageResult<Spec>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Spec>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

    /**
     * 根据商品分类名称查询规格列表
     *
     * @param categoryName 分类名称
     * @return 规格参数
     */
    @GetMapping("/category/{categoryName}")
    public Result<List<Map<String, Object>>> findSpecListByCategoryName(@PathVariable("categoryName") String categoryName) {
        specService.findSpecListByCategoryName( categoryName );
        return Result.<List<Map<String, Object>>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( specService.findSpecListByCategoryName( categoryName ) ).build();
    }
}