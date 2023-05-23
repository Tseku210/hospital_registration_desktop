package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    private static UserDAOImpl instance = null;
    private Connection connection;

    private UserDAOImpl() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinical_appointments", "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("INSERT INTO users (name, username, phoneNumber, password) VALUES (?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public User getUser(String username) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                User user = new User(name, username, phone, password);
                return user;
            }
            return null;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public void removeUser(User user) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users ORDER BY name");
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String phone = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                User user = new User(name, username, phone, password);
                users.add(user);
            }
            return users;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public User authenticate(String username, String password) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phoneNumber");
                return new User(name, username, phone, password);
            } else {
                return null;
            }
        } finally {
            close(statement, resultSet);
        }
    }

    private void close(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
