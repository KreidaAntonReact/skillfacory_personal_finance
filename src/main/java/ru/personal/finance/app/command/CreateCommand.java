package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.services.UserService;

public class CreateCommand implements Command {
    private final UserService userService;

    public CreateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Использование: create <login> <password>");
        }

        User user = userService.create(args[0], args[1]);
        System.out.printf("Пользователь %s успешно создан%n", user.getLogin());
    }

    @Override
    public String getDescription() {
        return "создать пользователя в системе - create <login> <password>";
    }
}
