package com.example.workflow.services.notifications;

import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.MailingService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotifyBetaReadersAboutManuscriptService implements JavaDelegate {

    @Autowired
    private MailingService mailingService;

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        List<String> selectedBetaReadersUsernames = new ArrayList<>();

        HashMap<String, Boolean> selectedBetaReadersHM = (HashMap<String, Boolean>)execution.getVariable("selectedBetaReadersForm");

        for (Map.Entry mapElement: selectedBetaReadersHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                selectedBetaReadersUsernames.add(mapElement.getKey().toString());
            }
        }

        String text = "You have new manuscript to read and review.";

        for (String username: selectedBetaReadersUsernames) {
            SysUser sysUser = systemUserService.getSystemUserByUsername(username);
            mailingService.sendMail("New manuscript to review",text, sysUser.getEmail());
        }

        execution.setVariable("selectedBetaReadersUsernames", selectedBetaReadersUsernames);

    }
}
