package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static Comparator<User> COMPARATOR = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        List<User> USERS = Arrays.asList(
                new User(counter.incrementAndGet(), "User", "u@u.com", "456", Role.ROLE_USER),
                new User(counter.incrementAndGet(), "Admin", "a@a.com", "123", Role.ROLE_ADMIN),
                new User(counter.incrementAndGet(), "TUAdmin", "ua@ua.com", "123", Role.ROLE_ADMIN, Role.ROLE_USER));
        USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return (repository.remove(id) != null) ? true : false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> lu = new ArrayList<User>(repository.values());
        Collections.sort(lu, COMPARATOR);
        return (lu != null || !lu.isEmpty()) ? lu : Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return
                repository.values().stream().
                        filter(user -> user.getEmail().equalsIgnoreCase(email)).
                        findFirst().orElse(null);

    }
}
