package web.Service;

import web.Model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveOrUpdateUser (User user);
    void deleteUser(long id);
    User getUser(long id);
}
