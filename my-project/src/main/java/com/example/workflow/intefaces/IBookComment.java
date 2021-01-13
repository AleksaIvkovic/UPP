package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.BookComment;

public interface IBookComment {
    void storeComment(BookComment bookComment);
    void deleteCommentsForBook(long bookId);
}
