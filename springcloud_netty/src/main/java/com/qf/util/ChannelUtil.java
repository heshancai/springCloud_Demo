package com.qf.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端连接channel工具类
 */
public class ChannelUtil {

    //map集合装所有的连接netty服务器的channel对象
    //使用线程安全的ConcurrentHashMap
    private static Map<Integer, Channel> map=new ConcurrentHashMap<>();

    //添加channel对象的方法
    public static void add(Integer uid,Channel channel){
        map.put(uid,channel);
    }

    //根据用户获得channel的方法
    public static Channel getChannle(Integer uid){
        return map.get(uid);
    }

    //根据用户id删除channel的方法
    public static void removeChannel(Integer uid){
        map.remove(uid);
    }


    //根据channel对象获得用户id的方法
    public static Integer getUid(Channel channel){
        for (Map.Entry<Integer, Channel> entry : map.entrySet()) {
            if(entry.getValue()==channel){
                return entry.getKey();
            }
        }
        return null;
    }

}
