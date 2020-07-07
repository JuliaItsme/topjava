package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("jpa")
public class JpaUserRepositoryTest extends AbstractUserServiceTest {
    public JpaUserRepositoryTest() {
        super();
    }
}