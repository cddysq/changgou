package com.changgou.service.goods.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 23:12
 * @Description: spu spk 共有条件
 */
public class Condition {
    public static void share(@NotNull Map<String, Object> searchMap, Example.Criteria criteria) {
        // 商品id
        String id = Convert.toStr( searchMap.get( "id" ) );
        if (StrUtil.isNotEmpty( id )) {
            criteria.andEqualTo( "id", id );
        }
        // 商品条码
        String sn = Convert.toStr( searchMap.get( "sn" ) );
        if (StrUtil.isNotEmpty( sn )) {
            criteria.andEqualTo( "sn", sn );
        }
        // SKU名称
        String name = Convert.toStr( searchMap.get( "name" ) );
        if (StrUtil.isNotEmpty( name )) {
            criteria.andLike( "name", "%" + name + "%" );
        }
        // 商品图片
        String image = Convert.toStr( searchMap.get( "image" ) );
        if (StrUtil.isNotEmpty( image )) {
            criteria.andLike( "image", "%" + image + "%" );
        }
        // 商品图片列表
        String images = Convert.toStr( searchMap.get( "images" ) );
        if (StrUtil.isNotEmpty( images )) {
            criteria.andLike( "images", "%" + images + "%" );
        }
        // 销量
        String saleNum = Convert.toStr( searchMap.get( "saleNum" ) );
        if (StrUtil.isNotEmpty( saleNum )) {
            criteria.andEqualTo( "saleNum", saleNum );
        }
        // 评论数
        String commentNum = Convert.toStr( searchMap.get( "commentNum" ) );
        if (StrUtil.isNotEmpty( commentNum )) {
            criteria.andEqualTo( "commentNum", commentNum );
        }
    }
}
