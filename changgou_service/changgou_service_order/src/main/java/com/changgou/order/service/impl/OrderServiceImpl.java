package com.changgou.order.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.order.dao.OrderItemMapper;
import com.changgou.order.dao.OrderMapper;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import com.changgou.order.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 22:26
 * @Description: 分类服务实现
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    private Snowflake snowflake = IdUtil.createSnowflake( 1, 1 );

    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }

    @Override
    public Order findById(String id) {
        return orderMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(Order order) {
        //1.获取购物车的相关数据  → redis 中取
        Map<String, Object> cartMap = cartService.list( order.getUsername() );
        List<OrderItem> orderItemList = (List<OrderItem>) cartMap.get( "orderItemList" );

        //2.统计计算：总金额，总数量
        //3.填充订单数据并保存到tb_order表
        String orderId = snowflake.nextIdStr();
        order.setId( orderId );
        order.setTotalNum( (Integer) cartMap.get( "totalNum" ) );
        order.setTotalMoney( (Integer) cartMap.get( "totalMoney" ) );
        order.setPayMoney( (Integer) cartMap.get( "totalMoney" ) );
        order.setCreateTime( new Date() );
        order.setUpdateTime( new Date() );
        order.setBuyerRate( "0" );
        order.setSourceType( "1" );
        order.setOrderStatus( "0" );
        order.setPayStatus( "0" );
        order.setConsignStatus( "0" );
        orderMapper.insertSelective( order );

        //4.填充订单项数据并保存到tb_order_item
        for (OrderItem orderItem : orderItemList) {
            orderItem.setId( snowflake.nextIdStr() );
            orderItem.setIsReturn( "0" );
            orderItem.setOrderId( orderId );
            orderItemMapper.insertSelective( orderItem );
        }
        //5.扣减库存
        skuFeign.decrCount( order.getUsername() );

        //6.从redis中删除购物车数据
        redisTemplate.delete( "cart_" + order.getUsername() );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective( order );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        orderMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Order> findList(@NotNull Map<String, Object> searchMap) {
        return orderMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Order> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> orderMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Order.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 订单id
            String id = Convert.toStr( searchMap.get( "id" ) );
            if (StrUtil.isNotEmpty( id )) {
                criteria.andEqualTo( "id", id );
            }
            // 支付类型，1、在线支付、0 货到付款
            String payType = Convert.toStr( searchMap.get( "payType" ) );
            if (StrUtil.isNotEmpty( payType )) {
                criteria.andEqualTo( "payType", payType );
            }
            // 物流名称
            String shippingName = Convert.toStr( searchMap.get( "shippingName" ) );
            if (StrUtil.isNotEmpty( shippingName )) {
                criteria.andLike( "shippingName", "%" + shippingName + "%" );
            }
            // 物流单号
            String shippingCode = Convert.toStr( searchMap.get( "shippingCode" ) );
            if (StrUtil.isNotEmpty( shippingCode )) {
                criteria.andLike( "shippingCode", "%" + shippingCode + "%" );
            }
            // 用户名称
            String username = Convert.toStr( searchMap.get( "username" ) );
            if (StrUtil.isNotEmpty( username )) {
                criteria.andLike( "username", "%" + username + "%" );
            }
            // 买家留言
            String buyerMessage = Convert.toStr( searchMap.get( "buyerMessage" ) );
            if (StrUtil.isNotEmpty( buyerMessage )) {
                criteria.andLike( "buyerMessage", "%" + buyerMessage + "%" );
            }
            // 是否评价
            String buyerRate = Convert.toStr( searchMap.get( "buyerRate" ) );
            if (StrUtil.isNotEmpty( buyerRate )) {
                criteria.andLike( "buyerRate", "%" + buyerRate + "%" );
            }
            // 收货人
            String receiverContact = Convert.toStr( searchMap.get( "receiverContact" ) );
            if (StrUtil.isNotEmpty( receiverContact )) {
                criteria.andLike( "receiverContact", "%" + receiverContact + "%" );
            }
            // 收货人手机
            String receiverMobile = Convert.toStr( searchMap.get( "receiverMobile" ) );
            if (StrUtil.isNotEmpty( receiverMobile )) {
                criteria.andLike( "receiverMobile", "%" + receiverMobile + "%" );
            }
            // 收货人地址
            String receiverAddress = Convert.toStr( searchMap.get( "receiverAddress" ) );
            if (StrUtil.isNotEmpty( receiverAddress )) {
                criteria.andLike( "receiverAddress", "%" + receiverAddress + "%" );
            }
            // 订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
            String sourceType = Convert.toStr( searchMap.get( "sourceType" ) );
            if (StrUtil.isNotEmpty( sourceType )) {
                criteria.andEqualTo( "sourceType", sourceType );
            }
            // 交易流水号
            String transactionId = Convert.toStr( searchMap.get( "transactionId" ) );
            if (StrUtil.isNotEmpty( transactionId )) {
                criteria.andLike( "transactionId", "%" + transactionId + "%" );
            }
            // 订单状态
            String orderStatus = Convert.toStr( searchMap.get( "orderStatus" ) );
            if (StrUtil.isNotEmpty( orderStatus )) {
                criteria.andEqualTo( "orderStatus", orderStatus );
            }
            // 支付状态
            String payStatus = Convert.toStr( searchMap.get( "payStatus" ) );
            if (StrUtil.isNotEmpty( payStatus )) {
                criteria.andEqualTo( "payStatus", payStatus );
            }
            // 发货状态
            String consignStatus = Convert.toStr( searchMap.get( "consignStatus" ) );
            if (StrUtil.isNotEmpty( consignStatus )) {
                criteria.andEqualTo( "consignStatus", consignStatus );
            }
            // 是否删除
            String isDelete = Convert.toStr( searchMap.get( "isDelete" ) );
            if (StrUtil.isNotEmpty( isDelete )) {
                criteria.andEqualTo( "isDelete", isDelete );
            }
            // 数量合计
            String totalNum = Convert.toStr( searchMap.get( "totalNum" ) );
            if (StrUtil.isNotEmpty( totalNum )) {
                criteria.andEqualTo( "totalNum", totalNum );
            }
            // 金额合计
            String totalMoney = Convert.toStr( searchMap.get( "totalMoney" ) );
            if (StrUtil.isNotEmpty( totalMoney )) {
                criteria.andEqualTo( "totalMoney", totalMoney );
            }
            // 优惠金额
            String preMoney = Convert.toStr( searchMap.get( "preMoney" ) );
            if (StrUtil.isNotEmpty( preMoney )) {
                criteria.andEqualTo( "preMoney", preMoney );
            }
            // 邮费
            String postFee = Convert.toStr( searchMap.get( "postFee" ) );
            if (StrUtil.isNotEmpty( postFee )) {
                criteria.andEqualTo( "postFee", postFee );
            }
            // 实付金额
            String payMoney = Convert.toStr( searchMap.get( "payMoney" ) );
            if (StrUtil.isNotEmpty( payMoney )) {
                criteria.andEqualTo( "payMoney", payMoney );
            }
        }
        return example;
    }
}