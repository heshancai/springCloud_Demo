package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
@Accessors(chain = true)
public class OrdersEntity extends BaseEntity {
    private String orderid;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private String code;
    private BigDecimal allprice;
    //放一个订单详情得集合
    @TableField(exist = false)
    private List<OrderDetilsEntity> OrderDetils;
}
