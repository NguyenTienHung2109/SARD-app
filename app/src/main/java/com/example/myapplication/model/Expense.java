package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Expense implements Serializable {
    public static ArrayList<Expense> expensesList = new ArrayList<>();
    private String expenseId;
    private int cateId;
    private String description;
    private int amount;

    private Date createAt;

    public Expense() {}

    public Expense(String expense_id, int cateId, String description, int amount, Date createAt) {
        this.expenseId = expense_id;
        this.cateId = cateId;
        this.description = description;
        this.amount = amount;
        this.createAt = createAt;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
