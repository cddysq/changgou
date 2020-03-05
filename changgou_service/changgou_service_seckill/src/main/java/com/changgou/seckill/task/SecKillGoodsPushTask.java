package com.changgou.seckill.task;

import cn.hutool.core.collection.CollUtil;
import com.changgou.common.util.DateUtil;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 18:06
 * @Description: 秒杀定时任务
 */
@Component
public class SecKillGoodsPushTask {
    /**
     * redis 中秒杀商品 key 前缀
     */
    private static final String SEC_KILL_GOODS_KEY = "sec_kill_goods_key";
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void loadSecKillGoodsToRedis() {
        //查询所有符合条件的秒杀商品
        //1.获取时间段集合并循环遍历出每一个时间段
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            //2.获取每一个时间段名称,用于后续redis中key的设置
            String redisExtName = DateUtil.date2Str( dateMenu );
            //3.拼装查询条件
            Example example = getExample( dateMenu, redisExtName );
            //4.执行查询获取对应的结果集
            List<SeckillGoods> secKillGoodsList = seckillGoodsMapper.selectByExample( example );
            //5.将秒杀商品存入缓存
            for (SeckillGoods goods : secKillGoodsList) {
                redisTemplate.opsForHash().put( SEC_KILL_GOODS_KEY + redisExtName, goods.getId(), goods );
            }
        }
    }

    /**
     * 构建查询对象
     *
     * @param dateMenu     当前时间段
     * @param redisExtName 当前时间段转为yyyyMMddHH的值
     * @return 查询条件对象
     */
    private Example getExample(Date dateMenu, String redisExtName) {
        Example example = new Example( SeckillGoods.class );
        Example.Criteria criteria = example.createCriteria();
        //状态必须为审核通过 status=1
        criteria.andEqualTo( "status", "1" );
        //商品库存个数>0
        String startTime = cn.hutool.core.date.DateUtil.formatDateTime( dateMenu );
        criteria.andGreaterThanOrEqualTo( "startTime", startTime );
        //秒杀商品结束<当前时间段+2小时
        String endTime = cn.hutool.core.date.DateUtil.formatDateTime( cn.hutool.core.date.DateUtil.offsetHour( dateMenu, 2 ) );
        criteria.andLessThan( "endTime", endTime );
        //排除之前已经加载到Redis缓存中的商品数据 key field=对象id value=对象
        Set field = redisTemplate.boundHashOps( SEC_KILL_GOODS_KEY + redisExtName ).keys();
        if (CollUtil.isNotEmpty( field )) {
            criteria.andNotIn( "id", field );
        }
        return example;
    }
}