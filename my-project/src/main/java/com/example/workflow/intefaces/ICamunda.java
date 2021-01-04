package com.example.workflow.intefaces;

import com.example.workflow.models.FormSubmissionDTO;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;

public interface ICamunda {
    HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list);
    ResponseEntity<?> trySubmitForm(String taskId, HashMap<String, Object> map);
}
