package com.stu217.helloserver.interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取本次请求的HTTP动词（GET/POST/PUT/DELETE）和请求URI（如/api/users/1001）
        String method = request.getMethod();
        String uri = request.getRequestURI();
        // 规则A：POST请求 + 路径精确等于/api/users → 放行（用户注册，无需鉴权）
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        // 规则B：GET请求 + 路径以/api/users/开头 → 放行（查询单个用户，无需鉴权）
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");
        // 3. 满足任一放行规则，直接放行，无需校验Token
        if (isCreateUser || isGetUser) {
            return true;
        }
        // 1. 从HTTP请求头中获取名为Authorization的令牌
        String token = request.getHeader("Authorization");
        // 2. 令牌为空/空字符串，直接拦截
        if (token == null || token.isEmpty()) {
            // 设置响应格式为JSON，编码UTF-8（防止中文乱码）
            response.setContentType("application/json;charset=UTF-8");
            // 构造401业务状态码的错误JSON
            String errorJson = "{\"code\":401,\"msg\":\"登录凭证已缺失,请重新登录\"}";
            response.getWriter().write(errorJson);
            return false;
        }
        return true;
    }
}
