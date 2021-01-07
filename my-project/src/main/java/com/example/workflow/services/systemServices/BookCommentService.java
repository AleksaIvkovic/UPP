package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IBookComment;
import com.example.workflow.models.BookComment;
import com.example.workflow.repositories.BookCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCommentService implements IBookComment {
    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Override
    public void StoreComment(BookComment bookComment) {
        bookCommentRepository.save(bookComment);
    }
}
