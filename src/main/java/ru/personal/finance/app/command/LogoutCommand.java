package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.services.AuthService;

public class LogoutCommand implements Command {
    private final AuthService authService;

    public LogoutCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();
        authService.logout();
        System.out.printf("Пользователь %s вышел из системы%n", user.getLogin());
    }

    @Override
    public String getDescription() {
        return "выйти из учётной записи - logout";
    }
}
