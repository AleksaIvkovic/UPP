package com.example.workflow.intefaces;

import com.example.workflow.models.CreditCardDetails;

public interface IPayment {
    boolean Pay(CreditCardDetails creditCardDetails);
}
