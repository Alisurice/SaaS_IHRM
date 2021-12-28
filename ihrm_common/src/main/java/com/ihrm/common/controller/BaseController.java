package com.ihrm.common.controller;

import com.ihrm.domain.system.response.ProfileResult;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    public HttpServletRequest request;
    public HttpServletResponse response;

    //1.设置保存的企业id
    //@Value("${company-id}")
    protected String companyId;
    protected String companyName;
    protected String userId;
    protected Claims claims;


    //使用jwt获取
    //进入控制器之前执行的方法
/*    @ModelAttribute
    public void serResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;

        Object obj = (Claims) request.getAttribute("user_claims");
        if (obj != null){
            this.claims = (Claims) obj;
            this.companyId = (String) claims.get("companyId");
            this.companyName = (String) claims.get("companyName");
        }
    }*/

    //使用shiro获取
    //进入控制器之前执行的方法
    @ModelAttribute
    public void serResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;

        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全集合
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null && !principals.isEmpty()){
            //获取安全数据
            ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
            this.companyId = result.getCompanyId();
            this.companyName = result.getCompany();
            this.userId = result.getUserId();
        }
    }
}
