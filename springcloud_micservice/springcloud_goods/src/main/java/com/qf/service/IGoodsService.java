package com.qf.service;

import com.qf.entity.GoodsEntity;

import java.util.Date;
import java.util.List;

public interface IGoodsService {

    //添加商品
    int insertGoods(GoodsEntity goodsEntity);
    //查询商品的列表
    List<GoodsEntity> goodsList();

    //根据时间的场次进行查询抢购的商品
    List<GoodsEntity> queryKillList(Date date);

    GoodsEntity queryByGid(Integer gid);

    //删除商品
    int deleteById(Integer gid);


    //Page<GoodsEntity> listPage(Page<GoodsEntity> page);
}
