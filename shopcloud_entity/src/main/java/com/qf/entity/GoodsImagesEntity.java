package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("goods_images")
@Accessors(chain = true)
public class GoodsImagesEntity extends BaseEntity{
    //商品id
    private Integer gid;
    private String info;
    //图片地址
    private String url;
    //是否为封面
    private Integer isfengmian;

}
