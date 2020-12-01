package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("goods")
public class GoodsEntity  extends BaseEntity{

    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;
    private Integer type=1;//商品的类型 1:普通商品 2:秒杀商品
    //商品的封面图片
    @TableField(exist = false)
    private String fmUrl;
    //图片的其他的图片
    @TableField(exist = false)
    private List<String> otherUrls=new ArrayList<>();
    //秒杀商品的信息
    @TableField(exist = false)
    private GoodsSecondKillEntity goodsSecondKillEntity;

    //添加商品的其他图片的方法
    public void  addOtherUrl(String url){
        otherUrls.add(url);
    }


}
