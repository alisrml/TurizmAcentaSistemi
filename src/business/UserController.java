package business;

import dao.UserDao;
import entity.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class UserController {
    private UserDao userDao;

    public UserController() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String mail, String password) {
        return userDao.findByLogin(mail,password);
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    public boolean delete(int id) {
        return this.userDao.delete(id);
    }

    public boolean save(User user) {
        return this.userDao.save(user);
    }

    public boolean update(User user) {
        return this.userDao.update(user);
    }

    public User getByID(int id) {
        return this.userDao.getByID(id);
    }
}
