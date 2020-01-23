package com.changgou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
* @Author: Haotian
* @Date: 2020/1/23 22:48
* @Description: 分页结果
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 记录
     */
    private List<T> rows;
}