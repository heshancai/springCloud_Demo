package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 异常信息json数据实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =true)
public class ResultData<T> {
    private String code;//错误信息码
    private String msg;//错误信息
    private T data;//数据部分

    /**
     * 响应状态的列表
     */
    public static interface ResultCodeList{
        String OK="200";
        String ERROR="500";
    }
}
