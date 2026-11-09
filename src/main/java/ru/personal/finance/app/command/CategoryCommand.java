package ru.personal.finance.app.command;

import ru.personal.finance.domain.CategoryType;
import ru.personal.finance.domain.User;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

public class CategoryCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoryCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Использование: category <name> <income|expense>");
        }

        User user = authService.requireCurrentUser();
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
        return "создать категорию - category <name> <income|expense>";
    }
}
