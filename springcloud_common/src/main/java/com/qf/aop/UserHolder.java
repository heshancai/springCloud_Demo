package com.qf.aop;

import com.qf.entity.User;

/**
 * 线程信息控制实体类
 * 线程之间互不干扰
 * 添加购物车时 不同的用户存不同的用户信息
 */
public class UserHolder {

    private static ThreadLocal<User> user=new ThreadLocal<>();

    //获得当前的用户登录状态 不为空为真
    public static boolean isLogin(){
        return user.get()!=null;
    }

    //设置当前对象的信息
    public static void setUser(User user){
        UserHolder.user.set(user);
    }

    //获取当前对象的信息
    public static User getUser(){
        return user.get();
    }

    /**
     * ThreadLocal测试
     * @param args
    /* */
    /*
    public static void main(String[] args) {

        ThreadLocal<String> threadLocal=new ThreadLocal();
        threadLocal.set("hello");
        new Thread(){
            @Override
            public void run() {
                threadLocal.set("my");

                System.out.println(threadLocal.get());
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadLocal.get());



    }*/



}
