package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("jdbc")
public class JdbcMealRepositoryTest extends AbstractMealServiceTest {
    public JdbcMealRepositoryTest() {
        super();
    }
}