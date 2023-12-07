package com.test.wex;

import com.test.wex.common.JSONConverter;
import com.test.wex.fixtures.TreasuryReportFixture;
import com.test.wex.model.PurchaseTransaction;
import com.test.wex.fixtures.PurchaseTransactionFixture;
import com.test.wex.model.TRRECurrency;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JSONConverterTest {

    @Autowired
    JSONConverter JSONConverter;

    @Autowired
    PurchaseTransactionFixture purchaseTransactionFixture;

    @Autowired
    TreasuryReportFixture treasuryReportFixture;

    @Test
    @SneakyThrows
    public void givenIdealPurchaseTransactionPayload_whenConverted_shouldReturnPurchaseTransaction() throws IOException {
        PurchaseTransaction pt = JSONConverter.payloadToPurchaseTransaction(purchaseTransactionFixture.storePurchaseTransactionIdealAsString());

        assertThat(pt).isEqualToComparingFieldByFieldRecursively(createPayloadPurchaseTransaction());
    }

    @Test
    @SneakyThrows
    public void givenWrongDatePurchaseTransactionPayload_whenConverted_shouldThrowException() throws IOException {
        assertThrows(ResponseStatusException.class,() -> JSONConverter.payloadToPurchaseTransaction(purchaseTransactionFixture.storePurchaseTransactionInvalidDateAsString()));
    }

    @Test
    @SneakyThrows
    public void givenListOfCurrencyPayload_whenConverted_shouldReturnTRRECurrency() throws IOException {
        List<TRRECurrency> listOfCurrency = JSONConverter.payloadToListOfCurrency(treasuryReportFixture.treasuryReportWithDataAsString());

        assertThat(listOfCurrency).isEqualToComparingFieldByFieldRecursively(createPayloadTRRECurrency());
    }

    public PurchaseTransaction createPayloadPurchaseTransaction() {
        PurchaseTransaction pt = new PurchaseTransaction();
        pt.setDescription("Purchase");
        pt.setTransactionDate(LocalDate.of(2023,9,30));
        pt.setAmountUSD(2.00);

        return pt;
    }

    public List<TRRECurrency> createPayloadTRRECurrency() {
        List<TRRECurrency> currencyList = new ArrayList<>();

        TRRECurrency currencyAfghani = new TRRECurrency();
        currencyAfghani.setCurrency("Afghani");
        currencyAfghani.setExchangeRate(86.75);
        currencyList.add(currencyAfghani);

        TRRECurrency currencyAriary = new TRRECurrency();
        currencyAriary.setCurrency("Ariary");
        currencyAriary.setExchangeRate(4468.0);
        currencyList.add(currencyAriary);

        TRRECurrency currencyBaht = new TRRECurrency();
        currencyBaht.setCurrency("Baht");
        currencyBaht.setExchangeRate(34.12);
        currencyList.add(currencyBaht);

        return currencyList;
    }
}
