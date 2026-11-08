package ru.personal.finance.services;

import ru.personal.finance.domain.User;
import ru.personal.finance.repositories.UserRepository;

import java.util.Objects;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "Необходимо передать UserRepository!");
    }

    public User create(String login, String password) {
        Objects.requireNonNull(login, "Необходимо передать логин!");
        Objects.requireNonNull(password, "Необходимо передать пароль!");

        if (userRepository.exists(login)) {
            String message = String.format("Пользователь с логином %s уже существует", login);
            throw new IllegalArgumentException(message);
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        return userRepository.create(user);
    }

    public User findUser(String login) {
        return userRepository.findUser(login);
    }
}
