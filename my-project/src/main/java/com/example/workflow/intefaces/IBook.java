package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DTOs.PublishedBookDTO;

import java.util.ArrayList;

public interface IBook {
    void StoreBook(PublishedBook publishedBook);
    void RemoveBook(PublishedBook publishedBook);
    PublishedBook GetBookByTitle(String title);
    boolean checkUniqueTitle(String title);
    ArrayList<PublishedBookDTO> GetPublishedBooks();
}
