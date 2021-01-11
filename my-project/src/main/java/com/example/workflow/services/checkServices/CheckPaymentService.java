package com.example.workflow.services.checkServices;

import com.example.workflow.models.CreditCardDetails;
import com.example.workflow.services.systemServices.PaymentServiceMock;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CheckPaymentService implements JavaDelegate {
    @Autowired
    private PaymentServiceMock paymentService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> map = (HashMap<String, Object>)delegateExecution.getVariable("creditCard");

        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setNumber(map.get("number").toString());
        creditCardDetails.setOwner(map.get("owner").toString());
        creditCardDetails.setExpirationDate(map.get("date").toString());
        creditCardDetails.setCrc(map.get("crc").toString());

        if (paymentService.Pay(creditCardDetails)) {
            delegateExecution.setVariable("successfulPayment", true);
        } else {
            delegateExecution.setVariable("successfulPayment", false);
        }
    }
}
