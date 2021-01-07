package com.example.workflow.controllers;

import com.example.workflow.services.systemServices.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getBooks() {
        return new ResponseEntity<>(bookService.GetPublishedBooks(), HttpStatus.OK);
    }
}
