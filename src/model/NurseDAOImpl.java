package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDAOImpl implements NurseDAO{
    private static NurseDAOImpl instance = null;
    private Connection connection;

    private NurseDAOImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinical_appointments", "root", "password");
    }

    public static NurseDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new NurseDAOImpl();
        }
        return instance;
    }

    @Override
    public void addNurse(Nurse nurse) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("INSERT INTO nurses (name, username, phoneNumber, password) VALUES (?, ?, ?, ?)");
            statement.setString(1, nurse.getName());
            statement.setString(2, nurse.getUsername());
            statement.setString(3, nurse.getPhoneNumber());
            statement.setString(4, nurse.getPassword());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public void removeNurse(Nurse nurse) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM nurses WHERE username = ?");
            statement.setString(1, nurse.getUsername());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Nurse> getNurses() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM nurses ORDER BY name");
            resultSet = statement.executeQuery();
            List<Nurse> nurses = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String phoneNumber = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                Nurse nurse = new Nurse(name, username, phoneNumber, password);
                nurses.add(nurse);
            }
            return nurses;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public Nurse authenticate(String username, String password) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM doctors where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phoneNumber");
                return new Nurse(name, username, phone, password);
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
