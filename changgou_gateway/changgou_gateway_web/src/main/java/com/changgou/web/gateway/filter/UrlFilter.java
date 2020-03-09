package com.changgou.web.gateway.filter;

/**
 * @Author: Haotian
 * @Date: 2020/2/25 18:01
 * @Description: url 规则
 */
public class UrlFilter {
    /**
     * 所有需要传递令牌的地址
     */
    public static String filterPath = "/api/wuser/**,/api/worder/**,/api/wseckillorder,/api/seckill,/api/wxpay,/api/wxpay/**,/api/worder/**,/api/user/**,/api/address/**,/api/wcart/**,/api/cart/**,/api/categoryReport/**,/api/orderConfig/**,/api/order/**,/api/orderItem/**,/api/orderLog/**,/api/preferential/**,/api/returnCause/**,/api/returnOrder/**,/api/returnOrderItem/**";

    /**
     * 判断访问 url 是否需要令牌
     * @param url 请求路径
     * @return true 需要令牌，false 不需要令牌
     */
    public static boolean hasAuthorize(String url) {
        String[] split = filterPath.replace( "**", "" ).split( "," );

        for (String value : split) {
            if (url.startsWith( value )) {
                //代表当前的访问地址是需要传递令牌的
                return true;
            }
        }
        //代表当前的访问地址是不需要传递令牌的
        return false;
    }
}