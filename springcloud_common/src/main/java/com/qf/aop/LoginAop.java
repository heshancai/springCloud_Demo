package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * 切面类
 *增强和切点
 * 每一个方法的在做核心业务织入增强
 * 进行登录的判断
 *
 */
@Component
@Aspect  //这是一个切面类
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 增强的方法
     * @return
     */
    //环绕增强 括号中设置需要增强的方法
    //@annotation(IsLogin):表示凡是放有islogin这个注解的地方都需要进行增强
    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){   //方法的参数放代理对象

        //判断登录的状态
        //1.获取cookie
        //2.通过获取到的cookie访问redis
        //3.通过从redis拿到登录的信息是否
        //  对象为空:未登录
        //  判断方法中放有islogin注解中的值 是否需要登录的验证
        //  @IsLogin(mustLogin=true)--》强制进行登录

        //不为空
        //用户信息进行保存 让开发者在controller中能够获得

        //获取登录的token(cookie)
        String loginToken=null;
        //获取上下文对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        //获取请求的对象
        HttpServletRequest request = requestAttributes.getRequest();
        //通过请求的对象获取所有的cookie
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies){
                //进行判断拿到 通过key拿到value
                if(cookie.getName().equals("loginToken")){
                    loginToken=cookie.getValue();
                    break;
                }
            }
        }

        //通过key值拿到redis的values值
        //用户的的信息
        User user=null;
        if(loginToken!=null){
             user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        //判断是否登录
        if(user ==null){
            //没有登录

            //判断注解中方法的返回值
            //先获取@IsLogin注解对象
            //通过代理对象拿到签名对象
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            //通过签名的对象拿到增强对象的反射对象
            Method method = methodSignature.getMethod();
            //通过强对象的反射对象拿到注解的对象
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            //注解反射对象拿到方法的返回值进行判断
            boolean flag = isLogin.mustLoign();

            //返回值为true 代表该方法需要的登录的验证
            if(flag){

                //进行强制的登录
                //拿到当前页跳转该方法的url
                //登录成功后跳转回该页面
                //String returnUrl=request.getRequestURL().toString()+"?"+ request.getQueryString();

                //得到请求地址url
                String returnUrl = "http://localhost"+request.getRequestURI();
                System.out.println(returnUrl);
                String returnUrl2=request.getRequestURL().toString();
                System.out.println(returnUrl2);
                //得到地址问号后面的参数
                String queryString = request.getQueryString();
                System.out.println(queryString);
                if(queryString!=null){
                    returnUrl=returnUrl+"?"+queryString;
                }
                //对url进行编码
                System.out.println(returnUrl);
                try {
                    returnUrl= URLEncoder.encode(returnUrl,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //强制跳回的登录页面
//                String loginUrl=""+returnUrl;

                return "redirect:http://localhost/sso/tologin?returnUrl="+returnUrl;
            }
        }
        //如果登录了user 不为空
        //将user的信息保存起来 到controller中使用
        UserHolder.setUser(user);
        //调用指定的目标方法
        Object reslut=null;
        try {
            //执行目标方法
            reslut=proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //目标方法执行完成后清空当前的线程user对象
        UserHolder.setUser(null);
        //返回目标方法的返回值
        return  reslut;

    }

}
