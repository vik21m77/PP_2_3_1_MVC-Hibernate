package web.DAO;

import web.Model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    void saveOrUpdateUser (User user);
    void deleteUser(long id);
    User getUser(long id);
}
