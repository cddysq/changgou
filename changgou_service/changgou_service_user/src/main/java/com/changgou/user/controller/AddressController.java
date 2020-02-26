package com.changgou.user.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 11:29
 * @Description: 地址接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 查询全部地址数据
     *
     * @return 所有地址信息
     */
    @GetMapping
    public Result<List<Address>> findAll() {
        return Result.<List<Address>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( addressService.findAll() ).build();
    }

    /**
     * 根据ID查询地址数据
     *
     * @param id 地址 id
     * @return 地址信息
     */
    @GetMapping("/{id}")
    public Result<Address> findById(@PathVariable("id") Integer id) {
        return Result.<Address>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( addressService.findById( id ) ).build();
    }

    /**
     * 新增地址数据
     *
     * @param address 新地址数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addAddress(@RequestBody Address address) {
        addressService.addAddress( address );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改地址数据
     *
     * @param id      地址 id
     * @param address 新地址数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateAddress(@PathVariable("id") Integer id, @RequestBody Address address) {
        address.setId( id );
        addressService.updateAddress( address );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除地址数据
     *
     * @param id 地址 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        addressService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索地址数据
     *
     * @param searchMap 搜索条件
     * @return 地址数据
     */
    @GetMapping("/search")
    public Result<List<Address>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Address>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( addressService.findList( searchMap ) ).build();
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
    public Result<PageResult<Address>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Address> pageList = addressService.findPage( searchMap, page, size );
        return Result.<PageResult<Address>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Address>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}