package com.example.workflow.services.db;

import com.example.workflow.intefaces.IBook;
import com.example.workflow.models.DBs.PublishedBook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Random;

@Service
public class SendToPrintService implements JavaDelegate {
    @Autowired
    private IBook bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PublishedBook publishedBook = bookService.GetBookByTitle(delegateExecution.getVariable("bookTitle").toString());

        publishedBook.setISBN(generateISBN());
        publishedBook.setPublished(true);
        publishedBook.setKeyWords("word1, word2, word3");
        publishedBook.setNumberOfPages(getNumOfPages(150, 200));
        publishedBook.setPlaceOfPublication("Novi Sad");
        publishedBook.setPublisher("Vulkan");
        publishedBook.setYearOfPublication(Year.now().getValue());

        bookService.StoreBook(publishedBook);
    }

    private String generateISBN() {
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

    private int getNumOfPages(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
