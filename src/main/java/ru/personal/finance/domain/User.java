package ru.personal.finance.domain;

import java.util.Objects;

public class User {
    private String login;
    private String password;
    private Wallet wallet;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setLogin(String login) {
        this.login = Objects.requireNonNull(login, "Необходимо передать логин!");
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password, "Необходимо передать пароль!");
    }

    public void setWallet(Wallet wallet) {
        Objects.requireNonNull(wallet, "Необходимо передать кошелёк!");

        if (this.wallet != null) {
            throw new IllegalStateException("У пользователя уже есть кошелёк!");
        }

        this.wallet = wallet;
    }
}
