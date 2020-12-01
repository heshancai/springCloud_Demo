package com.qf.serviceImpl;

import com.qf.entity.OrdersEntity;
import com.qf.entity.User;

import java.util.List;

public interface IOrdersService {

    //添加订单 返回订单的信息
    int insertOrders(Integer gid, Integer uid,Integer gnumber);
    //根据订单的表id查询一个订单的信息
    OrdersEntity queryByid(Integer id);
    //根据用户的id查询订单集合
    List<OrdersEntity> queryByUid(Integer uid);
    //订单生成uuid查询订单的信息
    OrdersEntity queryByOid(String oid);
    //根据订单生成的uuid和状态修改订单的状态
    int updateOrderStatus(String oid, Integer status);
}
