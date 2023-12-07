package com.test.wex;

import com.test.wex.common.ValidateObject;
import com.test.wex.model.PurchaseTransaction;
import com.test.wex.persistence.PurchaseTransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ValidateObjectTest {

    @Autowired
    ValidateObject validateObject;

    @Test
    public void givenIdealPurchaseTransaction_whenValidated_shouldNotThrowException() {
        assertDoesNotThrow(() -> validateObject.validatePurchaseTransaction(generateIdealPurchaseTransaction()));
    }

    @Test
    public void givenTooManyCharactersPurchaseTransaction_whenValidated_shouldThrowException() {
        PurchaseTransaction pt = generateIdealPurchaseTransaction();
        pt.setDescription("Description with many many many more characters than 50");

        assertThrows(ResponseStatusException.class,() -> validateObject.validatePurchaseTransaction(pt));
    }

    @Test
    public void givenNegativeAmountPurchaseTransaction_whenValidated_shouldThrowException() {
        PurchaseTransaction pt = generateIdealPurchaseTransaction();
        pt.setAmountUSD(-15.00);

        assertThrows(ResponseStatusException.class,() -> validateObject.validatePurchaseTransaction(pt));
    }

    @Test
    public void givenFilledPurchaseTransactionEntity_whenValidated_shouldNotThrowException() {
        assertDoesNotThrow(() -> validateObject.validatePurchaseTransaction(generateIdealPurchaseTransactionEntity()));
    }

    public PurchaseTransaction generateIdealPurchaseTransaction(){
        PurchaseTransaction pt = new PurchaseTransaction();
        pt.setDescription("String with less than 50 char");
        pt.setTransactionDate(LocalDate.now());
        pt.setAmountUSD(10.00);

        return pt;
    }

    public PurchaseTransactionEntity generateIdealPurchaseTransactionEntity(){
        PurchaseTransactionEntity ptEntity = new PurchaseTransactionEntity();
        ptEntity.setId(1);
        ptEntity.setDescription("String with less than 50 char");
        ptEntity.setTransactionDate(LocalDate.now());
        ptEntity.setAmountUSD("10.00");

        return ptEntity;
    }
}
