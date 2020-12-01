package com.qf.controller;

import com.qf.entity.GoodsEntity;
import com.qf.service.IGoodsService;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 *商品服务
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    //调用业务层
    @Autowired
    private IGoodsService goodsService;

    /**
     * 添加商品的服务
     * @param goodsEntity
     * @return
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody GoodsEntity goodsEntity) {
        System.out.println("执行添加商品的服务"+goodsEntity);
        return goodsService.insertGoods(goodsEntity);
    }


    /**
     * 查询商品服务
     * @return
     */
    @RequestMapping("/list")
    public List<GoodsEntity> list(){
        return goodsService.goodsList();
    }


    /**
     * 根据场次额时间查询对应的商品信息
     * @return
     */
    @RequestMapping("/queryKillList")
    public List<GoodsEntity> queryKillList(@RequestBody Date date){
        //根据时间的整点数来进行查询对应场次的商品信息
        return goodsService.queryKillList(date);
    }


    @RequestMapping("/queryByGid")
    public GoodsEntity queryByGid(@RequestParam("gid") Integer gid){
        GoodsEntity goodsEntity=goodsService.queryByGid(gid);
        return goodsEntity;
    }


    @RequestMapping("/deleteById")
    public int deleteById(@RequestParam("gid") Integer gid){
        System.out.println("进行商品删除的操作");
        return goodsService.deleteById(gid);
    }

//    @RequestMapping("/Pagelist")
//    public Page<GoodsEntity> listPage(@RequestBody Page<GoodsEntity> page){
//        return  goodsService.listPage(page);
//    }
}
