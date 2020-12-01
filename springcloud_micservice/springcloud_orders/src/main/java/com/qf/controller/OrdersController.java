package com.qf.controller;
import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.*;
import com.qf.serviceImpl.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/orders")
@Controller
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @RequestMapping("/list")
    @IsLogin(mustLoign = true)
    public String orderList(Model model){
        User user = UserHolder.getUser();
        List<OrdersEntity> ordersList = ordersService.queryByUid(user.getId());
        model.addAttribute("ordersList",ordersList);
        return "orderslist";
    }



}
