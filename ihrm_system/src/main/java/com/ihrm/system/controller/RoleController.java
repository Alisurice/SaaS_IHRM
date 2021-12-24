package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.response.RoleResult;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 分配权限
     */
    @RequestMapping(value = "/role/assignPerm" , method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map){

        //获取被分配的角色id
        String roleId = (String) map.get("id");
        //获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //调用service完成权限分配
        roleService.assignPerms(roleId , permIds);

        return new Result(ResultCode.SUCCESS);
    }

    //添加角色
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Result add(@RequestBody Role role){
        role.setCompanyId(super.companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改角色
     */
    @RequestMapping(value = "/role/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody Role role){
        //设置修改的用户Id
        role.setId(id);
        //调用Service更新
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据ID获取角色信息
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Role role = roleService.findById(id);
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS,roleResult);
    }

    /**
     * 根据Id删除
     */
    @RequestMapping(value = "/role/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        roleService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分页查询角色
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result findByPage(int page,int pagesize) throws Exception {
        Page<Role> searchPage = roleService.findByPage(companyId, page, pagesize);
        PageResult<Role> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);
    }

    @RequestMapping(value = "/role/list" , method = RequestMethod.GET)
    public Result findAll(){
        List<Role> roleList = roleService.findAll(super.companyId);
        return new Result(ResultCode.SUCCESS , roleList);
    }


}
