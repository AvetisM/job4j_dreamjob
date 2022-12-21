package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

@Service
public class UserService {

    private final UserDBStore userDBStore;

    private UserService(UserDBStore userDBStore) {
        this.userDBStore = userDBStore;
    }

    public void add(User user) {
        userDBStore.add(user);
    }

    public User findById(int id) {
        return userDBStore.findById(id);
    }

}
