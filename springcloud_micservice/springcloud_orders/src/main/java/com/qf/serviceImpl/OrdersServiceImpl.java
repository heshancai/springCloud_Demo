package com.qf.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.OrderDetailMapper;
import com.qf.dao.OrdersMapper;
import com.qf.entity.GoodsEntity;
import com.qf.entity.OrderDetilsEntity;
import com.qf.entity.OrdersEntity;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    //调用商品服务
    @Autowired
    private GoodsFeign goodsFeign;

    /**
     * 抢购秒杀商品生成订单
     * @param gid
     * @param uid
     * @param gnumber
     * @return
     */
    @Override
    public int insertOrders(Integer gid, Integer uid, Integer gnumber) {

        //根据商品的id查询商品信息
        GoodsEntity goodsEntity=goodsFeign.queryByGid(gid);
        //生成订单的信息
        OrdersEntity ordersEntity=new OrdersEntity()
                .setOrderid(UUID.randomUUID().toString())
                .setUid(uid)
                .setAllprice(goodsEntity.getGoodsSecondKillEntity().getKillPrice().multiply(BigDecimal.valueOf(gnumber)));
        //保存订单的信息
        ordersMapper.insert(ordersEntity);
        //生成订单详情的消息
        OrderDetilsEntity orderDetilsEntity=new OrderDetilsEntity()
                .setGid(gid)
                .setSubject(goodsEntity.getSubject())
                .setDetilsPrice(goodsEntity.getGoodsSecondKillEntity().getKillPrice().multiply(BigDecimal.valueOf(gnumber)))
                .setOid(ordersEntity.getId())
                .setPrice(goodsEntity.getPrice())
                .setNumber(gnumber)
                .setFmurl(goodsEntity.getFmUrl());
        //保存订单详情的消息
        orderDetailMapper.insert(orderDetilsEntity);
            return 1;
    }

    @Override
    public OrdersEntity queryByid(Integer id) {
        return  null;
    }

    /**
     * 根据用户id查询所有的订单
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public List<OrdersEntity> queryByUid(Integer uid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",uid);
        queryWrapper.orderByDesc("create_time");
        List<OrdersEntity> ordersEntities = ordersMapper.selectList(queryWrapper);
        //一个订单多个订单详情
        for (OrdersEntity ordersEntity : ordersEntities) {

            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("oid",ordersEntity.getId());

            List<OrderDetilsEntity> list = orderDetailMapper.selectList(queryWrapper1);

            ordersEntity.setOrderDetils(list);

        }
        return ordersEntities;
    }


    @Override
    public OrdersEntity queryByOid(String oid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid",oid);
        OrdersEntity ordersEntity = ordersMapper.selectOne(queryWrapper);

        //查询订单详情
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("oid", ordersEntity.getId());
        List<OrderDetilsEntity> list = orderDetailMapper.selectList(queryWrapper1);
        ordersEntity.setOrderDetils(list);
        return ordersEntity;
    }

    @Override
    public int updateOrderStatus(String oid, Integer status) {
        OrdersEntity ordersEntity = this.queryByOid(oid);
        ordersEntity.setStatus(status);
        return ordersMapper.updateById(ordersEntity);
    }
}
