package com.sch.library;

import com.alibaba.fastjson.JSONObject;
import com.sch.library.ResultCache;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/user/*",filterName = "userPower")
public class PowerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String id = httpServletRequest.getSession().getAttribute("user_id")+"";
        if("".equals(id)){
            response.getWriter().write(JSONObject.toJSONString(ResultCache.PERMISSION_DENIED));
            return;
        }
        chain.doFilter(request,response);
    }
    @Override
    public void destroy() {}
}
