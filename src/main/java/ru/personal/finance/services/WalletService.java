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

    public void addIncome(User user, String categoryName, double amount) {
        Category category = requireCategory(user, categoryName, CategoryType.INCOME);

        Operation operation = new Operation();
        operation.setType(OperationType.INCOME);
        operation.setAmount(amount);
        operation.setCategory(category.getName());

        getWallet(user).addOperation(operation);
    }

    public void addExpense(User user, String categoryName, double amount) {
        Category category = requireCategory(user, categoryName, CategoryType.EXPENSE);

        Operation operation = new Operation();
        operation.setType(OperationType.EXPENSE);
        operation.setAmount(amount);
        operation.setCategory(category.getName());

        getWallet(user).addOperation(operation);
    }

    private Category requireCategory(User user, String categoryName, CategoryType expectedType) {
        if (isNullOrEmptyValue(categoryName)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        Wallet wallet = getWallet(user);
        Category category = wallet.getCategory(categoryName);

        if (category == null) {
            String message = String.format("Категория %s не существует", categoryName);
            throw new IllegalArgumentException(message);
        }

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
