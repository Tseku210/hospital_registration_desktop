package model;

import java.sql.SQLException;
import java.util.List;

public interface DoctorDAO {
    void addDoctor(Doctor doctor) throws SQLException;

    void removeDoctor(Doctor doctor) throws SQLException;

    List<Doctor> getDoctors() throws SQLException;

    Doctor authenticate(String username, String password) throws SQLException;
}
