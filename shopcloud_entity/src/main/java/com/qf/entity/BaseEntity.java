package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private Integer status=1;
    //主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createTime=new Date();

}
