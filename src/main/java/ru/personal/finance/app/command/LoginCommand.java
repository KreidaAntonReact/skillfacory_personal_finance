package ru.personal.finance.app.command;

import ru.personal.finance.services.AuthService;
import ru.personal.finance.domain.User;

public class LoginCommand implements Command {
    private final AuthService authService;

    public LoginCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Использование: login <login> <password>");
        }

        User authUser = authService.login(args[0], args[1]);
        System.out.printf("Вы вошли как %s%n", authUser.getLogin());
    }

    @Override
    public String getDescription() {
        return "войти в систему - login <login> <password>";
    }
}
