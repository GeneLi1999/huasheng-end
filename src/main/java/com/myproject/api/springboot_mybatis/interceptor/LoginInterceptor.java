package com.myproject.api.springboot_mybatis.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.myproject.api.springboot_mybatis.entity.Staff;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    //    在请求处理之前调用,只有返回true才会执行要执行的请求
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("UTF-8");
        String token=httpServletRequest.getHeader("token");
        if (null==token){
            Map<String,Object> map=new HashMap<>();
            map.put("data","token is null");
            map.put("code","400");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            httpServletResponse.getWriter().flush();
        }else {
            Staff s=redisTemplate.opsForValue().get(token);
            if (s!=null){
                //更新存储的token信息
                httpServletRequest.setAttribute("staffMessage",JSONObject.toJSON(s));
                return true;
            }
            Map<String,Object> map=new HashMap<>();
            map.put("data","token is null");
            map.put("code","401");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            httpServletResponse.getWriter().flush();
        }
        return false;
    }

    //    试图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //    在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}