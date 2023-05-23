package model;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void addUser(User user) throws SQLException;
    User getUser(String username) throws  SQLException;

    void removeUser(User user) throws SQLException;

    List<User> getUsers() throws SQLException;

    User authenticate(String username, String password) throws SQLException;
}
