package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("datajpa")
public class DataJpaMealRepositoryTest extends AbstractMealServiceTest {
    public DataJpaMealRepositoryTest() {
        super();
    }
}