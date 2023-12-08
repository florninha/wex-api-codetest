package com.test.wex.fixtures;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PurchaseTransactionFixture {

    public String CONST_PATH="./src/test/java/com/test/wex/resources/payloads";
    public String storePurchaseTransactionIdealAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/purchase/StorePurchaseBodyIdeal.json");
        return Files.readString(filePath);
    }

    public String storePurchaseTransactionInvalidDateAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/purchase/StorePurchaseBodyInvalidDate.json");
        return Files.readString(filePath);
    }

    public String purchaseWithCurrencyAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/purchase/PurchaseWithCurrencyPayload.json");
        return Files.readString(filePath);
    }

    public String purchaseWithNegativeAmountAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/purchase/StorePurchaseBodyNegativeAmount.json");
        return Files.readString(filePath);
    }

    public String purchaseTooManyCharactersAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/purchase/StorePurchaseBodyTooManyCharacters.json");
        return Files.readString(filePath);
    }
}
