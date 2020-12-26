package com.example.workflow.services;

import com.example.workflow.intefaces.IPayment;
import com.example.workflow.models.CreditCardDetails;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentServiceMock implements IPayment {

    @Override
    public boolean Pay(CreditCardDetails creditCardDetails) {
        Random r = new Random();
        int result = r.nextInt(100);
        return result % 2 == 0;
    }
}
