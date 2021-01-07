package com.example.workflow.utils;

import com.example.workflow.services.systemServices.BookService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtils {
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    BookService bookService;

    private static ServiceUtils instance;

    @PostConstruct
    public void fillInstance() {instance = this;}

    public static SystemUserService getSystemUserService() { return instance.systemUserService;}

    public static BookService getBookService() { return instance.bookService;}
}
