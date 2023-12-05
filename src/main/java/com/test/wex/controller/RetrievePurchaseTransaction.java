package com.test.wex.controller;

import com.test.wex.service.PurchaseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RetrievePurchaseTransaction {

    @Autowired
    PurchaseTransactionService ptService;

    @RequestMapping(
            value = "/retrieveById/{id}",
            method = RequestMethod.GET)
    public String retrievePurchaseById(@PathVariable Integer id) {
        return ptService.retrievePurchaseById(id);
    }
}
