package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DTOs.PublishedBookDTO;
import com.example.workflow.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService implements IBook {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public ArrayList<PublishedBookDTO> GetPublishedBooks() {
        ArrayList<PublishedBook> publishedBooks = bookRepository.getAllByPublishedTrue();
        ArrayList<PublishedBookDTO> publishedBooksDTO = new ArrayList<>();

        publishedBooks.forEach(book -> {
            PublishedBookDTO temp = modelMapper.map(book, PublishedBookDTO.class);
            temp.setGenre(book.getGenre().getName());
            temp.setWriter(book.getWriter().getFirstname() + " " + book.getWriter().getLastname());
            publishedBooksDTO.add(temp);
        });

        return publishedBooksDTO;
    }
}
