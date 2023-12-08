package com.test.wex;

import com.test.wex.common.HTTPRequest;
import com.test.wex.common.ValidateObject;
import com.test.wex.fixtures.PurchaseTransactionFixture;
import com.test.wex.fixtures.TreasuryReportFixture;
import com.test.wex.model.PurchaseTransaction;
import com.test.wex.persistence.PurchaseTransactionEntity;
import com.test.wex.persistence.PurchaseTransactionRepository;
import com.test.wex.service.PurchaseTransactionService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

@SpringBootTest
public class PurchaseTransactionServiceTest {

    @Autowired
    PurchaseTransactionService purchaseTransactionService;

    @Autowired
    PurchaseTransactionFixture purchaseTransactionFixture;

    @Autowired
    TreasuryReportFixture treasuryReportFixture;

    @Mock
    PurchaseTransactionRepository purchaseTransactionRepository;

    @Mock
    HTTPRequest httpRequest;

    @Mock
    ValidateObject validateObject;

    @Test
    @SneakyThrows
    public void givenIdealPurchaseTransactionPayload_whenProcessed_shouldReturnTheIdCreated() throws IOException {
        Mockito.when(purchaseTransactionRepository.save(any(PurchaseTransactionEntity.class))).thenReturn(perfectTransactionEntity());

        String message = purchaseTransactionService.storePurchase(purchaseTransactionFixture.storePurchaseTransactionIdealAsString());

        assertEquals(message, "Purchase Transaction created! Id:1");
    }

    @Test
    public void givenInvalidDatePurchaseTransactionPayload_whenProcessed_shouldThrowException() {
        Mockito.when(purchaseTransactionRepository.save(any(PurchaseTransactionEntity.class))).thenReturn(perfectTransactionEntity());

        assertThrows(ResponseStatusException.class,() -> purchaseTransactionService.storePurchase(purchaseTransactionFixture.storePurchaseTransactionInvalidDateAsString()));
    }

    @Test
    public void givenNegativeAmountPurchaseTransactionPayload_whenProcessed_shouldThrowException() {
        Mockito.when(purchaseTransactionRepository.save(any(PurchaseTransactionEntity.class))).thenReturn(perfectTransactionEntity());

        assertThrows(ResponseStatusException.class,() -> purchaseTransactionService.storePurchase(purchaseTransactionFixture.purchaseWithNegativeAmountAsString()));
    }

    @Test
    public void givenDescriptionTooBigPurchaseTransactionPayload_whenProcessed_shouldThrowException() {
        Mockito.when(purchaseTransactionRepository.save(any(PurchaseTransactionEntity.class))).thenReturn(perfectTransactionEntity());

        assertThrows(ResponseStatusException.class,() -> purchaseTransactionService.storePurchase(purchaseTransactionFixture.purchaseTooManyCharactersAsString()));
    }

    @Test
    @Disabled
    @SneakyThrows
    public void givenExistentPurchaseTransaction_whenProcessed_shouldReturnTheObjectWithCurrency() throws IOException {
        Mockito.when(purchaseTransactionRepository.getReferenceById(1)).thenReturn(perfectTransactionEntity());
        Mockito.doNothing().when(validateObject).validatePurchaseTransactionEntity(any(Integer.class));
        //Mockito.when(purchaseTransactionRepository.existsById(any(Integer.class))).thenReturn(true);
        Mockito.when(httpRequest.requestCurrencyByDate(LocalDate.of(2023,9,30))).thenReturn(treasuryReportFixture.treasuryReportWithDataAsString());

        String message = purchaseTransactionService.retrievePurchaseById(1);

        assertEquals(message, purchaseTransactionFixture.purchaseWithCurrencyAsString());
    }

    @Test
    @SneakyThrows
    public void givenNonExistentPurchaseTransactionEntity_whenProcessed_shouldThrowAnException() throws IOException {
        Mockito.when(purchaseTransactionRepository.getReferenceById(1)).thenReturn(perfectTransactionEntity());
        Mockito.when(purchaseTransactionRepository.existsById(any(Integer.class))).thenReturn(false);
        Mockito.when(httpRequest.requestCurrencyByDate(LocalDate.of(2023,9,30))).thenReturn(treasuryReportFixture.treasuryReportWithDataAsString());

        assertThrows(ResponseStatusException.class,() -> purchaseTransactionService.retrievePurchaseById(1));
    }

    public PurchaseTransactionEntity perfectTransactionEntity() {
        PurchaseTransactionEntity ptEntity = new PurchaseTransactionEntity();
        ptEntity.setId(1);
        ptEntity.setDescription("Purchase");
        ptEntity.setTransactionDate(LocalDate.of(2023,9,30));
        ptEntity.setAmountUSD(String.format("%.2f", 2.00));

        return ptEntity;
    }

}
