package com.example.workflow.intefaces;

import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.PublishedBookDTO;

import java.util.ArrayList;

public interface IBook {
    void StoreBook(PublishedBook publishedBook);
    void RemoveBook(PublishedBook publishedBook);
    PublishedBook GetBookByTitle(String title);
    boolean checkUniqueTitle(String title);
    ArrayList<PublishedBookDTO> GetPublishedBooks();
}
