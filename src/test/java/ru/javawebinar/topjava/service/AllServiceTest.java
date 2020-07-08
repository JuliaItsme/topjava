package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.DataJpaMealRepositoryTest;
import ru.javawebinar.topjava.service.datajpa.DataJpaUserRepositoryTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealRepositoryTest;
import ru.javawebinar.topjava.service.jdbc.JdbcUserRepositoryTest;
import ru.javawebinar.topjava.service.jpa.JpaMealRepositoryTest;
import ru.javawebinar.topjava.service.jpa.JpaUserRepositoryTest;

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
