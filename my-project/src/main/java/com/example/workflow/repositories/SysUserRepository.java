package com.example.workflow.repositories;

import com.example.workflow.models.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser getSystemUserByUsername(String username);
    SysUser getSystemUserByEmail(String email);
    ArrayList<SysUser> getSysUsersByFirstnameAndLastname(String firstname, String lastname);
}
