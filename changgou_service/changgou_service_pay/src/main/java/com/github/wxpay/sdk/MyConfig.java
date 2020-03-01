package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 20:28
 * @Description: 微信支付配置类
 */
public class MyConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "wx8397f8696b538317";
    }

    @Override
    public String getMchID() {
        return "1473426802";
    }

    @Override
    public String getKey() {
        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String s, long l, Exception e) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new DomainInfo( "api.mch.weixin.qq.com", true );
            }
        };
    }

}