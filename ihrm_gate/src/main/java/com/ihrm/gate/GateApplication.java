package com.ihrm.gate;


import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ihrm", exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableZuulProxy
public class GateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateApplication.class,args);
    }
}