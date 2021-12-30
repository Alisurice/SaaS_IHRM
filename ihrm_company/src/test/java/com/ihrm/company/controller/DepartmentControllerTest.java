package com.ihrm.company.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DepartmentControllerTest {

    private int companyPort = 9001;
    private int sysPort = 9002;
    RestTemplate restTemplate = new RestTemplate();
    String loginResult = "";

    @Test
    public void testShiroLogin(){
        Map<String, String> form = new HashMap<>();
        form.put("mobile", "2341");
        form.put("password", "123456");

        String result = restTemplate.postForObject("http://localhost:"+sysPort+"/sys/login", form, String.class);
        System.out.println(result);
        loginResult =  result;
    }
    @Test
    public void findByCode() throws IOException {
        //得先登录，完成安全数据的构造
        ObjectMapper mapper = new ObjectMapper();
        DepartmentControllerTest controller = new DepartmentControllerTest();
        controller.testShiroLogin();
        JsonNode jsonNode = mapper.readTree(controller.loginResult);
        String json = "Bearer "+jsonNode.get("data").asText();
        System.out.println(json);

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", json);



        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("code", "DEPT-DEV");
        form.add("companyId", "1");
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(form, headers);

        String result = restTemplate.postForObject("http://localhost:"+companyPort+"/company/department/search", formEntity, String.class);
        System.out.println(result);
    }
}