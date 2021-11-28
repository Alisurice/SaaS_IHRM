package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存用户
     *  1.配置idwork到工程
     *  2.在service中注入idwork
     *  3.通过idwork生成id
     *  4.保存用户
     */
    public void add(Company company){
        String id = idWorker.nextId()+"";
        company.setId(id);
        company.setAuditState("0");
        company.setState(1);
        companyDao.save(company);
    }

    public void update(Company company){
        //查到相应的id就保存，没查到get方法会抛异常，详情ctrl Q 看文档
        Company temp = companyDao.findById(company.getId()).get();
        companyDao.save(company);
    }
    /**
     * 删除用户
     */
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    public Company findById(String id) {
        return companyDao.findById(id).get();
    }

    /**
     * 查询用户列表
     */
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    public Company save(Company company) {
        return companyDao.save(company);
    }

}
