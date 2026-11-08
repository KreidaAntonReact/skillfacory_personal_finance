package ru.personal.finance.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Wallet {
    private final Map<String, Double> categories = new LinkedHashMap<String, Double>();
    private final List<Operation> operations = new ArrayList<Operation>();

    public boolean addCategory(String category, double budget) {
        if (categories.containsKey(category)) {
            return false;
        }

        categories.put(category, budget);
        return true;
    }

    public boolean addBudget(String category, double budget) {
        if (!categories.containsKey(category)) {
            return false;
        }

        categories.put(category, budget);
        return true;
    }

    public boolean hasCategory(String category) {
        return categories.containsKey(category);
    }

    public Set<String> getCategories() {
        return categories.keySet();
    }

    public Map<String, Double> getCategoryBudgets() {
        return categories;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
