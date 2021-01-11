package com.example.workflow.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetSelectedBetaReadersUsernamesService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> map = (HashMap<String, Object>)execution.getVariable("selectedBetaReadersForm");
        HashMap<String, Boolean> selectedBetaReadersHM = (HashMap<String, Boolean>)map.get("betaReaders_1");
        List<String> selectedBetaReadersUsernames = new ArrayList<>();

        for (Map.Entry mapElement: selectedBetaReadersHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                selectedBetaReadersUsernames.add(mapElement.getKey().toString());
            }
        }
        execution.setVariable("selectedBetaReadersUsernames", selectedBetaReadersUsernames);
    }
}
