package com.example.workflow.repositories;

import com.example.workflow.models.DBs.PublishedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends JpaRepository<PublishedBook, Long> {
    PublishedBook getPublishedBookByTitle(String title);
    ArrayList<PublishedBook> getAllByPublishedTrue();
}
