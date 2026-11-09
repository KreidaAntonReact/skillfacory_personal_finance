package ru.personal.finance.domain;

import java.util.Objects;

public class Category {
    private String name;
    private CategoryType type;

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Необходимо передать название категории!");
    }

    public void setType(CategoryType type) {
        this.type = Objects.requireNonNull(type, "Необходимо передать тип категории!");
    }
}
