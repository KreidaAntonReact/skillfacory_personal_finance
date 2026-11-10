package ru.personal.finance.repositories;

import ru.personal.finance.domain.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserRepository {
    private final Map<String, User> users = new LinkedHashMap<String, User>();

    public User create(User user) {
        users.put(user.getLogin(), user);
        return user;
    }

    public boolean exists(String login) {
        return users.containsKey(login);
    }

    public User findUser(String login) {
        User findUser = users.get(login);

        if (Objects.isNull(findUser)) {
            String message = String.format("Пользователь с логином %s не существует", login);

            throw new NullPointerException(message);
        }

        return findUser;
    }

    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }

    public void replaceAll(List<User> usersForMap) {
        users.clear();

        for (User user : usersForMap) {
            users.put(user.getLogin(), user);
        }
    }
}
