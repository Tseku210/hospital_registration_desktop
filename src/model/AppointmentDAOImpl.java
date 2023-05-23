package model;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AppointmentDAOImpl extends Observable implements AppointmentDAO {
    private static AppointmentDAOImpl instance = null;
    private Connection connection;

    private AppointmentDAOImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinical_appointments", "root", "password");
    }

    public static AppointmentDAOImpl getInstance(){
        if (instance == null) {
            try {
                instance = new AppointmentDAOImpl();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public boolean addAppointment(Appointment appointment){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO appointments (time, userId) VALUES (?, ?)");
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.setString(2, appointment.getUser().getUsername());
            int numRowsAffected = statement.executeUpdate();
            setChanged();
            notifyObservers();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, null);
        }
    }

    public void cancelAppointment(Appointment appointment){
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM appointments WHERE time = ?");
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.executeUpdate();
            setChanged();
            notifyObservers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    public Appointment getAppointment(Date time, User user) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments WHERE time = ? ");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Date time = new Date(resultSet.getTimestamp("time").getTime());
                String username = resultSet.getString("userId");
                User user = UserDAOImpl.getInstance().getUser(username);
                Appointment appointment = new Appointment(time, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    public List<Appointment> getAppointments() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments ORDER BY time");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Date time = new Date(resultSet.getTimestamp("time").getTime());
                String username = resultSet.getString("userId");
                User user = UserDAOImpl.getInstance().getUser(username);
                Appointment appointment = new Appointment(time, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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