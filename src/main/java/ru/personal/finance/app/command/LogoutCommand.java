package ru.personal.finance.app.command;

import ru.personal.finance.app.AuthService;
import ru.personal.finance.domain.User;

import java.util.Objects;

public class LogoutCommand implements Command {
    private final AuthService authService;

    public LogoutCommand(AuthService authService) {
        this.authService = Objects.requireNonNull(authService, "Необходимо передать AuthService!");
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
