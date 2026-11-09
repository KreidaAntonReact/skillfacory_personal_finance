package ru.personal.finance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Category {
    private String name;
    private CategoryType type;
    private Double budget;
    private final List<Operation> operations = new ArrayList<Operation>();

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public Optional<Double> getBudget() {
        return Optional.ofNullable(budget);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Необходимо передать название категории!");
    }

    public void setType(CategoryType type) {
        this.type = Objects.requireNonNull(type, "Необходимо передать тип категории!");
    }

    public void setBudget(Double budget) {
        if (this.type != CategoryType.EXPENSE) {
            throw new IllegalArgumentException("Нельзя добавить бюджет к доходам");
        }

        this.budget = budget;
    }

    public void addOperation(Operation operation) {
        operations.add(Objects.requireNonNull(operation, "Необходимо передать операцию!"));
    }
}
