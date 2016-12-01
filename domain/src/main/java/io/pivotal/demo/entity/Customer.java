package io.pivotal.demo.entity;

import java.math.BigDecimal;

public class Customer {

    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
