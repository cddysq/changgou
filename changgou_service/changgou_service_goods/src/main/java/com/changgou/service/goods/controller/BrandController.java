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
 * 品牌接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:56
 **/
@RestController
@CrossOrigin // 开启跨域访问
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 查询全部品牌数据
     *
     * @return 所有品牌信息
     */
    @GetMapping
    public Result<List<Brand>> findAll() {
        return Result.<List<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findAll() ).build();
    }

    /**
     * 根据ID查询品牌数据
     *
     * @param id 品牌id
     * @return 品牌信息
     */
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        return Result.<Brand>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findById( id ) ).build();
    }

    /**
     * 新增品牌数据
     *
     * @param brand 新品牌数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addBrand(@RequestBody Brand brand) {
        brandService.addBrand( brand );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改品牌数据
     *
     * @param id    品牌id
     * @param brand 新品牌数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateBrand(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId( id );
        brandService.updateBrand( brand );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除品牌数据
     *
     * @param id 品牌id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        brandService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param searchMap 搜索条件
     * @return 品牌数据
     */
    @GetMapping("/search")
    public Result<List<Brand>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Brand>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findList( searchMap ) ).build();
    }

    /**
     * 分页查询
     *
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页结果
     */
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

    /**
     * 分页搜索实现
     *
     * @param searchMap 搜索条件
     * @param page      当前页码
     * @param size      每页显示条数
     * @return 分页结果
     */
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

    /**
     * 根据分类名称查询品牌列表
     *
     * @param categoryName 分类名称
     * @return 品牌名与图片
     */
    @GetMapping("/category/{categoryName}")
    public Result<List<Map<String, Object>>> findBrandListByCategoryName(@PathVariable("categoryName") String categoryName) {
        return Result.<List<Map<String, Object>>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( brandService.findBrandListByCategoryName( categoryName ) ).build();
    }
}