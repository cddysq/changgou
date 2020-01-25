package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.goods.service.BrandService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/24 14:35
 * @Description: 品牌接口
 */
@RestController
@CrossOrigin //开启跨域访问
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAll() {
        return Result.<List<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findAll() ).build();
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        return Result.<Brand>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findById( id ) ).build();
    }

    @PostMapping
    public Result<Object> addBrand(@RequestBody Brand brand) {
        brandService.addBrand( brand );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    @PutMapping("/{id}")
    public Result<Object> updateBrand(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId( id );
        brandService.updateBrand( brand );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        brandService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    @GetMapping("/search")
    public Result<List<Brand>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findList( searchMap ) ).build();
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageResult<Brand>> findPage(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Brand> pageList = brandService.findPage( page, size );
        return Result.<PageResult<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Brand>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

    @GetMapping("/searchPage/{page}/{size}")
    public Result<PageResult<Brand>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Brand> pageList = brandService.findPage( searchMap, page, size );
        return Result.<PageResult<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Brand>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}