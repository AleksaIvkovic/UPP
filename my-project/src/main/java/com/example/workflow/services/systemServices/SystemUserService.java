package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.DBs.Authority;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.repositories.SysUserRepository;
import com.example.workflow.models.DBs.VerificationToken;
import com.example.workflow.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemUserService implements ISystemUser, UserDetailsService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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
    public boolean checkIfWriterExists(String fullName) {
        ArrayList<SysUser> users = sysUserRepository.getSysUsersByFirstnameAndLastname(fullName.split(" ")[0], fullName.split(" ")[1]);

        if (users == null) {
            return false;
        }

        for(SysUser user: users){
            if(((List<Authority>)user.getAuthorities()).get(0).getName().equals("WRITER")){
                return true;
            }
        }

        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = getSystemUserByUsername(s);

        if(user == null)
            throw new UsernameNotFoundException("User with " + s + " doesn't exists!");

        return user;
    }

    @Override
    public void addPenaltyPoint(String username) {
        SysUser user = getSystemUserByUsername(username);
        user.setPoints(user.getPoints() + 1);
        storeSystemUser(user);
    }

    @Override
    public void loseBetaStatus(SysUser user, List<Authority> authorities) {
        user.getBetaGenres().clear();
        user.getAuthorities().clear();
        user.setBeta(false);
        //storeSystemUser(user);
        user.setAuthorities(authorities);
        storeSystemUser(user);
    }

    @Override
    public void confirmEmail(String token) {
        SysUser sysUser = findSystemUserByToken(token);

        if (sysUser != null){
            sysUser.setConfirmed(true);
            storeSystemUser(sysUser);
        }
    }
}
