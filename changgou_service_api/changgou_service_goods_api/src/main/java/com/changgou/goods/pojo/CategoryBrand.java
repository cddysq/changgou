package com.changgou.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Haotian
 * @Date: 2020/2/16 18:35
 * @Description: 分类品牌关联实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_category_brand")
public class CategoryBrand {
    /**
     * 分类id
     */

    @Id
    private Integer categoryId;

    /**
     * 品牌id
     */
    @Id
    private Integer brandId;
}