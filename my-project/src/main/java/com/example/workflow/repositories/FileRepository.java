package com.example.workflow.repositories;

import com.example.workflow.models.DBs.SubmittedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<SubmittedFile, Long> {
    List<SubmittedFile> getAllByProcessId(String processId);
}
