package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaMealRepositoryTest.class,
        DataJpaUserRepositoryTest.class,
        JdbcMealRepositoryTest.class,
        JdbcUserRepositoryTest.class,
        JpaMealRepositoryTest.class,
        JpaUserRepositoryTest.class
})
public class AllServiceTest {
}
