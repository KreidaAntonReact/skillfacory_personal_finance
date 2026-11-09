package ru.personal.finance.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private final Map<String, Category> categories = new LinkedHashMap<String, Category>();
    private final List<Operation> operations = new ArrayList<Operation>();

    public boolean addCategory(Category category) {
        if (categories.containsKey(category.getName())) {
            return false;
        }

        categories.put(category.getName(), category);
        return true;
    }

    public boolean hasCategory(String name) {
        return categories.containsKey(name);
    }

    public Category getCategory(String name) {
        return categories.get(name);
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
