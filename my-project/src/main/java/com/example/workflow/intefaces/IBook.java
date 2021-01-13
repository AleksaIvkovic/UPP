package com.example.workflow.intefaces;

import com.example.workflow.models.DBs.BookComment;
import com.example.workflow.models.DBs.Genre;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.models.DTOs.PublishedBookDTO;
import org.camunda.bpm.engine.identity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface IBook {
    void storeBook(PublishedBook publishedBook);
    void removeBook(PublishedBook bookToDelete);
    PublishedBook getBookByTitle(String title);
    boolean checkUniqueTitle(String title);
    ArrayList<PublishedBookDTO> getPublishedBooks();
    void markAsPlagiarized(String title);
    String generateISBN();
    int getNumOfPages(int min, int max);
    void sendToPrint(String title);
    void storeComment(PublishedBook book, BookComment bookComment);
    PublishedBook storeInitialBookDetails(HashMap<String, Object> map, SysUser writer, Genre genre);
}
