package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validationMeal(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        saveRole(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRole(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRole(users);
    }

    @Override
    public List<User> getAll() {
        List<User> listUsers = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        listUsers.forEach(user -> {
            user.setRoles(getAllRole().get(user.getId()));
        });
        return listUsers;
    }

    //https://mkyong.com/spring/spring-jdbctemplate-batchupdate-example/
    private void saveRole(User user) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles VALUES (?,?)",
                new BatchPreparedStatementSetter() {
                    List<Role> listRoles = new ArrayList<>(user.getRoles());

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.getId());
                        ps.setString(2, listRoles.get(i).name());
                    }

                    @Override
                    public int getBatchSize() {
                        return listRoles.size();
                    }
                });
    }

    private Set<Role> getRole(User user) {
        return new HashSet<>(jdbcTemplate.queryForList("SELECT role FROM user_roles  WHERE user_id=?", Role.class, user.getId()));
    }

    private boolean deleteRole(User user) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId()) != 0;
    }

    private User setRole(Collection<User> users) {
        User user = DataAccessUtils.singleResult(users);
        assert user != null;
        user.setRoles(getRole(user));
        return user;
    }

    private Map<Integer, Set<Role>> getAllRole() {
        Map<Integer, Set<Role>> mapRole = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles",
                rs -> {
                    mapRole.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                            .add(Role.valueOf(rs.getString("role")));
                });
        return mapRole;
    }
}
