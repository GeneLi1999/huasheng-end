package com.myproject.api.springboot_mybatis.config;

import com.myproject.api.springboot_mybatis.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor getMyInterceptor(){return new LoginInterceptor();}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(getMyInterceptor());
        // 添加拦截请求
        ir.addPathPatterns("/file/getOperator");
        ir.addPathPatterns("/file/getChecker");
        ir.addPathPatterns("/file/checkpass");
        ir.addPathPatterns("/file/checknotpass");
        ir.addPathPatterns("/file/GetAllContract");
        ir.addPathPatterns("/file/GetAllContractChecker");
        ir.addPathPatterns("/file/upload");
        ir.addPathPatterns("/project/getAllProject");
        ir.addPathPatterns("/project/getCheckProject");
        ir.addPathPatterns("/project/insert");
        ir.addPathPatterns("/project/submit");
        ir.addPathPatterns("/project/pass");
        ir.addPathPatterns("/project/refuse");
        //ir.addPathPatterns("staff/*");
        // 添加不拦截的请求
        //ir.excludePathPatterns("/staff/login");
        ir.excludePathPatterns("/static/**");

        // 以上三句代码可以使用下面的代替
        // registry.addInterceptor(new MyInterceptor()).addPathPatterns("/*").excludePathPatterns("/login");
    }
}