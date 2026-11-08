package ru.personal.finance.domain;

import java.util.Objects;

public class User {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = Objects.requireNonNull(login, "Необходимо передать логин!");
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password, "Необходимо передать пароль!");
    }
}
