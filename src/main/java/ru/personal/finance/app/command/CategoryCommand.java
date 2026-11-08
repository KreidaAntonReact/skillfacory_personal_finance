package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

import java.util.Objects;

public class CategoryCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoryCommand(AuthService authService, WalletService walletService) {
        this.authService = Objects.requireNonNull(authService, "Необходимо передать AuthService!");
        this.walletService = Objects.requireNonNull(walletService, "Необходимо передать WalletService!");
    }

    @Override
    public void execute(String... args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Использование: category <name>");
        }

        User user = authService.requireCurrentUser();

        walletService.addCategory(user, args[0]);
        System.out.printf("Категория %s успешно создана%n", args[0]);
    }

    @Override
    public String getDescription() {
        return "создать категорию - category <name>";
    }
}
