package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IAuthority;
import com.example.workflow.models.Authority;
import com.example.workflow.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthority {
    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority findById(Long id) {
        Authority auth = this.authorityRepository.getOne(id);
        return auth;
    }

    @Override
    public Authority findByname(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        return auth;
    }
}
