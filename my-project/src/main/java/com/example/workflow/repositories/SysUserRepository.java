package com.example.workflow.repositories;

import com.example.workflow.models.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser getSystemUserByUsername(String username);
    SysUser getSystemUserByEmail(String email);
    SysUser getSysUserByFirstnameAndLastname(String firstname, String lastname);
}
