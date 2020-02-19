package com.changgou.search.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Haotian
 * @Date: 2020/2/19 18:22
 * @Description: 搜索服务实现
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        //有数据，拼接条件
        if (ObjectUtil.isNotEmpty( searchMap )) {
            //构建查询对象
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            //按照关键字查询
            String keywords = searchMap.get( "keywords" );
            if (StrUtil.isNotEmpty( keywords )) {
                boolQueryBuilder.must( QueryBuilders.matchQuery( "name", keywords ).operator( Operator.AND ) );
            }

            //按照品牌进行过滤查询
            String brand = searchMap.get( "brand" );
            if (StrUtil.isNotEmpty( brand )) {
                boolQueryBuilder.filter( QueryBuilders.termQuery( "brandName", brand ) );
            }
            nativeSearchQueryBuilder.withQuery( boolQueryBuilder );

            //按照品牌进行分组(聚合)查询
            String skuBrand = "skuBrand";
            nativeSearchQueryBuilder.addAggregation( AggregationBuilders.terms( skuBrand ).field( "brandName" ) );

            //执行查询，返回结果
            AggregatedPage<SkuInfo> resultInfo = elasticsearchTemplate.queryForPage( nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    List<T> list = new ArrayList<>();
                    //获取查询结果命中
                    SearchHits hits = searchResponse.getHits();
                    //有查询结果
                    if (ObjectUtil.isNotEmpty( hits )) {
                        for (SearchHit hit : hits) {
                            //数据转换为skuInfo
                            SkuInfo skuInfo = JSON.parseObject( hit.getSourceAsString(), SkuInfo.class );
                            list.add( (T) skuInfo );
                        }
                    }
                    return new AggregatedPageImpl<T>( list, pageable, hits.getTotalHits(), searchResponse.getAggregations() );
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            } );
            //封装品牌的分组结果
            StringTerms brandTerms = (StringTerms) resultInfo.getAggregation( skuBrand );
            List<String> brandList = brandTerms.getBuckets().stream().map( StringTerms.Bucket::getKeyAsString ).collect( Collectors.toList() );

            return MapUtil.<String, Object>builder()
                    //封装总记录数
                    .put( "total", resultInfo.getTotalElements() )
                    //封装总页数
                    .put( "totalPages", resultInfo.getTotalPages() )
                    //封装总数据
                    .put( "rows", resultInfo.getContent() )
                    //封装品牌聚合结果
                    .put( "brandList", brandList )
                    .build();
        }
        return null;
    }
}
