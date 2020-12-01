package com.qf.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    //用户注册
    @Override
    public int register(User user) {
        //构建查询条件
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        //查询数据库中是否存在
        Integer count = userMapper.selectCount(queryWrapper);
        //不存在
        if(count==0){
            return  userMapper.insert(user);
        }
        //用户已经存在
        return -1;
    }

    //通过用户名查询一个用户
    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    //修改用户密码
    @Override
    public int updatePassword(String username, String password) {
        User user = this.queryByUserName(username);
        user.setPassword(password);
        return userMapper.updateById(user);
    }
}
