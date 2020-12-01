package com.qf.controller;

import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/sso")
@Controller
public class SsoController {

    //登录页面的跳转
    @RequestMapping("/tologin")
    public String login(){
        return "login";
    }
    //注册页面的跳转
    @RequestMapping("/toregister")
    public String register(){
        return "register";
    }

    //注入用户的微服务对象
    @Autowired
    private IUserService userService;
    //redis操作对象
    @Autowired
    private RedisTemplate redisTemplate;
    //用户注册
    @RequestMapping("/register")
    public String register(User user, Model model){

        int register = userService.register(user);

        if(register>0){
            return "login";
        }else if(register==-1){
            model.addAttribute("msg","用户名已经存在");
        }else {
            model.addAttribute("msg","注册失败");
        }

        return "register";
    }

    //用户进行登录
    @RequestMapping("/login")
    public String login(String username, String password, String returnUrl, Model model, HttpServletResponse response){

        //根据用户名查询是否存在对象
        User user = userService.queryByUserName(username);
        //判断对象是否为空 并且密码进行比对
        if(user!=null && user.getPassword().equals(password)){
            //不为空，保存登录的状态到redis中
            System.out.println("登录成功:"+user);
            //uuid作为redis的唯一标识 作为token
            String token = UUID.randomUUID().toString();
            //将对象的存在redis中
            redisTemplate.opsForValue().set(token,user);
            //设置token有效时间 最作为判断的条件
            redisTemplate.expire(token,7, TimeUnit.DAYS);
            //将token 最为cookie的值
            Cookie cookie=new Cookie("loginToken",token);
            //设置cookie跨域请求时的设置
            //cookie存活时间
            cookie.setMaxAge(60*60*24*7);
            //设置可以访问该cookie的请求
            cookie.setPath("/");
            response.addCookie(cookie);

            //判断当前登录的页面携带的返回的url是否为空
            if(returnUrl==null || returnUrl.equals("")){
                //为空返回首页地址
                returnUrl = "http://localhost";
            }
            //登录成功后重定向回首页
            return "redirect:"+returnUrl;
        }
        //登录失败
        model.addAttribute("msg","用户名或密码错误");
        return "login";


    }


    /**
     * 判断是否登录
     * 解决ajax请求跨域问题
     * jsonp解决的方案
     * @return
     */
    @ResponseBody
    @RequestMapping("/isologin")
    public ResultData<User> isologin(@CookieValue(name = "loginToken",required = false)String loginToken){
        System.out.println("获取客户端的cookie"+loginToken);
        //判断是否为空cookie
        if(loginToken!=null){
            //根据cookie值从redis中拿用户的信息
            User user=(User) redisTemplate.opsForValue().get(loginToken);
            if(user!=null){
                //已经登录
                return new ResultData<User>().setCode(ResultData.ResultCodeList.OK).setData(user);
            }
        }
        return new ResultData<User>().setCode(ResultData.ResultCodeList.ERROR).setMsg("未登录");
    }

    /**
     * 注销的请求
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken")String loginToken,HttpServletResponse response){
        //得到前台传过来的cookie
        System.out.println("注销请求:"+loginToken);
        //删除redis上的标识
        redisTemplate.delete(loginToken);
        //前台的cookie失效
        Cookie cookie=new Cookie("loginToken",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return "login";
    }



}
