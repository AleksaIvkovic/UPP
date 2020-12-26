package com.example.workflow.services;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.PublishedBook;
import com.example.workflow.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBook {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void StoreBook(PublishedBook publishedBook) {
        bookRepository.save(publishedBook);
    }

    @Override
    public void RemoveBook(PublishedBook publishedBook) {
        bookRepository.delete(publishedBook);
    }

    @Override
    public PublishedBook GetBookByTitle(String title) {
        return bookRepository.getPublishedBookByTitle(title);
    }

    @Override
    public boolean checkUniqueTitle(String title) {
        return bookRepository.getPublishedBookByTitle(title) == null;
    }
}
