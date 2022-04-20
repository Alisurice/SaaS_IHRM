package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.SalaryArchive;
import com.ihrm.domain.social_security.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 自定义dao接口继承
 *      JpaRepository<实体类，主键>
 *      JpaSpecificationExecutor<实体类>
 */
public interface ArchiveDao extends JpaRepository<SalaryArchive,String> ,JpaSpecificationExecutor<SalaryArchive> {

    SalaryArchive findByCompanyIdAndYearsMonth(String companyId,String yearMonth);
    List<SalaryArchive> findByCompanyIdAndYearsMonthLike(String companyId, String s);
}
