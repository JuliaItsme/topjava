package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("datajpa")
public class DataJpaUserRepositoryTest extends AbstractUserServiceTest {
    public DataJpaUserRepositoryTest() {
        super();
    }
}