package com.example.workflow.repositories;

import com.example.workflow.models.SubmittedFile;
import com.example.workflow.models.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<SubmittedFile, Long> {
}
