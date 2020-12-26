package com.example.workflow.repositories;

import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<PublishedBook, Long> {
    PublishedBook getPublishedBookByTitle(String title);
}
