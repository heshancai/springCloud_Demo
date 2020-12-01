package com.qf.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * webSocket消息实体类
 */
@Data
@Accessors(chain = true)
public class WebSocketMsgEntity<T> implements Serializable {

    //发送方 -1 系统
    private Integer fromId=-1;
    //接收方
    private Integer toId;
    //消息类型 1 初始化消息 2 心跳消息 3秒杀消息提醒
    private Integer type;
    //消息携带的数据
    private T data;


}
