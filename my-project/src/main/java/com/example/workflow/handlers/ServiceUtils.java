package com.example.workflow.handlers;

import com.example.workflow.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtils {

    private static ServiceUtils instance;

    @Autowired
    ReaderService readerService;

    @PostConstruct
    public void fillInstance() {instance = this;}

    public static ReaderService getReaderService() { return instance.readerService;}
}
