package io.pivotal.demo.entity;

public class Customer {

    private String id;
    private String name;
    private Integer income;

    public Integer getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public void setIncome(Integer income) {
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
