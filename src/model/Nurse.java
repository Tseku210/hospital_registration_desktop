package model;

import java.util.List;

public class Nurse extends Person{
    private List<Appointment> appointments;

    public Nurse(String name, String username, String phoneNumber, String password, List<Appointment> appointments) {
        super(name, username, phoneNumber, password);
        this.appointments = appointments;
    }

    public Nurse(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
