package com.changgou.page.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 18:39
 * @Description: 静态化页面服务实现
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private CategoryFeign categoryFeign;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${pagepath}")
    private String pagepath;

    @Override
    public void generateHtml(String spuId) {
        //1.获取context对象，用于存储商品的相关数据
        Context context = new Context();
        //获取静态化页面数据
        Map<String, Object> itemData = this.getItemData( spuId );
        context.setVariables( itemData );
        //2.获取商品详情页面的储存位置
        File dir = new File( pagepath );
        //3.判断当前储存文件是否存在，如不存在，则创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //4.定义输出流，完成文件输出
        File file = new File( dir + "/" + spuId + ".html" );
        Writer out = null;
        try {
            out = new PrintWriter( file );
            /*
             * 模板名，内容，输出流
             */
            templateEngine.process( "item", context, out );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态化页面数据封装
     *
     * @param spuId 商品id
     * @return map集合
     */
    private Map<String, Object> getItemData(String spuId) {
        Map<String, Object> map = new HashMap<>( 0 );
        //获取spu
        Spu spu = spuFeign.findSpuById( spuId ).getData();
        map.put( "spu", spu );
        //获取图片信息
        if (ObjectUtil.isNotEmpty( spu )) {
            String images = spu.getImages();
            if (StrUtil.isNotEmpty( images )) {
                map.put( "imagesList", images.split( "," ) );
            }
        }
        //获取商品三级分类信息
        Category category1 = categoryFeign.findById( spu.getCategory1Id() ).getData();
        map.put( "category1", category1 );
        Category category2 = categoryFeign.findById( spu.getCategory2Id() ).getData();
        map.put( "category2", category2 );
        Category category3 = categoryFeign.findById( spu.getCategory3Id() ).getData();
        map.put( "category3", category3 );
        //获取sku的相关信息
        List<Sku> skuList = skuFeign.findSkuListBySpuId( spuId );
        map.put( "skuList", skuList );

        return map;
    }
}
