package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orderdetils")
@Accessors(chain = true)
public class OrderDetilsEntity extends BaseEntity {

    //订单id
    private Integer oid;
    //商品的id
    private Integer gid;
    //商品的名称
    private String subject;
    private BigDecimal price;
    private Integer number;
    private String fmurl;
    //购物车小计
    private BigDecimal detilsPrice;


}
