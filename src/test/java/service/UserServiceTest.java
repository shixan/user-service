package service;

import com.example.userservice.dao.UserDao;
import com.example.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void createUser_shouldCallDaoCreate() {
        userService.createUser("Dima", "dima@mail.ru", 30);
        verify(userDao, times(1)).create(any(User.class));
    }

    @Test
    void getUser_shouldReturnUser() {
        User mockUser = new User("Misha", "misha@mail.ru", 25);
        when(userDao.get(1L)).thenReturn(mockUser);

        User result = userService.getUser(1L);

        assertEquals("Misha", result.getName());
        assertEquals("misha@mail.ru", result.getEmail());
        assertEquals(25, result.getAge());
    }

    @Test
    void getAllUsers_shouldReturnList() {
        List<User> mockList = Arrays.asList(
                new User("Igor", "igor@mail.ru", 20),
                new User("Sasha", "sasha@mail.ru", 22)
        );
        when(userDao.getAll()).thenReturn(mockList);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    void updateUser_shouldUpdateIfExists() {
        User user = new User("Old Name", "old@mail.ru", 50);
        when(userDao.get(1L)).thenReturn(user);

        userService.updateUser(1L, "New Name", "new@mail.ru", 40);

        assertEquals("New Name", user.getName());
        assertEquals("new@mail.ru", user.getEmail());
        assertEquals(40, user.getAge());
        verify(userDao).update(user);
    }

    @Test
    void updateUser_shouldNotThrowIfUserNotFound() {
        when(userDao.get(99L)).thenReturn(null);
        assertDoesNotThrow(() -> userService.updateUser(99L, "X", "x@mail.ru", 18));
        verify(userDao, never()).update(any());
    }

    @Test
    void deleteUser_shouldCallDaoDelete() {
        userService.deleteUser(5L);
        verify(userDao).delete(5L);
    }
}
