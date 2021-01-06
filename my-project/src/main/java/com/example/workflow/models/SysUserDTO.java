package com.example.workflow.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserDTO {
    private String firstname;
    private String lastname;
    private String city;
    private String country;
    private String username;
    private String email;
    private Boolean isBeta;
    private String authority;
    private Boolean isActive;
}
