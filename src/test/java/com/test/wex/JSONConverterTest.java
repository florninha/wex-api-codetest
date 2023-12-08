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
import static org.junit.jupiter.api.Assertions.*;

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
    public void givenWrongDatePurchaseTransactionPayload_whenConverted_shouldThrowException() {
        assertThrows(ResponseStatusException.class,() -> JSONConverter.payloadToPurchaseTransaction(purchaseTransactionFixture.storePurchaseTransactionInvalidDateAsString()));
    }

    @Test
    @SneakyThrows
    public void givenListOfCurrencyPayload_whenConverted_shouldReturnAListTRRECurrency() throws IOException {
        List<TRRECurrency> listOfCurrency = JSONConverter.payloadToListOfCurrency(treasuryReportFixture.treasuryReportWithDataAsString());

        assertThat(listOfCurrency).isEqualToComparingFieldByFieldRecursively(createPayloadTRRECurrency());
    }

    @Test
    @SneakyThrows
    public void givenEmptyListOfCurrencyPayload_whenConverted_shouldReturnAnEmptyListTRRECurrency() {
        assertThrows(ResponseStatusException.class,() -> JSONConverter.payloadToListOfCurrency(treasuryReportFixture.treasuryReportWithoutDataAsString()));
    }

    @Test
    @SneakyThrows
    public void givenPurchaseTransactionList_whenConverted_shouldReturnAPayloadWithCorrectInformation() throws IOException {
        String payload = JSONConverter.purchaseTransactionListToPayload(createPayloadPurchase());

        assertEquals(purchaseTransactionFixture.purchaseWithCurrencyAsString(), payload);
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

    public List<PurchaseTransaction> createPayloadPurchase() {
        List<PurchaseTransaction> ptList = new ArrayList<>();

        PurchaseTransaction pt1 = createPayloadPurchaseTransaction();
        pt1.setId(2);
        pt1.setCurrency("Afghani");
        pt1.setExchangeRate("86.75");
        pt1.setConvertedAmount("173.50");
        ptList.add(pt1);

        PurchaseTransaction pt2 = createPayloadPurchaseTransaction();
        pt2.setId(2);
        pt2.setCurrency("Dinar");
        pt2.setExchangeRate("135.66");
        pt2.setConvertedAmount("271.31");
        ptList.add(pt2);

        PurchaseTransaction pt3 = createPayloadPurchaseTransaction();
        pt3.setId(2);
        pt3.setCurrency("Dollar");
        pt3.setExchangeRate("1.34");
        pt3.setConvertedAmount("2.69");
        ptList.add(pt3);

        return ptList;
    }
}
