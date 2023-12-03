package com.test.wex.controller;

import com.test.wex.service.PurchaseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorePurchaseTransaction {

    @Autowired
    PurchaseTransactionService ptService;

    @RequestMapping(
            value = "/store",
            method = RequestMethod.POST,
            consumes = "application/json")
    public String storePurchase(@RequestBody String payload) {
        return ptService.storePurchase(payload);
    }
}
