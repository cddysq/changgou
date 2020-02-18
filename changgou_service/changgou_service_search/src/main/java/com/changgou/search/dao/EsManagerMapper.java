package com.changgou.search.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:15
 * @Description: es操作商品接口
 */
@Component
public interface EsManagerMapper extends ElasticsearchRepository<SkuInfo, Long> {
}