package com.example.workflow.controllers;

import com.example.workflow.models.DBs.Authority;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.models.DTOs.SysUserDTO;
import com.example.workflow.models.UserTokenState;
import com.example.workflow.security.TokenUtils;
import com.example.workflow.models.JwtAuthenticationRequest;
import com.example.workflow.services.systemServices.SystemUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SystemUserService userDetailsService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        if (!systemUserService.getSystemUserByUsername(authenticationRequest.getUsername()).isConfirmed()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        if(authentication == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwt = tokenUtils.generateToken(details.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loggedInUser")
    public ResponseEntity<?> GetLoggedInSysUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();
        SysUserDTO sysUserDTO = modelMapper.map(sysUser, SysUserDTO.class);
        sysUserDTO.setAuthority(((List<Authority>)sysUser.getAuthorities()).get(0).getName());
        sysUserDTO.setIsActive(sysUser.isActive());

        return new ResponseEntity<>(sysUserDTO, HttpStatus.OK);
    }

}
