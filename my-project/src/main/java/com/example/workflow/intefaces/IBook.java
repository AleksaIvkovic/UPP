package com.example.workflow.intefaces;

import com.example.workflow.models.PublishedBook;

public interface IBook {
    void StoreBook(PublishedBook publishedBook);
    void RemoveBook(PublishedBook publishedBook);
    PublishedBook GetBookByTitle(String title);
    boolean checkUniqueTitle(String title);
}
