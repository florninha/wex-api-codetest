package com.test.wex.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.wex.model.TRRECurrency;
import com.test.wex.model.PurchaseTransaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
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

    public String purchaseTransactionListToPayload(List<PurchaseTransaction> purchaseTransactionList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(purchaseTransactionList);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to convert object to JSON. Contact system administrator");
        }
    }

    public List<TRRECurrency> payloadToListOfCurrency(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode dataNode = rootNode.at("/data");
            return objectMapper.treeToValue(dataNode, objectMapper.getTypeFactory().constructCollectionType(List.class, TRRECurrency.class));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to convert JSON. Make sure date is yyyy-MM-dd");
        }
    }
}
