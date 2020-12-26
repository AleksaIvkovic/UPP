package com.example.workflow.services;

import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.Authority;
import com.example.workflow.models.SysUser;
import com.example.workflow.repositories.SysUserRepository;
import com.example.workflow.models.VerificationToken;
import com.example.workflow.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class SystemUserService implements ISystemUser, UserDetailsService {
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
    public SysUser getSystemUserByEmail(String email) {
        SysUser sysUser = sysUserRepository.getSystemUserByEmail(email);
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

    @Override
    public boolean checkIfWriterExists(String fullname) {
        SysUser user = sysUserRepository.getSysUserByFirstnameAndLastname(fullname.split(" ")[0], fullname.split(" ")[1]);

        if (user == null) {
            return false;
        }

        return ((List<Authority>)user.getAuthorities()).get(0).getName().equals("WRITER");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.getSystemUserByUsername(s);

        if(user == null)
            throw new UsernameNotFoundException("User with " + s + " doesn't exists!");

        return user;
    }
}
