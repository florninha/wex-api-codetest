package com.test.wex.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.wex.model.PurchaseTransaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JSONConverter {

    public PurchaseTransaction payloadToPurchaseTransaction(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, PurchaseTransaction.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to convert JSON. Make sure date is yyyy-MM-dd");
        }
    }
}
