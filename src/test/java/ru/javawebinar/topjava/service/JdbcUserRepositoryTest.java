package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("jdbc")
public class JdbcUserRepositoryTest extends AbstractUserServiceTest {
    public JdbcUserRepositoryTest() {
        super();
    }
}