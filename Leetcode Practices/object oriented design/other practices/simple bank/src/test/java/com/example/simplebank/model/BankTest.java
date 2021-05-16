package com.example.simplebank.model;

import com.example.simplebank.utils.BankCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
        private Bank bank;

        @BeforeEach
        public void setup() {
            bank = new Bank(BankCode.ANZ);
        }

        @Test
        public void testNegativeBalance() {
            Exception exception = assertThrows(RuntimeException.class, () -> {
                bank.setTotalBalance(BigDecimal.valueOf(-1));
            });
            String expectedMessage = "Bank balance cannot be negative.";
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
}
