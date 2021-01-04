package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.ICamunda;
import com.example.workflow.models.FormSubmissionDTO;
import com.example.workflow.models.ValidationError;
import org.camunda.bpm.engine.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class CamundaService implements ICamunda {
    @Autowired
    private FormService formService;

    @Override
    public HashMap<String, Object> mapListToDTO(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    @Override
    public ResponseEntity<?> trySubmitForm(String taskId, HashMap<String, Object> map) {
        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return new ResponseEntity<>(new ValidationError(e.toString().split("'")[1],e.toString().split("[()]+")[1].split("[.]")[4]), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
