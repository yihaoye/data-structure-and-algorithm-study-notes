package com.bank.model;

import java.math.*;
import java.util.*;
import java.time.*;
import com.bank.exception.*;
import com.bank.utils.*;

public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime time;

    public Transaction(TransactionType type, BigDecimal amount, LocalDateTime time) {
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    public TransactionType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.format("Transaction: %s %s %s", this.type, this.amount, this.time);
    }
}