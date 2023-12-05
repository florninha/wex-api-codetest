package com.test.wex.service;

import com.test.wex.common.JSONConverter;
import com.test.wex.common.ValidateObject;
import com.test.wex.model.PurchaseTransaction;
import com.test.wex.persistence.PurchaseTransactionEntity;
import com.test.wex.persistence.PurchaseTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseTransactionService {

    @Autowired
    public JSONConverter jsonConverter;

    @Autowired
    public ValidateObject validateObject;

    @Autowired
    public PurchaseTransactionRepository purchaseTransactionRepository;

    public String storePurchase(String payload) {
        PurchaseTransaction purchaseTransaction = jsonConverter.payloadToPurchaseTransaction(payload);
        validateObject.validatePurchaseTransaction(purchaseTransaction);

        PurchaseTransactionEntity purchaseTransactionEntity = new PurchaseTransactionEntity();
        purchaseTransactionEntity.setDescription(purchaseTransaction.getDescription());
        purchaseTransactionEntity.setTransactionDate(purchaseTransaction.getTransactionDate());
        purchaseTransactionEntity.setAmountUSD(String.format("%.2f", purchaseTransaction.getAmountUSD()));

        PurchaseTransactionEntity savedPurchaseTransaction = purchaseTransactionRepository.save(purchaseTransactionEntity);

        return "Purchase Transaction created! Id:" + savedPurchaseTransaction.getId();

    }
}
