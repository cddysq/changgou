package com.changgou.user.feign;

import com.changgou.common.pojo.Result;
import com.changgou.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/27 18:46
 * @Description: 用户地址对外接口
 */
@FeignClient(name = "user")
public interface AddressFeign {
    /**
     * 根据当前的登录用户名获取与之相关的收件人地址
     *
     * @return 登录人所属收件地址
     */
    @GetMapping("/address/list")
    Result<List<Address>> list();
}