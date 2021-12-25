package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class ProfileResult {
    private String mobile;
    private String username;
    private String company;
    private Map<String,Object> roles = new HashMap<>(); //感觉叫resourceGroup更正确
    //它首先是一个资源组，然后在资源组中，我们用资源的权限代码标象征一个资源？

    public ProfileResult(User user){
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();

        //Set可以过滤掉重名的
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        for (Role role : roles) {
            //根据roles获取PermissionList
            Set<Permission> perms = role.getPermissions();
            //再根据权限列表设置资源列表
            for (Permission perm : perms) {
                String code = perm.getCode();
                if(perm.getType() == 1) {
                    menus.add(code);
                }else if(perm.getType() == 2) {
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }

    public ProfileResult(User user, List<Permission> list){
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        //再根据权限列表设置资源列表
        for (Permission perm : list) {
            String code = perm.getCode();
            if(perm.getType() == 1) {
                menus.add(code);
            }else if(perm.getType() == 2) {
                points.add(code);
            }else {
                apis.add(code);
            }
        }

        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }
}
