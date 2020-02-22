package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Category;
import com.changgou.service.goods.service.CategoryService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 18:21
 * @Description: 分类接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询全部Category数据
     *
     * @return 所有Category信息
     */
    @GetMapping
    public Result<List<Category>> findAll() {
        return Result.<List<Category>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( categoryService.findAll() ).build();
    }

    /**
     * 根据ID查询Category数据
     *
     * @param id Category id
     * @return Category信息
     */
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable("id") Integer id) {
        return Result.<Category>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( categoryService.findById( id ) ).build();
    }

    /**
     * 新增Category数据
     *
     * @param category 新Category数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addCategory(@RequestBody Category category) {
        categoryService.addCategory( category );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改Category数据
     *
     * @param id  Category id
     * @param category 新Category数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        category.setId( id );
        categoryService.updateCategory( category );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除Category数据
     *
     * @param id Category id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        categoryService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索Category数据
     *
     * @param searchMap 搜索条件
     * @return Category数据
     */
    @GetMapping("/search")
    public Result<List<Category>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Category>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( categoryService.findList( searchMap ) ).build();
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
    public Result<PageResult<Category>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Category> pageList = categoryService.findPage( searchMap, page, size );
        return Result.<PageResult<Category>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Category>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

}