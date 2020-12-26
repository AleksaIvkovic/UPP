package com.example.workflow.handlers;

import com.example.workflow.services.BookService;
import com.example.workflow.services.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtils {

    private static ServiceUtils instance;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    BookService bookService;

    @PostConstruct
    public void fillInstance() {instance = this;}

    public static SystemUserService getSystemUserService() { return instance.systemUserService;}

    public static BookService getBookService() { return instance.bookService;}
}
