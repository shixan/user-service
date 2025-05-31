package service;

import com.example.userservice.dao.UserDao;
import com.example.userservice.model.User;
import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String name, String email, int age) {
        User user = new User(name, email, age);
        userDao.create(user);
    }

    public User getUser(Long id) {
        return userDao.get(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void updateUser(Long id, String newName, String newEmail, int newAge) {
        User user = userDao.get(id);
        if (user != null) {
            user.setName(newName);
            user.setEmail(newEmail);
            user.setAge(newAge);
            userDao.update(user);
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
