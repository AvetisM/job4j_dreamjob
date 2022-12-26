package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore userDBStore;

    private UserService(UserDBStore userDBStore) {
        this.userDBStore = userDBStore;
    }

    public Collection<User> findAll() {
        List<User> users = userDBStore.findAll();
        return users;
    }
    public Optional<User> add(User user) {
        return userDBStore.add(user);
    }

    public Optional<User> findById(int id) {
        return userDBStore.findById(id);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userDBStore.findUserByEmailAndPassword(email, password);
    }

}
