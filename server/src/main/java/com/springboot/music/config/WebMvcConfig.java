package com.springboot.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/*
 * 解决跨域问题，启动项目时会自动加载这个类里面的方法
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //所有跨域目录都可以访问
                .allowedHeaders("*")
                .allowedOrigins("*")  //允许那些网站进行跨域访问
                .allowedMethods("*")
                .allowCredentials(true);  //访问时需要验证
    }
}

//@Configuration//spring2.0以上
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOrigins("*")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//        super.addCorsMappings(registry);
//    }
//}
