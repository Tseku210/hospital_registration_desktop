package model;

import java.util.Date;

public class TreatmentAppointment extends Appointment{
    public TreatmentAppointment(int id, Date time, User user) {
        super(id, time, user);
    }

    public TreatmentAppointment(Date time, User user) {
        super(time, user);
    }
}
