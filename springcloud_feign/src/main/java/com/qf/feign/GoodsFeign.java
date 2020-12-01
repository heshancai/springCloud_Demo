package com.qf.feign;

import com.qf.entity.GoodsEntity;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 公共调用的微服务接口
 * 商品的微服务
 */
@FeignClient("MICSERVICE-GOODS")//注册中心为服务的名称
public interface GoodsFeign {

    //添加商品
    @RequestMapping("/goods/insert")
    int insertGoods(@RequestBody GoodsEntity goodsEntity);//注册中心微服务的路径
    //查询商品的列表
    @RequestMapping("/goods/list")
    List<GoodsEntity> goodsList();

    //根据时间的场次查询对应的商品信息
    @RequestMapping("/goods/queryKillList")
    List<GoodsEntity> queryKillList(@RequestBody Date nextDate);

    //根据商品id查询商品详情
    @RequestMapping("/goods/queryByGid")
    GoodsEntity queryByGid(@RequestParam("gid") Integer gid);

    //根据商品id删除商品
    @RequestMapping("/goods/deleteById")
    int deleteById(@RequestParam("gid")Integer gid);

    //分页查询商品的服务
//    @RequestMapping("/goods/Pagelist")
//    Page<GoodsEntity> listPage(@RequestBody Page<GoodsEntity> page);
}
