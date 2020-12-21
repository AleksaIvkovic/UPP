package com.example.workflow.intefaces;

import com.example.workflow.models.Authority;

public interface IAuthority {
    Authority findById(Long id);
    Authority findByname(String name);
}
