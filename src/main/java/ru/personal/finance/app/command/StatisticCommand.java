package ru.personal.finance.app.command;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.CategoryType;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.StatisticService;
import ru.personal.finance.services.WalletService;

import java.util.Collection;

public class StatisticCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;
    private final StatisticService statisticService;

    public StatisticCommand(
            AuthService authService,
            WalletService walletService,
            StatisticService statisticService
    ) {
        this.authService = authService;
        this.walletService = walletService;
        this.statisticService = statisticService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();
        Wallet wallet = walletService.getWallet(user);
        Collection<Category> categories = statisticService.resolveCategories(wallet, args);
        boolean filteredByCategories = args.length > 0;

        printStats(categories, filteredByCategories);
    }

    @Override
    public String getDescription() {
        return "статистика - stats | stats <category> or [category...]";
    }

    private void printStats(Collection<Category> categories, boolean filteredByCategories) {
        Collection<Category> incomeCategories = statisticService.filterByType(categories, CategoryType.INCOME);
        Collection<Category> expenseCategories = statisticService.filterByType(categories, CategoryType.EXPENSE);
        Collection<Category> budgetCategories = statisticService.getCategoriesWithBudget(categories);

        boolean showIncome = !filteredByCategories || !incomeCategories.isEmpty();
        boolean showExpense = !filteredByCategories || !expenseCategories.isEmpty();

        if (showIncome) {
            double totalIncome = statisticService.getTotalByType(categories, CategoryType.INCOME);
            System.out.printf("Общий доход: %.1f%n", totalIncome);
            printCategoriesBlock("Доходы по категориям:", incomeCategories, false);
        }

        if (showExpense) {
            double totalExpense = statisticService.getTotalByType(categories, CategoryType.EXPENSE);
            System.out.printf("Общие расходы: %.1f%n", totalExpense);
            printCategoriesBlock("Расходы по категориям:", expenseCategories, false);
        }

        printCategoriesBlock("Бюджет по категориям:", budgetCategories, true);
    }

    private void printCategoriesBlock(String title, Collection<Category> categories, boolean withBudget) {
        if (categories.isEmpty()) {
            return;
        }

        System.out.println(title);

        for (Category category : categories) {
            if (withBudget) {
                System.out.printf(
                        "- %s: %.1f, Оставшийся бюджет: %.1f%n",
                        category.getName(),
                        category.getBudget().get(),
                        category.getRemainingBudget()
                );
            } else {
                System.out.printf("- %s: %.1f%n", category.getName(), category.getTotalAmount());
            }
        }
    }
}
