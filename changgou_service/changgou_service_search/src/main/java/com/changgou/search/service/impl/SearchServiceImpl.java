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
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
            //按照规格进行过滤查询
            for (String key : searchMap.keySet()) {
                if (key.startsWith( "spec_" )) {
                    String value = searchMap.get( key );
                    //spec_黑色
                    boolQueryBuilder.filter( QueryBuilders.termQuery( ("specMap." + key.substring( 5 ) + ".keyword"), value ) );
                }
            }
            //按照价格进行区间过滤查询
            String price = searchMap.get( "price" );
            if (StrUtil.isNotEmpty( price )) {
                String[] prices = price.split( "-" );
                //长度如为一 500/100 大于
                boolQueryBuilder.filter( QueryBuilders.rangeQuery( "price" ).gte( prices[0] ) );
                //长度如为二 500-1000/1000-2000 小于最后一个元素
                if (prices.length == 2) {
                    boolQueryBuilder.filter( QueryBuilders.rangeQuery( "price" ).lte( prices[1] ) );
                }
            }
            nativeSearchQueryBuilder.withQuery( boolQueryBuilder );


            //按照品牌进行分组(聚合)查询
            String skuBrand = "skuBrand";
            nativeSearchQueryBuilder.addAggregation( AggregationBuilders.terms( skuBrand ).field( "brandName" ) );
            //按照规格进行聚合查询
            String skuSpec = "skuSpec";
            nativeSearchQueryBuilder.addAggregation( AggregationBuilders.terms( skuSpec ).field( "spec.keyword" ) );

            //设置分页数据
            String pageNum = searchMap.get( "pageNum" );
            String pageSize = searchMap.get( "pageSize" );
            if (StrUtil.isEmpty( pageNum )) {
                //当前页码为空，默认第一页
                pageNum = "1";
            }
            if (StrUtil.isEmpty( pageSize )) {
                //每页显示条数为空，默认30条
                pageSize = "30";
            }
            nativeSearchQueryBuilder.withPageable( PageRequest.of( Integer.parseInt( pageNum ), Integer.parseInt( pageSize ) ) );

            //设置排序
            //要排序的域
            String sortField = searchMap.get( "sortField" );
            //要排序的规则
            String sortRule = searchMap.get( "sortRule" );
            if (StrUtil.isNotEmpty( sortField ) && StrUtil.isNotEmpty( sortRule )) {
                if ("ASC".equals( sortRule )) {
                    //升序
                    nativeSearchQueryBuilder.withSort( SortBuilders.fieldSort( sortField ).order( SortOrder.ASC ) );
                } else {
                    //降序
                    nativeSearchQueryBuilder.withSort( SortBuilders.fieldSort( sortField ).order( SortOrder.DESC ) );
                }
            }

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
            //封装规格分组结果
            StringTerms specTerms = (StringTerms) resultInfo.getAggregation( skuSpec );
            List<String> specList = specTerms.getBuckets().stream().map( StringTerms.Bucket::getKeyAsString ).collect( Collectors.toList() );

            return MapUtil.<String, Object>builder()
                    //封装总记录数
                    .put( "total", resultInfo.getTotalElements() )
                    //封装总页数
                    .put( "totalPages", resultInfo.getTotalPages() )
                    //封装总数据
                    .put( "rows", resultInfo.getContent() )
                    //封装品牌聚合结果
                    .put( "brandList", brandList )
                    //封装规格聚合结果
                    .put( "specList", specList )
                    //封装当前页码
                    .put( "pageNum", pageNum )
                    .build();
        }
        return null;
    }
}
