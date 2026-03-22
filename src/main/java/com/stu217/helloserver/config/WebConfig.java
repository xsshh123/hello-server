package com.stu217.helloserver.config;
import com.stu217.helloserver.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    @Configuration // 标记为Spring配置类，接管MVC配置
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AuthInterceptor()) // 注册自定义鉴权拦截器
                    .addPathPatterns("/api/**") // 拦截规则：/api下的所有请求（包括子路径）
                    .excludePathPatterns( // 放行规则：指定接口不拦截
                            "/api/users/login" // 登录接口（核心放行，无需鉴权）
                            //"/api/users"       // 新增用户接口（注册，无需鉴权）
                            //"/api/users/*" //放行：获取用户信息接口
                    );
        }
}

