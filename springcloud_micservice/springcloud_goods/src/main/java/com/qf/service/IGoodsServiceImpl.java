package com.qf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsImagesMapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodskillMapper;
import com.qf.entity.GoodsEntity;
import com.qf.entity.GoodsImagesEntity;
import com.qf.entity.GoodsSecondKillEntity;
import com.qf.util.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
//配置缓存库的名称 统一配置
@CacheConfig(cacheNames = "goods")
public class IGoodsServiceImpl implements IGoodsService {

    //注入数据源
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImagesMapper goodsImagesMapper;
    @Autowired
    private GoodskillMapper goodskillMapper;
    //注入队列的服务
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //注入缓存的服务
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 添加商品服务
     * @param goodsEntity
     * @return
     */
    //添加事务管理
    @Transactional
    @Override
    //在方法执行前,删除指定的缓存(操作商品类型为秒杀的商品)   开始的时间作为商品的key值
    @CacheEvict(key = "'kill_'+#goodsEntity.goodsSecondKillEntity.startTime.time",condition = "#goodsEntity.type==2")
    public int insertGoods(GoodsEntity goodsEntity) {
        //添加商品
        goodsMapper.insert(goodsEntity);
        //添加商品的封面图片
        GoodsImagesEntity goodsImagesEntity=new GoodsImagesEntity()
                .setGid(goodsEntity.getId())
                .setIsfengmian(1)
                .setUrl(goodsEntity.getFmUrl());
        goodsImagesMapper.insert(goodsImagesEntity);


        //添加商品的其他图片
        for (String url: goodsEntity.getOtherUrls()) {
            GoodsImagesEntity goodsImagesEntity1=new GoodsImagesEntity()
                    .setGid(goodsEntity.getId())
                    .setIsfengmian(0)
                    .setUrl(url);
            goodsImagesMapper.insert(goodsImagesEntity1);
        }

        //TODO 添加秒杀商品的信息
        if(goodsEntity.getType()==2){
            //为秒杀产品  添加秒杀的信息
            GoodsSecondKillEntity goodsSecondKillEntity=goodsEntity.getGoodsSecondKillEntity()
                    .setGid(goodsEntity.getId());
            goodskillMapper.insert(goodsSecondKillEntity);

            //TODO 将秒杀商品id放入 秒杀的场次的时间作为key 放到redis中 防止提前下单的功能
            //1.获取秒杀商品的场次的时间的字符串                年月日时
            String time = DateUtil.dateToString(goodsSecondKillEntity.getStartTime(), "yyMMddHH");
            //2.存到redis中  放入商品的id  放入set集合中
            redisTemplate.opsForSet().add("killgoods_"+time,goodsEntity.getId()+"");
        }

        //TODO 将商品的信息放到rabbitmq队列中，以及生成静态页
        rabbitTemplate.convertAndSend("kill_exchange","",goodsEntity);
        return 1;
    }


    /**
     * 查询商品详情信息
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<GoodsEntity> goodsList() {
        List<GoodsEntity> goodsEntityList = goodsMapper.selectList(null);

        //根据商品的id查询商品的图片
        for (GoodsEntity goodsEntity : goodsEntityList) {
            //查询相关的额图片信息
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("gid",goodsEntity.getId());
            List<GoodsImagesEntity> imagesEntities = goodsImagesMapper.selectList(queryWrapper);

            //进行设置图片的操作
            for (GoodsImagesEntity goodsImagesEntity : imagesEntities) {
                    //判断设值
                if(goodsImagesEntity.getIsfengmian()==1){
                    //封面图片
                    goodsEntity.setFmUrl(goodsImagesEntity.getUrl());
                }else {
                    //非封面
                    goodsEntity.addOtherUrl(goodsImagesEntity.getUrl());

                }
            }
            //查询秒杀的信息
            if(goodsEntity.getType()==2){
                //根据id查询秒杀商品的信息
                GoodsSecondKillEntity goodsSecondKillEntity = goodskillMapper.selectOne(queryWrapper);
                goodsEntity.setGoodsSecondKillEntity(goodsSecondKillEntity);
            }

        }
        return goodsEntityList;
    }

    /**
     * 根据时间的来查询对应的场次商品信息
     * @param date
     * @return
     */
    @Override
    //缓存的查询 缓存有了将不会查询数据库 缓存没有的话将查询数据库并且缓存的重建
    @Cacheable(key = "'kill_'+#date.time")
    public List<GoodsEntity> queryKillList(Date date) {
        System.out.println(date.getTime());
        System.out.println("查询了数据库");

        QueryWrapper queryWrapper=new QueryWrapper();
        //根据开始的时间进行查询
        queryWrapper.eq("start_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        List<GoodsSecondKillEntity> goodsSecondKillEntities = goodskillMapper.selectList(queryWrapper);

        List<GoodsEntity> goodsEntityList=new ArrayList<>();
        //根据秒杀的信息查询商品的信息
        for (GoodsSecondKillEntity goodsSecondKillEntity : goodsSecondKillEntities) {
            GoodsEntity goodsEntity = goodsMapper.selectById(goodsSecondKillEntity.getGid());
            goodsEntity.setGoodsSecondKillEntity(goodsSecondKillEntity);


            //查询相关的额图片信息
            QueryWrapper queryWrapper2=new QueryWrapper();
            queryWrapper2.eq("gid",goodsEntity.getId());
            List<GoodsImagesEntity> imagesEntities = goodsImagesMapper.selectList(queryWrapper2);

            //进行设置图片的操作
            for (GoodsImagesEntity goodsImagesEntity : imagesEntities) {
                //判断设值
                if(goodsImagesEntity.getIsfengmian()==1){
                    //封面图片
                    goodsEntity.setFmUrl(goodsImagesEntity.getUrl());
                }else {
                    //非封面
                    goodsEntity.addOtherUrl(goodsImagesEntity.getUrl());
                }
            }
            goodsEntityList.add(goodsEntity);
        }

        return goodsEntityList;
    }

    /**
     * 根据商品的id查询商品信息
     * @param gid
     * @return
     */
    @Override
    public GoodsEntity queryByGid(Integer gid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("gid",gid);
        GoodsEntity goodsEntity = goodsMapper.selectById(gid);
        //根据商品的id查询商品的图片
        //查询相关的额图片信息
        List<GoodsImagesEntity> imagesEntities = goodsImagesMapper.selectList(queryWrapper);

        //进行设置图片的操作
            for (GoodsImagesEntity goodsImagesEntity : imagesEntities) {
                //判断设值
                if(goodsImagesEntity.getIsfengmian()==1){
                    //封面图片
                    goodsEntity.setFmUrl(goodsImagesEntity.getUrl());
                }else {
                    //非封面
                    goodsEntity.addOtherUrl(goodsImagesEntity.getUrl());

                }
            }
            //查询秒杀的信息
            if(goodsEntity.getType()==2){
                //根据id查询秒杀商品的信息
                GoodsSecondKillEntity goodsSecondKillEntity = goodskillMapper.selectOne(queryWrapper);
                goodsEntity.setGoodsSecondKillEntity(goodsSecondKillEntity);
            }

        return goodsEntity;
    }

    /**
     * 删除商品
     * @param gid
     * @return
     */
    @Override
    public int deleteById(Integer gid) {
        goodsMapper.deleteById(gid);
        return goodsMapper.deleteById(gid);
    }

    /**
     * 商品查询参与分页
     * @param page
     * @return
     */
//    @Override
//    public Page<GoodsEntity> listPage(Page<GoodsEntity> page) {
//        return goodsMapper;
//    }
}
