package com.changgou.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.EsManagerMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:19
 * @Description: es 具体服务实现
 */
@Service
public class EsManagerServiceImpl implements EsManagerService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private EsManagerMapper esManagerMapper;

    @Override
    public void importAll() {
        //查询sku集合
        List<Sku> skuList = skuFeign.findSkuListBySpuId( "all" );
        this.covertAdd( skuList );
    }

    @Override
    public void importDataBySpuId(String spuId) {
        List<Sku> skuList = skuFeign.findSkuListBySpuId( spuId );
        this.covertAdd( skuList );
    }

    @Override
    public void delDataBySpuId(String spuId) {
        List<Sku> skuList = skuFeign.findSkuListBySpuId( spuId );
        if (CollUtil.isEmpty( skuList )) {
            throw new RuntimeException( "当前没有数据被查询到,无法导入索引库" );
        }
        for (Sku sku : skuList) {
            esManagerMapper.deleteById( Convert.toLong( sku.getId() ) );
        }
    }

    /**
     * 转换数据添加至es
     *
     * @param skuList 商品数据集合
     */
    private void covertAdd(List<Sku> skuList) {
        if (CollUtil.isEmpty( skuList )) {
            throw new RuntimeException( "当前没有数据被查询到,无法导入索引库" );
        }
        //将集合转换为json
        String jsonSkuList = JSON.toJSONString( skuList );
        //将json转为skuInfo列表
        List<SkuInfo> skuInfoList = JSON.parseArray( jsonSkuList, SkuInfo.class );

        for (SkuInfo skuInfo : skuInfoList) {
            //将规格信息进行单独转换
            skuInfo.setSpecMap( JSON.parseObject( skuInfo.getSpec(), Map.class ) );
        }
        //添加索引库
        esManagerMapper.saveAll( skuInfoList );
    }
}