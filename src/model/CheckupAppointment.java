package model;

import java.util.Date;

public class CheckupAppointment extends Appointment{
    public CheckupAppointment(int id, Date time, User user) {
        super(id, time, user);
    }

    public CheckupAppointment(Date time, User user) {
        super(time, user);
    }
}
