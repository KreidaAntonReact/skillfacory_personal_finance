package ru.personal.finance.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wallet {
    private final Map<String, Category> categories = new LinkedHashMap<String, Category>();

    public boolean addCategory(Category category) {
        if (categories.containsKey(category.getName())) {
            return false;
        }

        categories.put(category.getName(), category);
        return true;
    }

    public Category getCategory(String name) {
        return categories.get(name);
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public void addOperation(Operation operation, String categoryName) {
        Category category = categories.get(categoryName);

        if (category == null) {
            String message = String.format("Категория %s не существует", categoryName);
            throw new IllegalArgumentException(message);
        }

        category.addOperation(operation);
    }
}
