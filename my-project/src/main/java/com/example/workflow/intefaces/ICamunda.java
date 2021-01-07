package com.example.workflow.intefaces;

import com.example.workflow.models.DTOs.FormSubmissionDTO;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;

public interface ICamunda {
    HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list);
    ResponseEntity<?> trySubmitForm(String taskId, HashMap<String, Object> map);
    ResponseEntity<?> getNextTask(String taskName, String processInstanceId);
}
