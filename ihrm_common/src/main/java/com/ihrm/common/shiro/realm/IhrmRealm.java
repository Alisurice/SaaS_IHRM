package com.ihrm.common.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.ihrm.domain.system.response.ProfileResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 公共的realm
 * @author: hyl
 * @date: 2020/02/08
 **/
@Slf4j
public class IhrmRealm extends AuthorizingRealm {

    public void setName(String name){
        super.setName("IhrmRealm");
    }


    //授权方法
    //授权:授权的目的就是根据认证数据获取到用户的权限信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*
        该做法会出现xxx cannot be cast to xxx
        ClassCastException: com.ihrm.domain.system.response.ProfileResult cannot be cast to com.ihrm.domain.system.response.ProfileResult
        出现这种情况的原因是 类加载器（ ClassLoader ) 不同导致的 类型转换错误，因为项目采用的是 spring-boot-devtools 热部署的方式，项目启动时加载项目中的类使用的加载器都是 org.springframework.boot.devtools.restart.classloader.RestartClassLoader ，而从 shiro session 中取出来的对象（从 redis 中取出经过反序列化）的类加载器都是sun.misc.Launcher.AppClassLoader 。两者不同导致的出现这种结果。
        参考：shiro 整合 redis 缓存出现 xxx cannot be cast to xxx
             https://blog.csdn.net/xhf852963/article/details/117553566
        //获取安全数据
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
         */
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();

        //获取安全数据
        //ProfileResult result = null;
        //Object object = principalCollection.getPrimaryPrincipal();
        //if (object instanceof ProfileResult) {
        //    result = (ProfileResult) object;
        //} else {
        //    result = JSON.parseObject(JSON.toJSON(object).toString(), ProfileResult.class);
        //}
        //log.debug(String.valueOf(result));
        //获取权限信息
        Set<String> apisPerms = (Set<String>) result.getRoles().get("apis");
        //构造权限信息,返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apisPerms);
        return info;
    }

    //认证方法
    //认证:认证的主要目的,比较y用户和密码是否与数据库中的一致
    //然后通过认证之后，会加载用户相应的安全数据（即权限数据），然后将安全数据存到shiro(比如的session中?)进行保管
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
