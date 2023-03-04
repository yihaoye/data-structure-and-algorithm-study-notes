package model;

import java.util.*;
import java.math.*;
import utils.*;

public class Customer {
    private String name;
    // private Long customerId; // unique id for customer

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
