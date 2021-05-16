package com.example.simplebank.model;

public class Customer {
    private String accountNumber;
    private String name;

    public Customer(String accountNumber, String name) {
        this.accountNumber = accountNumber;
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
