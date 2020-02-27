package com.changgou.order.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.Order;
import com.changgou.order.service.OrderService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/27 22:15
 * @Description: 订单接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 查询全部订单数据
     *
     * @return 所有订单信息
     */
    @GetMapping
    public Result<List<Order>> findAll() {
        return Result.<List<Order>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( orderService.findAll() ).build();
    }

    /**
     * 根据ID查询订单数据
     *
     * @param id 订单 id
     * @return 订单信息
     */
    @GetMapping("/{id}")
    public Result<Order> findById(@PathVariable("id") String id) {
        return Result.<Order>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( orderService.findById( id ) ).build();
    }

    /**
     * 新增订单数据
     *
     * @param order 新订单数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addOrder(@RequestBody Order order) {
        //获取当前登录用户名
        String username = tokenDecode.getUserInfo().get( "username" );
        order.setUsername( username );
        orderService.addOrder( order );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改订单数据
     *
     * @param id    订单 id
     * @param order 新订单数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateOrder(@PathVariable("id") String id, @RequestBody Order order) {
        order.setId( id );
        orderService.updateOrder( order );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除订单数据
     *
     * @param id 订单 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        orderService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索订单数据
     *
     * @param searchMap 搜索条件
     * @return 订单数据
     */
    @GetMapping("/search")
    public Result<List<Order>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Order>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( orderService.findList( searchMap ) ).build();
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
    public Result<PageResult<Order>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Order> pageList = orderService.findPage( searchMap, page, size );
        return Result.<PageResult<Order>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Order>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}