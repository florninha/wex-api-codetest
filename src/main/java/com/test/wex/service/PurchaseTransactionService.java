package com.test.wex.service;

import com.test.wex.common.HTTPRequest;
import com.test.wex.common.JSONConverter;
import com.test.wex.common.ValidateObject;
import com.test.wex.model.PurchaseTransaction;
import com.test.wex.model.TRRECurrency;
import com.test.wex.persistence.PurchaseTransactionEntity;
import com.test.wex.persistence.PurchaseTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class PurchaseTransactionService {

    @Autowired
    public JSONConverter jsonConverter;

    @Autowired
    public ValidateObject validateObject;

    @Autowired
    public PurchaseTransactionRepository purchaseTransactionRepository;
    @Autowired
    public HTTPRequest httpRequest;

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

    public String retrievePurchaseById(Integer id) {
        PurchaseTransactionEntity savedPurchaseTransaction = purchaseTransactionRepository.getReferenceById(id);
        validateObject.validatePurchaseTransaction(savedPurchaseTransaction);
        PurchaseTransaction purchaseTransaction = fillPurchaseTransactionWithPurchaseTransactionEntity(savedPurchaseTransaction);
        String JSONResponse = httpRequest.requestCurrencyByDate(purchaseTransaction.getTransactionDate());
        List<TRRECurrency> currencyList = jsonConverter.payloadToListOfCurrency(JSONResponse);
        List<PurchaseTransaction> purchaseTransactionList = fillPurchaseTransactionWithCurrency(purchaseTransaction, currencyList);

        return jsonConverter.purchaseTransactionListToPayload(purchaseTransactionList);
    }

    public PurchaseTransaction fillPurchaseTransactionWithPurchaseTransactionEntity(PurchaseTransactionEntity purchaseTransactionEntity) {
        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

        purchaseTransaction.setId(purchaseTransactionEntity.getId());
        purchaseTransaction.setDescription(purchaseTransactionEntity.getDescription());
        purchaseTransaction.setTransactionDate(purchaseTransactionEntity.getTransactionDate());
        purchaseTransaction.setAmountUSD(Double.parseDouble(purchaseTransactionEntity.getAmountUSD()));

        return purchaseTransaction;
    }
    public List<PurchaseTransaction> fillPurchaseTransactionWithCurrency(PurchaseTransaction purchaseTransaction, List<TRRECurrency> currencyList) {
        List<PurchaseTransaction> purchaseTransactionList = new ArrayList<>();

        currencyList.forEach(currency -> {
            PurchaseTransaction pt = new PurchaseTransaction();
            pt.setId(purchaseTransaction.getId());
            pt.setDescription(purchaseTransaction.getDescription());
            pt.setTransactionDate(purchaseTransaction.getTransactionDate());
            pt.setAmountUSD(purchaseTransaction.getAmountUSD());
            pt.setCurrency(currency.getCurrency());
            pt.setExchangeRate(String.format("%.2f", currency.getExchangeRate()));
            pt.setConvertedAmount(String.format("%.2f", currency.getExchangeRate()*purchaseTransaction.getAmountUSD()));

            purchaseTransactionList.add(pt);
        });

        return purchaseTransactionList;
    }
}
