package ru.personal.finance.domain;

import java.util.*;

public class Wallet {
    private final Set<String> categories = new LinkedHashSet<String>();
    private final List<Operation> operations = new ArrayList<Operation>();

    public boolean addCategory(String category) {
        return categories.add(category);
    }

    public boolean hasCategory(String category) {
        return categories.contains(category);
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
