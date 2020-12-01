package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    //用户注册
    int register(User user);
    //根据用户名查询
    User queryByUserName(String username);
    //修改密码
    int updatePassword(String username, String password);
}
