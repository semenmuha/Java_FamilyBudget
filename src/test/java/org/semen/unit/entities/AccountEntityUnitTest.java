package org.semen.unit.entities;

import org.junit.jupiter.api.Test;
import org.semen.entities.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountEntityUnitTest {

    @Test
    void testAccountBalanceOperations() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100.0));

        // Проверяем работу сеттера
        assertEquals(BigDecimal.valueOf(100.0), account.getBalance());

        // Если бы был метод для изменения баланса:
        // account.deposit(50.0);
        // assertEquals(150.0, account.getBalance());
    }
}
