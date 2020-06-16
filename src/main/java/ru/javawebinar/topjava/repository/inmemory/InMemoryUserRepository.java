package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> repositoryUsers = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repositoryUsers.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repositoryUsers.put(user.getId(), user);
            return user;
        }
        return repositoryUsers.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repositoryUsers.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = new ArrayList<>(repositoryUsers.values());
        users.sort(comparing(AbstractNamedEntity::getName));
        users.sort(comparing(User::getEmail));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repositoryUsers.entrySet().stream().filter(i -> i.getValue().getEmail().equals(email)).findFirst().orElse(null).getValue();
    }
}
