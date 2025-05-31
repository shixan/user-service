package com.example.userservice.dao;

import com.example.userservice.model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDao dao;

    @BeforeAll
    void setUpAll() {
        postgres.start();
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());
        dao = new UserDao();
    }

    @AfterAll
    void tearDown() {
        postgres.stop();
    }

    @Test
    void testCreateAndGet() {
        User user = new User("Misha", "misha@mail.ru", 25);
        dao.create(user);

        User found = dao.get(user.getId());
        assertNotNull(found);
        assertEquals("Misha", found.getName());
    }

    @Test
    void testGetAll() {
        List<User> users = dao.getAll();
        assertNotNull(users);
    }

    @Test
    void testUpdateUser() {
        User user = new User("Old Name", "old@mail.ru", 50);
        dao.create(user);

        user.setName("New Name");
        user.setEmail("new@mail.ru");
        user.setAge(35);
        dao.update(user);

        User updated = dao.get(user.getId());
        assertEquals("New Name", updated.getName());
        assertEquals("new@mail.ru", updated.getEmail());
        assertEquals(35, updated.getAge());
    }

    @Test
    void testDeleteUser() {
        User user = new User("ToDelete", "delete@test.ru", 40);
        dao.create(user);

        dao.delete(user.getId());

        User deleted = dao.get(user.getId());
        assertNull(deleted);
    }

    @Test
    void testGetNonExistingUser() {
        User user = dao.get(9999L); // ID, которого точно нет
        assertNull(user);
    }
}
