package com.example.workflow.repositories;

import com.example.workflow.models.DBs.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    void deleteBookCommentByPublishedBookId(long bookId);
}
