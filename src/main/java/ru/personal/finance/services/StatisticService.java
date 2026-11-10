package ru.personal.finance.services;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.CategoryType;
import ru.personal.finance.domain.Wallet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatisticService {
    public double getTotalIncome(Wallet wallet) {
        return getTotalByType(wallet.getCategories(), CategoryType.INCOME);
    }

    public double getTotalExpense(Wallet wallet) {
        return getTotalByType(wallet.getCategories(), CategoryType.EXPENSE);
    }

    public double getTotalByType(Collection<Category> categories, CategoryType type) {
        double total = 0;

        for (Category category : categories) {
            if (category.getType() == type) {
                total += category.getTotalAmount();
            }
        }

        return total;
    }

    public Collection<Category> getCategoriesByType(Wallet wallet, CategoryType type) {
        List<Category> result = new ArrayList<Category>();

        for (Category category : wallet.getCategories()) {
            if (category.getType() == type) {
                result.add(category);
            }
        }

        return result;
    }

    public Collection<Category> requireCategories(Wallet wallet, String... names) {
        List<Category> result = new ArrayList<Category>();

        for (String name : names) {
            Category category = wallet.getCategory(name);

            if (category == null) {
                String message = String.format("Категория %s не существует", name);
                throw new IllegalArgumentException(message);
            }

            result.add(category);
        }

        return result;
    }

    public Collection<Category> filterByType(Collection<Category> categories, CategoryType type) {
        List<Category> result = new ArrayList<Category>();

        for (Category category : categories) {
            if (category.getType() == type) {
                result.add(category);
            }
        }

        return result;
    }

    public Collection<Category> getCategoriesWithBudget(Collection<Category> categories) {
        List<Category> result = new ArrayList<Category>();

        for (Category category : categories) {
            if (category.getBudget().isPresent()) {
                result.add(category);
            }
        }

        return result;
    }

    public Collection<Category> resolveCategories(Wallet wallet, String... categoryNames) {
        if (categoryNames == null || categoryNames.length == 0) {
            return wallet.getCategories();
        }

        return requireCategories(wallet, categoryNames);
    }
}
