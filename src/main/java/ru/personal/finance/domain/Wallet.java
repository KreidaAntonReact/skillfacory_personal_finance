package ru.personal.finance.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Wallet {
    private final Set<String> categories = new LinkedHashSet<String>();
    private final List<Operation> operations = new ArrayList<Operation>();
    private final Map<String, Double> budgets = new LinkedHashMap<String, Double>();

    public boolean addCategory(String category) {
        return categories.add(category);
    }

    public boolean hasCategory(String category) {
        return categories.contains(category);
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }
}
