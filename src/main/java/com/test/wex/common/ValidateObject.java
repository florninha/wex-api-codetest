package com.test.wex.common;

import com.test.wex.model.PurchaseTransaction;
import com.test.wex.persistence.PurchaseTransactionEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.StrictMath.round;


@Component
public class ValidateObject {

    public void validatePurchaseTransaction(PurchaseTransaction pt) {
        if (pt.getDescription().length() > 50){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description must be 50 or less characters");
        }
        if (pt.getAmountUSD() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be a value above 0");
        }

    }

    public void validatePurchaseTransaction(PurchaseTransactionEntity purchaseTransactionEntity) {
        try {
            purchaseTransactionEntity.isNonExistent();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id!");
        }
    }
}
