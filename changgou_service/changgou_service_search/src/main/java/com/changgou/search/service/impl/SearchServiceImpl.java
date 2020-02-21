package com.changgou.search.service.impl;

import cn.hutool.core.collection.CollUtil;
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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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

import java.util.*;
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
                    String value = searchMap.get( key ).replace( "%2B", "+" );
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
            nativeSearchQueryBuilder.withPageable( PageRequest.of( Integer.parseInt( pageNum ) - 1, Integer.parseInt( pageSize ) ) );

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

            //设置高亮域以及高亮的样式
            HighlightBuilder.Field field = new HighlightBuilder
                    //高亮域
                    .Field( "name" )
                    //高亮样式的前缀
                    .preTags( "<span style='color:red'>" )
                    //高亮样式的后缀
                    .postTags( "</span>" );
            nativeSearchQueryBuilder.withHighlightFields( field );

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
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            if (ObjectUtil.isNotEmpty( highlightFields )) {
                                //有高亮内容，替换数据
                                skuInfo.setName( highlightFields.get( "name" ).getFragments()[0].string() );
                            }
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
                    .put( "specList", this.formartSpec( specList ) )
                    //封装当前页码
                    .put( "pageNum", pageNum )
                    .build();
        }
        return null;
    }

    /**
     * <pre>原有数据:
     *     [
     *      "{'颜色': '黑色', '尺码': '平光防蓝光-无度数电脑手机护目镜'}",
     *      "{'颜色': '红色', '尺码': '150度'}",
     *     ]
     * 需要的数据格式:
     *         {
     *            颜色:[黑色,红色],
     *            尺码:[100度,150度]
     *         }</pre>
     *
     * @param specList 规格数据
     * @return 转换后的map
     */
    public Map<String, Set<String>> formartSpec(List<String> specList) {
        Map<String, Set<String>> resultMap = new HashMap<>();
        if (CollUtil.isNotEmpty( specList )) {
            //specJsonString={'颜色': '蓝色', '版本': '6GB+128GB'}
            for (String specJsonString : specList) {
                /*将json数据转换为map
                map={
                    key=颜色 value=蓝色
                    key=版本 value=6GB+128GB
                }*/
                Map<String, String> specMap = JSON.parseObject( specJsonString, Map.class );
                for (String specKey : specMap.keySet()) {
                    //拿到每一个key {颜色,版本}
                    Set<String> specSet = resultMap.get( specKey );
                    //判断是否在map中存在此key的set
                    if (specSet == null) {
                        specSet = new HashSet<String>();
                    }
                    //将规格的值放入set中 {蓝色}{6GB+128GB}
                    specSet.add( specMap.get( specKey ) );
                    //将set放入map中 {key=颜色，value=set{蓝色}}{key=版本，value=set{6GB+128GB}}
                    resultMap.put( specKey, specSet );
                }
            }
        }
        return resultMap;
    }
}
