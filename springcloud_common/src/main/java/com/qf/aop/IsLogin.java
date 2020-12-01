package com.qf.aop;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
//被该注解标注的注解 生成api文档时，会记录该注解
@Documented
//当前自定义的注解可以标注在方法上 注解的作用范围
@Target(ElementType.METHOD)
//当前自定义注解 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    //定义一个方法  方法的返回值默认值为false
    boolean mustLoign() default  false;
}
