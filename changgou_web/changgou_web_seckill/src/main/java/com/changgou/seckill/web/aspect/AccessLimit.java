package com.changgou.seckill.web.aspect;

import java.lang.annotation.*;

/**
 * @Author: Haotian
 * @Date: 2020/3/7 20:07
 * @Description: 访问限制注解
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) //不仅保存到class文件中，并且jvm加载class后，该注解依然存在
public @interface AccessLimit {

}