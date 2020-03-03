package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Sku;
import com.changgou.service.goods.service.SkuService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/16 18:25
 * @Description: sku 接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 查询全部Sku数据
     *
     * @return 所有Sku信息
     */
    @GetMapping
    public Result<List<Sku>> findAll() {
        return Result.<List<Sku>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( skuService.findAll() ).build();
    }

    /**
     * 根据ID查询Sku数据
     *
     * @param id Sku id
     * @return Sku信息
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable("id") String id) {
        return Result.<Sku>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( skuService.findById( id ) ).build();
    }

    /**
     * 新增Sku数据
     *
     * @param sku 新Sku数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addSku(@RequestBody Sku sku) {
        skuService.addSku( sku );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改Sku数据
     *
     * @param id  Sku id
     * @param sku 新Sku数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateSku(@PathVariable("id") String id, @RequestBody Sku sku) {
        sku.setId( id );
        skuService.updateSku( sku );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除Sku数据
     *
     * @param id Sku id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        skuService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索Sku数据
     *
     * @param searchMap 搜索条件
     * @return Sku数据
     */
    @GetMapping("/search")
    public Result<List<Sku>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Sku>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( skuService.findList( searchMap ) ).build();
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
    public Result<PageResult<Sku>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Sku> pageList = skuService.findPage( searchMap, page, size );
        return Result.<PageResult<Sku>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Sku>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

    /**
     * 查询商品
     *
     * @param spuId 商品id
     * @return 商品数据
     */
    @GetMapping("/spu/{spuId}")
    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId) {
        Map<String, Object> searchMap = new HashMap<>( 0 );
        if (!"all".equals( spuId )) {
            searchMap.put( "spuId", spuId );
        }
        searchMap.put( "status", "1" );
        return skuService.findList( searchMap );
    }

    /**
     * 扣减库存，增加销量
     *
     * @param username 当前下单用户名
     * @return 操作提示
     */
    @PostMapping("/decr/count")
    public Result<Object> decrCount(@RequestParam("username") String username) {
        skuService.decrCount( username );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "库存扣减成功，销量已增加" ).build();
    }

    /**
     * 回滚库存，扣减销量
     *
     * @param skuId  商品id
     * @param number 商品数量
     * @return 操作提示
     */
    @PostMapping("/resumeStockNumber")
    public Result<Object> resumeStockNumber(@RequestParam("skuId") String skuId, @RequestParam("num") Integer number) {
        skuService.resumeStockNumber( skuId, number );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "回滚库存成功，销量已减少" ).build();
    }
}