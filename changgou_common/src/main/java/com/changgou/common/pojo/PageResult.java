package com.changgou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:26
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