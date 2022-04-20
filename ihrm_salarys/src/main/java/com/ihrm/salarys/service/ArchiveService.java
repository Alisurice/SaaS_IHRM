package com.ihrm.salarys.service;

import com.alibaba.fastjson.JSON;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.atte.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.salarys.SalaryArchive;
import com.ihrm.domain.salarys.SalaryArchiveDetail;
import com.ihrm.domain.salarys.Settings;
import com.ihrm.domain.salarys.UserSalary;
import com.ihrm.domain.social_security.Archive;
import com.ihrm.domain.social_security.ArchiveDetail;
import com.ihrm.salarys.dao.ArchiveDao;
import com.ihrm.salarys.dao.ArchiveDetailDao;
import com.ihrm.salarys.dao.UserSalaryDao;
import com.ihrm.salarys.feign.AttendanceFeignClient;
import com.ihrm.salarys.feign.FeignClientService;
import com.ihrm.salarys.feign.SocialSecurityFeignClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * 归档service
 */
@Service
public class ArchiveService {

    @Autowired
    private ArchiveDao archiveDao;

    @Autowired
    private ArchiveDetailDao archiveDetailDao;

    @Autowired
    private FeignClientService feignClientService;

    @Autowired
    private AttendanceFeignClient attendanceFeignClient;

    @Autowired
    private SocialSecurityFeignClient socialSecurityFeignClient;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private UserSalaryDao userSalaryDao;

    @Autowired
    private SalaryService salaryService;


    /**
     * 根据企业和年月查询归档主表数据
     * @param yearMonth 年月
     * @param companyId 企业id
     * @return  企业和年月对应的归档主表数据
     */
    public SalaryArchive findSalaryArchive(String yearMonth, String companyId) {
        return archiveDao.findByCompanyIdAndYearsMonth(companyId , yearMonth);
    }

    /**
     * 根据归档的id查询所有的归档明细记录
     * @param id    归档id
     * @return  归档id对应的所有的归档明细记录
     */
    public List<SalaryArchiveDetail> findSalaryDetail(String id) {
        return archiveDetailDao.findByArchiveId(id);
    }

    public SalaryArchiveDetail findUserSalaryDetail(String userId) {
        return archiveDetailDao.findByUserId(userId);
    }
    /**
     *  查询月报表
     * @param yearMonth 年月
     * @param companyId 企业id
     * @return  对应企业id和年月的月报表数据
     */
    public List<SalaryArchiveDetail> getReports(String yearMonth, String companyId) {
        List<SalaryArchiveDetail> list = new ArrayList<>();
        //查询当前企业的福利津贴
        Settings setting = settingsService.findById(companyId);
        //查询所有的用户
        Page<Map> users = userSalaryDao.findPage(companyId, null);
        //遍历用户数据
        for (Map user : users.getContent()) {
            //构造SalaryArchiveDetail
            SalaryArchiveDetail saDetail = new SalaryArchiveDetail();
            saDetail.setUser(user);
            //获取每个用户社保数据
            Object obj = socialSecurityFeignClient.historyData(saDetail.getUserId(), yearMonth).getData();
            if (obj != null){
                ArchiveDetail socialInfo = JSON.parseObject(JSON.toJSONString(obj),  ArchiveDetail.class);
                if (socialInfo != null){
                    saDetail.setSocialInfo(socialInfo);
                    if (obj != null){
                        //获取每个用户考勤数据
                        obj = attendanceFeignClient.historyData(saDetail.getUserId(), yearMonth).getData();
                        if (obj != null){
                            ArchiveMonthlyInfo atteInfo = JSON.parseObject(JSON.toJSONString(obj),  ArchiveMonthlyInfo.class);
                            if (atteInfo != null){
                                saDetail.setAtteInfo(atteInfo);
                                //获取每个用户的薪资
                                UserSalary userSalary = salaryService.findUserSalary(saDetail.getUserId());
                                if (userSalary != null){
                                    saDetail.setUserSalary(userSalary);
                                    //计算工资
                                    saDetail.calSalary(setting);
                                }
                            }
                        }
                    }
                }
            }

            list.add(saDetail);
        }
        return list;
    }

//    /**
//     * 社保数据归档
//     */
//    public void archive(String yearMonth, String companyId) throws Exception {
//        //1.查询归档明细数据
//        List<SalaryArchiveDetail> archiveDetails = getReports(yearMonth, companyId);
//        //1.1 计算当月,企业与员工支出的所有社保金额
//        BigDecimal enterMoney = new BigDecimal(0);
//        BigDecimal personMoney = new BigDecimal(0);
//        for (SalaryArchiveDetail archiveDetail : archiveDetails) {
    //把这部分改一下，应该可以用反射，获取BigDecimal型的数据，然后批量操作。
//            BigDecimal t1 = archiveDetail.getProvidentFundEnterprises() == null ? new BigDecimal(0): archiveDetail.getProvidentFundEnterprises();
//            BigDecimal t2 = archiveDetail.getSocialSecurityEnterprise() == null ? new BigDecimal(0): archiveDetail.getSocialSecurityEnterprise();
//            BigDecimal t3 = archiveDetail.getProvidentFundIndividual() == null ? new BigDecimal(0): archiveDetail.getProvidentFundIndividual();
//            BigDecimal t4 = archiveDetail.getSocialSecurityIndividual() == null ? new BigDecimal(0): archiveDetail.getSocialSecurityIndividual();
//            enterMoney = enterMoney.add(t1).add(t2);
//            personMoney = enterMoney.add(t3).add(t4);
//        }
//        //2.查询当月是否已经归档
//        Archive archive = this.findArchive(companyId,yearMonth);
//        //3.不存在已归档的数据,保存
//        if(archive == null) {
//            archive = new Archive();
//            archive.setCompanyId(companyId);
//            archive.setYearsMonth(yearMonth);
//            archive.setId(idWorker.nextId()+"");
//        }
//        //4.如果存在已归档数据,覆盖
//        archive.setEnterprisePayment(enterMoney);
//        archive.setPersonalPayment(personMoney);
//        archive.setTotal(enterMoney.add(personMoney));
//        archiveDao.save(archive);
//        for (SalaryArchiveDetail archiveDetail : archiveDetails) {
//            archiveDetail.setId(idWorker.nextId() + "");
//            archiveDetail.setArchiveId(archive.getId());
//            archiveDetailDao.save(archiveDetail);
//        }
//    }

    /**
     * 通过年份查询归档数据
     * @param companyId	公司id
     * @param year	年份
     * @return	对应年份和公司id的归档数据
     */
    public List<SalaryArchive> findByYear(String companyId, String year) {
        return archiveDao.findByCompanyIdAndYearsMonthLike(companyId , year + "%");
    }

}
