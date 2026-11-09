package ru.personal.finance.app.command;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

import java.util.Collection;

public class CategoriesCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoriesCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();
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

    @Override
    public String getDescription() {
        return "показать категории кошелька - categories";
    }
}
