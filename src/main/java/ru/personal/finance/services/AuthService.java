package ru.personal.finance.services;

import ru.personal.finance.domain.User;

import java.util.Objects;

public class AuthService {
    private final UserService userService;
    private User currentUser;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String login, String password) {
        User user = userService.findUser(login);

        if (Objects.nonNull(currentUser)) {
            String message = String.format("Вы уже авторизованы как - %s", currentUser.getLogin());

            throw new IllegalStateException(message);
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неправильно введен пароль!");
        }

        this.currentUser = user;
        return user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User requireCurrentUser() {
        if (Objects.isNull(currentUser)) {
            throw new IllegalStateException("Необходимо войти в систему!");
        }

        return currentUser;
    }
}
