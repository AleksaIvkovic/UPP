package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.Authority;
import com.example.workflow.models.DBs.SysUser;

import java.util.List;

public interface ISystemUser {
    SysUser getSystemUserById(Long id);
    SysUser getSystemUserByUsername(String username);
    SysUser getSystemUserByEmail(String email);
    SysUser findSystemUserByToken(String token);
    void storeSystemUser(SysUser newSysUser);
    boolean checkUniqueUsername(String username);
    boolean checkUniqueEmail(String email);
    void createVerificationToken(SysUser sysUser, String token);
    boolean checkIfWriterExists(String fullName);
    void addPenaltyPoint(String username);
    void loseBetaStatus(SysUser user, List<Authority> authorities);
    void confirmEmail(String token);
}
