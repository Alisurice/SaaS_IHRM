package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/company")
@Slf4j
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    //1.设置保存的企业id
    // TODO: 2021/12/14 企业id：目前使用固定值1，以后会解决
    @Value("${company-id}")
    private String companyId;

    @RequestMapping(value = "/department" ,method = RequestMethod.POST)
    public Result save(@RequestBody Department department){


        department.setCompanyId(super.companyId);

        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id，根据登录用户自动获取，不需要传入
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public Result findAll(){


        Company company = companyService.findById(super.companyId);

        List<Department> departmentList = departmentService.findAll(company.getId());

        DeptListResult deptListResult = new DeptListResult(company,departmentList);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        return new Result(ResultCode.SUCCESS, departmentService.findById(id));
    }

    /**
     * 修改Department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id, @RequestBody Department department){
        //1.设置修改的部门id
        department.setId(id);
        //2.调用service更新
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }



    /**
     *  根据部门编码和公司id查询部门
     */
    @RequestMapping(value = "/department/search" , method = RequestMethod.POST)
    public Department findByCode(@RequestParam("code") String code,@RequestParam("companyId") String companyId){
        return departmentService.findByCode(code , companyId);

    }
}
