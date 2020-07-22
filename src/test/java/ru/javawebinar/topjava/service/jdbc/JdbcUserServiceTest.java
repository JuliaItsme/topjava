package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private static Environment environment;

    @BeforeClass
    public static void checkProfiles() throws Exception {
        Assume.assumeTrue(!isProfiles());
    }
    private static  boolean isProfiles(){
        try{
            return environment.acceptsProfiles(Profiles.of(JDBC));
        }catch (NullPointerException npe) {
            return true;
        }
    }
}