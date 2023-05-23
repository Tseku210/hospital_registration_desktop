package model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person{
    private Appointment appointment;

    public User(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password);
    }

    public User(String name, String username, String phoneNumber, String password, Appointment appointment) {
        super(name, username, phoneNumber, password);
        this.appointment = appointment;
    }


    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


}
