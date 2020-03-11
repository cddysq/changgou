package com.changgou.common.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/7 21:06
 * @Description: 短信发送工具类
 */
public class SmsUtils {
    private static final String ACCESSKEYID = "";
    private static final String ACCESSKEYSECRET = "";
    /**
     * 短信发送成功返回状态码
     */
    private static final String REQUEST_OK = "OK";
    /**
     * 短信发送返回状态码字段名
     */
    private static final String REQUEST_CODE = "Code";
    /**
     * 注册验证码短信模板
     */
    public static final String REGISTRY_VALIDATE_CODE = "SMS_185241091";
    /**
     * 重置手机号短信模板
     */
    public static final String RESET_PHONE_NUMBER_VALIDATE_CODE = "SMS_185231799";

    /**
     * 发送短信
     *
     * @param templateCode 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
     * @param phoneNumbers 接收短信的手机号码。国内短信：11位手机号码。
     * @param authCode     验证码
     * @return true:发送成功 false:发送失败
     */
    public static boolean sendShortMessage(String templateCode, String phoneNumbers, String authCode) {
        DefaultProfile profile = DefaultProfile.getProfile( "cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET );
        IAcsClient client = new DefaultAcsClient( profile );
        CommonRequest request = new CommonRequest();
        request.setMethod( MethodType.POST );
        request.setDomain( "dysmsapi.aliyuncs.com" );
        request.setVersion( "2017-05-25" );
        request.setAction( "SendSms" );
        request.putQueryParameter( "RegionId", "cn-hangzhou" );
        request.putQueryParameter( "PhoneNumbers", phoneNumbers );
        //短信签名名称。请在控制台签名管理页面签名名称一列查看。
        request.putQueryParameter( "SignName", "爱购商城" );
        request.putQueryParameter( "TemplateCode", templateCode );
        request.putQueryParameter( "TemplateParam", "{\"code\":\"" + authCode + "\"}" );

        try {
            CommonResponse response = client.getCommonResponse( request );
            Map<String, String> map = JSON.parseObject( response.getData(), Map.class );
            if (map.get( REQUEST_CODE ).equals( REQUEST_OK )) {
                return true;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}