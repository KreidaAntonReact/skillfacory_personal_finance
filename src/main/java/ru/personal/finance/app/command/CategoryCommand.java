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

        if ("add".equalsIgnoreCase(args[0])) {
            addCategory(user, args);
            return;
        }

        throw new IllegalArgumentException(getMessageHowUseCommand());
    }

    private void addCategory(User user, String... args) {
        if (args.length < 3) {
            throw new IllegalArgumentException(getMessageHowUseCommand());
        }

        CategoryType type = parseCategoryType(args[2]);

        walletService.addCategory(user, args[1], type);
        System.out.printf("Категория %s (%s) успешно создана%n", args[1], type);
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

    private CategoryType parseCategoryType(String value) {
        try {
            return CategoryType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Тип категории должен быть income или expense");
        }
    }

    @Override
    public String getDescription() {
        return "категории - category add <name> <income|expense> | category";
    }

    private String getMessageHowUseCommand() {
        return "Ошибка при вводе. Нужно использовать: category add <name> <income|expense> | category";
    }
}
