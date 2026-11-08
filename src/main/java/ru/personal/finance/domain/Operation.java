package ru.personal.finance.domain;

import java.util.Objects;

public class Operation {
    private OperationType type;
    private double amount;
    private String category;

    public OperationType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public void setType(OperationType type) {
        this.type = Objects.requireNonNull(type, "Необходимо передать тип операции!");
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма операции должна быть строго положительной!");
        }
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = Objects.requireNonNull(category, "Необходимо передать категорию!");
    }
}
