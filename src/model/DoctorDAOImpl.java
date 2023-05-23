package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO{
    private static DoctorDAOImpl instance = null;
    private Connection connection;

    private DoctorDAOImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinical_appointments", "root", "password");
    }

    public static DoctorDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new DoctorDAOImpl();
        }
        return instance;
    }

    @Override
    public void addDoctor(Doctor doctor) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("INSERT INTO doctors (name, username, phoneNumber, password) VALUES (?, ?, ?, ?)");
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getUsername());
            statement.setString(3, doctor.getPhoneNumber());
            statement.setString(4, doctor.getPassword());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public void removeDoctor(Doctor doctor) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM doctors WHERE email = ?");
            statement.setString(1, doctor.getUsername());
            statement.executeUpdate();
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Doctor> getDoctors() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM doctors ORDER BY name");
            resultSet = statement.executeQuery();
            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                Doctor doctor = new Doctor(name, username, phone, password);
                doctors.add(doctor);
            }
            return doctors;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public Doctor authenticate(String username, String password) throws SQLException {
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
                return new Doctor(name, username, phone, password);
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
