package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private List<Appointment> appointments;

    public Doctor(String name, String username, String phoneNumber, String password, List<Appointment> appointments) {
        super(name, username, phoneNumber, password);
        this.appointments = appointments;
    }
    public Doctor(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password);
        this.appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
