package com.ihrm.salarys.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.salarys.SalaryArchive;
import com.ihrm.domain.salarys.SalaryArchiveDetail;
import com.ihrm.domain.social_security.Archive;
import com.ihrm.domain.social_security.ArchiveDetail;
import com.ihrm.salarys.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/salarys")
public class ArchiveController extends BaseController {

    @Autowired
    private ArchiveService archiveService;


    @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
    public Result userDetail(@PathVariable(value = "userId") String userId) {
        SalaryArchiveDetail details = archiveService.findUserSalaryDetail(userId);
        return new Result(ResultCode.SUCCESS, details);
    }
    /**
     * 制作薪资报表
     */
    @RequestMapping(value = "/reports/{yearMonth}" , method = RequestMethod.GET)
    public Result historyDetail(@PathVariable String yearMonth, int opType){
        List<SalaryArchiveDetail> list = new ArrayList<>();
        //判断opType是否为新制作的报表
        if (opType == 1){
            //新制作的报表
            list = archiveService.getReports(yearMonth , companyId);
        }else{
            //查询归档历史报表
            //1.查询主表数据
            SalaryArchive sa = archiveService.findSalaryArchive(yearMonth , companyId);
            //2.根据主表的id,查询明细表的所有数据
            if (sa != null){
                list = archiveService.findSalaryDetail(sa.getId());
            }
        }
        return new Result(ResultCode.SUCCESS , list);
    }


    /**
     * 查询历史归档列表
     */
    @RequestMapping(value = "/reports/{year}/list" , method = RequestMethod.GET)
    public Result historyList(@PathVariable String year){
        List<SalaryArchive> list = archiveService.findByYear(companyId , year);
        return new Result(ResultCode.SUCCESS , list);
    }


}
