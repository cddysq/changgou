package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.changgou.service.goods.service.SpuService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * spu 接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:57
 **/
@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 查询全部Spu数据
     *
     * @return 所有Spu信息
     */
    @GetMapping
    public Result<List<Spu>> findAll() {
        return Result.<List<Spu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( spuService.findAll() ).build();
    }

    /**
     * 根据ID查询Spu数据
     *
     * @param id Spu id
     * @return Spu信息集合
     */
    @GetMapping("/findSpuById/{id}")
    public Result<Spu> findSpuById(@PathVariable("id") String id) {
        return Result.<Spu>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( spuService.findById( id ) ).build();
    }

    /**
     * 根据ID查询Spu与Sku数据
     *
     * @param id Spu id
     * @return Spu和Sku信息集合
     */
    @GetMapping("/{id}")
    public Result<Goods> findById(@PathVariable("id") String id) {
        return Result.<Goods>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( spuService.findGoodsById( id ) ).build();
    }

    /**
     * 新增Spu数据
     *
     * @param goods 新Spu数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addSpu(@RequestBody Goods goods) {
        spuService.addSpu( goods );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改Spu数据
     *
     * @param goods 新Spu与Sku数据
     * @return 修改结果
     */
    @PutMapping()
    public Result<Object> updateSpu(@RequestBody Goods goods) {
        spuService.updateSpu( goods );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除Spu数据
     *
     * @param id Spu id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        spuService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索Spu数据
     *
     * @param searchMap 搜索条件
     * @return Spu数据
     */
    @GetMapping("/search")
    public Result<List<Spu>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Spu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( spuService.findList( searchMap ) ).build();
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
    public Result<PageResult<Spu>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Spu> pageList = spuService.findPage( searchMap, page, size );
        return Result.<PageResult<Spu>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Spu>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }

    /**
     * 商品审核
     *
     * @param id spu id
     * @return 审核提示
     */
    @PutMapping("/audit/{id}")
    public Result<Object> audit(@PathVariable("id") String id) {
        spuService.audit( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "商品审核成功" ).build();
    }

    /**
     * 商品上架
     *
     * @param id spu id
     * @return 上架提示
     */
    @PutMapping("/pull/{id}")
    public Result<Object> pull(@PathVariable("id") String id) {
        spuService.pull( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "商品下架成功" ).build();
    }

    /**
     * 商品下架
     *
     * @param id spu id
     * @return 下架提示
     */
    @PutMapping("/put/{id}")
    public Result<Object> put(@PathVariable("id") String id) {
        spuService.put( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "商品上架成功" ).build();
    }

    /**
     * 商品还原
     *
     * @param id spu id
     * @return 还原提示
     */
    @PutMapping("/restore/{id}")
    public Result<Object> restore(@PathVariable("id") String id) {
        spuService.restore( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "商品还原成功" ).build();
    }

    /**
     * 物理删除商品
     *
     * @param id spu id
     * @return 删除提示
     */
    @DeleteMapping("/realDel/{id}")
    public Result<Object> realDel(@PathVariable("id") String id) {
        spuService.realDel( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "商品删除成功" ).build();
    }
}