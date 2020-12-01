package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 */
@Controller
@RequestMapping("toPage")
public class PageController {

    //前台页面的跳转需要经过后台
    @RequestMapping("/{page}")
    public String toPage(@PathVariable  String page){
        return page;
    }
}
