package ru.personal.finance.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private final List<Operation> operations = new ArrayList<Operation>();
    private final Map<String, Double> budgets = new LinkedHashMap<String, Double>();

    public List<Operation> getOperations() {
        return operations;
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }
}
