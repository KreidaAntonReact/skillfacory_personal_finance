package ru.personal.finance.services;

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

    public void addCategory(User user, String category, double budget) {
        if (isNullOrEmptyValue(category)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        if (budget < 0) {
            throw new IllegalArgumentException("Бюджет не может быть отрицательным!");
        }

        Wallet wallet = getWallet(user);

        if (!wallet.addCategory(category, budget)) {
            String message = String.format("Категория %s уже существует", category);
            throw new IllegalArgumentException(message);
        }
    }

    public void addBudget(User user, String category, double budget) {
        if (isNullOrEmptyValue(category)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        if (budget < 0) {
            throw new IllegalArgumentException("Бюджет не может быть отрицательным!");
        }

        Wallet wallet = getWallet(user);

        if (!wallet.addBudget(category, budget)) {
            String message = String.format("Категория %s не существует", category);
            throw new IllegalArgumentException(message);
        }
    }

    public void addIncome(User user, String category, double amount) {
        if (isNullOrEmptyValue(category)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        Wallet wallet = getWallet(user);

        if (!wallet.hasCategory(category)) {
            String message = String.format("Категория %s не существует", category);
            throw new IllegalArgumentException(message);
        }

        Operation operation = new Operation();
        operation.setType(OperationType.INCOME);
        operation.setAmount(amount);
        operation.setCategory(category);

        wallet.addOperation(operation);
    }

    public void addExpense(User user, String category, double amount) {
        if (isNullOrEmptyValue(category)) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }

        Wallet wallet = getWallet(user);

        if (!wallet.hasCategory(category)) {
            String message = String.format("Категория %s не существует", category);
            throw new IllegalArgumentException(message);
        }

        Operation operation = new Operation();
        operation.setType(OperationType.EXPENSE);
        operation.setAmount(amount);
        operation.setCategory(category);

        wallet.addOperation(operation);
    }

    private boolean isNullOrEmptyValue(String value) {
        return value == null || value.isEmpty();
    }
}
