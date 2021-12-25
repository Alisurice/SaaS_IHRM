package com.ihrm.system.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.ihrm.domain.system.User;

/**
 * @author: hyl
 * @date: 2020/01/09
 **/
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    public User findByMobile(String mobile);
}
