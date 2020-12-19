package com.example.workflow.services;

import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.SysUser;
import com.example.workflow.repositories.SysUserRepository;
import com.example.workflow.models.VerificationToken;
import com.example.workflow.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

@Service
public class SystemUserService implements ISystemUser {
    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public SysUser getSystemUserById(Long id) {
        SysUser sysUser = sysUserRepository.getOne(id);
        if (sysUser == null) {
            throw new NotFoundException();
        }
        return sysUser;
    }

    @Override
    public SysUser getSystemUserByUsername(String username) {
        SysUser sysUser = sysUserRepository.getSystemUserByUsername(username);
        if (sysUser == null) {
            throw new NotFoundException();
        }
        return sysUser;
    }

    @Override
    public SysUser findSystemUserByToken(String token) {
        SysUser sysUser = verificationTokenRepository.findByToken(token).getReader();
        return sysUser;
    }

    @Override
    public void storeSystemUser(SysUser newSysUser) {
        sysUserRepository.save(newSysUser);
    }

    @Override
    public boolean checkUniqueUsername(String username) {
        return sysUserRepository.getSystemUserByUsername(username) == null;
    }

    @Override
    public boolean checkUniqueEmail(String email) {
        return sysUserRepository.getSystemUserByEmail(email) == null;
    }

    @Override
    public void createVerificationToken(SysUser sysUser, String token) {
        VerificationToken myToken = new VerificationToken(token, sysUser);
        verificationTokenRepository.save(myToken);
    }
}
