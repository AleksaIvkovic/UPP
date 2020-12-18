package com.example.workflow.intefaces;

import com.example.workflow.models.SysUser;

public interface ISystemUser {
    SysUser getSystemUserById(Long id);
    SysUser getSystemUserByUsername(String username);
    SysUser findSystemUserByToken(String token);
    void storeSystemUser(SysUser newSysUser);
    boolean checkUniqueUsername(String username);
    boolean checkUniqueEmail(String email);
    void createVerificationToken(SysUser sysUser, String token);
}