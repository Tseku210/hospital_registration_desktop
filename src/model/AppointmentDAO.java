package model;

import java.sql.SQLException;
import java.util.List;

public interface AppointmentDAO {
    boolean addAppointment(Appointment appointment) throws SQLException;

    void cancelAppointment(Appointment appointment) throws SQLException;

    List<Appointment> getAppointments() throws SQLException;

}
