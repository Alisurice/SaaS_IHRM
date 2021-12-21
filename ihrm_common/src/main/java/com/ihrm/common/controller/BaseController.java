package com.ihrm.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    public HttpServletRequest request;
    public HttpServletResponse response;

    //1.设置保存的企业id
    // TODO: 2021/12/14 企业id：目前使用固定值1，以后会解决
    @Value("${company-id}")
    protected String companyId;
    //protected String companyId;

    protected String companyName;
    protected String userId;



    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;

    }
}
