package model;

import java.util.Date;

public class CancelledState implements AppointmentState2 {
    private Appointment appointment;

    public CancelledState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void reschedule(Date newDate) {

    }
}
