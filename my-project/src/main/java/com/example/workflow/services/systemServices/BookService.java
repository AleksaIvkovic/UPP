package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.DBs.BookComment;
import com.example.workflow.models.DBs.Genre;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.models.DTOs.PublishedBookDTO;
import com.example.workflow.repositories.BookRepository;
import org.camunda.bpm.engine.identity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class BookService implements IBook {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookCommentService bookCommentService;

    @Override
    public void storeBook(PublishedBook publishedBook) {
        bookRepository.save(publishedBook);
    }

    @Override
    public void removeBook(PublishedBook bookToDelete) {
        bookRepository.delete(bookToDelete);
    }

    @Override
    public PublishedBook getBookByTitle(String title) {
        return bookRepository.getPublishedBookByTitle(title);
    }

    @Override
    public boolean checkUniqueTitle(String title) {
        return bookRepository.getPublishedBookByTitle(title) == null;
    }

    @Override
    public ArrayList<PublishedBookDTO> getPublishedBooks() {
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

    @Override
    public void markAsPlagiarized(String title) {
        PublishedBook publishedBook = getBookByTitle(title);
        publishedBook.setPlagiarism(true);
        storeBook(publishedBook);
    }

    @Override
    public String generateISBN() {
        String isbn = "";
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            isbn += Integer.toString(rand.nextInt(10));
            if (i == 0 || i == 4 || i == 8) {
                isbn += "-";
            }
        }

        return isbn;
    }

    @Override
    public int getNumOfPages(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public void sendToPrint(String title) {
        PublishedBook publishedBook = getBookByTitle(title);

        publishedBook.setISBN(generateISBN());
        publishedBook.setPublished(true);
        publishedBook.setKeyWords("word1, word2, word3");
        publishedBook.setNumberOfPages(getNumOfPages(150, 200));
        publishedBook.setPlaceOfPublication("Novi Sad");
        publishedBook.setPublisher("Vulkan");
        publishedBook.setYearOfPublication(Year.now().getValue());

        storeBook(publishedBook);
    }

    @Override
    public void storeComment(PublishedBook book, BookComment bookComment) {
        List<BookComment> comments = book.getBookComments();
        comments.add(bookComment);
        book.setBookComments(comments);
        storeBook(book);
    }

    @Override
    public PublishedBook storeInitialBookDetails(HashMap<String, Object> map, SysUser writer, Genre genre) {
        PublishedBook newPublishedBook = new PublishedBook();
        newPublishedBook.setTitle(map.get("title").toString());
        newPublishedBook.setPublished(false);
        newPublishedBook.setGenre(genre);
        newPublishedBook.setSynopsis(map.get("synopsis").toString());
        newPublishedBook.setWriter(writer);

        storeBook(newPublishedBook);
        return newPublishedBook;
    }
}
