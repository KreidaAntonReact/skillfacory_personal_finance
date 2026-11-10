package ru.personal.finance.app.command;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.CategoryType;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

import java.util.Collection;

public class CategoryCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoryCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();

        if (args.length == 0) {
            showCategories(user);
            return;
        }

        if (args.length < 2) {
            throw new IllegalArgumentException(getDescription());
        }

        this.createNewCategory(user, args);
    }

    private void showCategories(User user) {
        Wallet wallet = walletService.getWallet(user);
        Collection<Category> categories = wallet.getCategories();

        if (categories.isEmpty()) {
            System.out.println("Категории пока не созданы");
            return;
        }

        System.out.println("Категории кошелька:");
        int order = 1;
        for (Category category : categories) {
            System.out.printf("%d. %s (%s)%n", order, category.getName(), category.getType());
            order++;
        }
    }

    private void createNewCategory(User user, String... args) {
        CategoryType type = parseCategoryType(args[1]);

        walletService.addCategory(user, args[0], type);
        System.out.printf("Категория %s (%s) успешно создана%n", args[0], type);
    }

    private CategoryType parseCategoryType(String value) {
        try {
            return CategoryType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Тип категории должен быть income или expense");
        }
    }

    @Override
    public String getDescription() {
        return "категории - category <name> <income|expense> | category";
    }
}
