package ru.personal.finance.services;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.CategoryType;
import ru.personal.finance.domain.Operation;
import ru.personal.finance.domain.OperationType;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;

public class WalletService {
    public void createForUser(User user) {
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
    }

    public Wallet getWallet(User user) {
        Wallet wallet = user.getWallet();

        if (wallet == null) {
            String message = String.format("У пользователя %s нет кошелька", user.getLogin());
            throw new IllegalStateException(message);
        }

        return wallet;
    }

    public void addCategory(User user, String name, CategoryType type) {
        if (isNullOrEmptyValue(name)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        if (type == null) {
            throw new IllegalArgumentException("Тип категории не может быть пустым!");
        }

        Wallet wallet = getWallet(user);

        Category category = new Category();
        category.setName(name);
        category.setType(type);

        boolean isCreate = wallet.addCategory(category);

        if (!isCreate) {
            String message = String.format("Категория %s уже существует", name);
            throw new IllegalArgumentException(message);
        }
    }

    public void addBudget(User user, String categoryName, double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("Бюджет не может быть отрицательным!");
        }

        Category category = requireExistingCategory(user, categoryName);
        category.setBudget(budget);
    }

    public Category getCategoryWithBudget(User user, String categoryName) {
        Category category = requireExistingCategory(user, categoryName);

        if (category.getBudget().isEmpty()) {
            String message = String.format("У категории %s бюджет не установлен", categoryName);
            throw new IllegalArgumentException(message);
        }

        return category;
    }

    public void addIncome(User user, String categoryName, double amount) {
        Category category = requireCategory(user, categoryName, CategoryType.INCOME);

        Operation operation = new Operation();
        operation.setType(OperationType.INCOME);
        operation.setAmount(amount);

        getWallet(user).addOperation(operation, category.getName());
    }

    public void addExpense(User user, String categoryName, double amount) {
        Category category = requireCategory(user, categoryName, CategoryType.EXPENSE);

        Operation operation = new Operation();
        operation.setType(OperationType.EXPENSE);
        operation.setAmount(amount);

        getWallet(user).addOperation(operation, category.getName());
    }

    private Category requireExistingCategory(User user, String categoryName) {
        if (isNullOrEmptyValue(categoryName)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        Category category = getWallet(user).getCategory(categoryName);

        if (category == null) {
            String message = String.format("Категория %s не существует", categoryName);
            throw new IllegalArgumentException(message);
        }

        return category;
    }

    private Category requireCategory(User user, String categoryName, CategoryType expectedType) {
        Category category = requireExistingCategory(user, categoryName);

        if (category.getType() != expectedType) {
            String message = String.format(
                    "Категория %s имеет тип %s, ожидался %s",
                    categoryName,
                    category.getType(),
                    expectedType
            );
            throw new IllegalArgumentException(message);
        }

        return category;
    }

    private boolean isNullOrEmptyValue(String value) {
        return value == null || value.isEmpty();
    }
}
