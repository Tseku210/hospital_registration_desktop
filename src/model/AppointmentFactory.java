package model;

import java.util.Date;

public class AppointmentFactory {
    public static Appointment createAppointment(User user, Date date, AppointmentType type) {
        Appointment appointment = null;
        switch (type) {
            case CHECKUP -> appointment = new CheckupAppointment(date, user);
            case TREATMENT -> appointment = new TreatmentAppointment(date, user);
            default -> {
            }
        }
        return appointment;
    }
}
