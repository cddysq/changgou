package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 22:46
 * @Description: 用户通用接口
 **/
public interface UserMapper extends Mapper<User> {

    /**
     * 修改用户积分
     *
     * @param username 用户名
     * @param point    当前传入积分
     * @return 是否修改成功 1：成功 0：失败
     */
    @Update("update tb_user set points=points+#{point} where username=#{username}")
    int updateUserPoint(@Param("username") String username, @Param("point") int point);
}