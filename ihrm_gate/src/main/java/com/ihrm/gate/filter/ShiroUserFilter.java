package com.ihrm.gate.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ShiroUserFilter  extends UserFilter {
    /**
     * 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            log.debug("---------------shiro处理options------------------");
            log.debug(((HttpServletRequest) request).getRequestURI());
            setHeader(httpRequest,httpResponse);
            //options就没有必要继续往下走了，所以直接return false返回。
            //return true的话，回继续执行拦截链，最后导致这里设置好的响应内容被后续的shiro filter覆盖了。
            return false;
        }
        return super.preHandle(request,response);
    }
    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
