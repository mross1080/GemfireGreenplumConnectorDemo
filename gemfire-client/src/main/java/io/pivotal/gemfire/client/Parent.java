package io.pivotal.gemfire.client;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Parent {

    private BigInteger id;
    private String name;
    private BigDecimal income;

    public BigDecimal getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
