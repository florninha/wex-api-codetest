package com.test.wex.service;

import com.test.wex.common.JSONConverter;
import com.test.wex.common.ValidateObject;
import com.test.wex.model.PurchaseTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseTransactionService {

    @Autowired
    public JSONConverter jsonConverter;

    @Autowired
    public ValidateObject validateObject;

    public String storePurchase(String payload) {
        PurchaseTransaction purchaseTransaction = jsonConverter.payloadToPurchaseTransaction(payload);
        validateObject.validatePurchaseTransaction(purchaseTransaction);
        return purchaseTransaction.toString();

    }
}
