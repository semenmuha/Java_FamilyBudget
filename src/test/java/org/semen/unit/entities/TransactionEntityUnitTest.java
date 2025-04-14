package org.semen.unit.entities;

import org.junit.jupiter.api.Test;
import org.semen.entities.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionEntityUnitTest {

    @Test
    void testTransactionCreation() {
        Transaction transaction = new Transaction();
        transaction.setDescription("Пополнение счета");
        transaction.setAmount(BigDecimal.valueOf(50.0));
        transaction.setDate(LocalDateTime.now());

        assertEquals("Пополнение счета", transaction.getDescription());
        assertEquals(BigDecimal.valueOf(50.0), transaction.getAmount());
        assertNotNull(transaction.getDate());
    }
}