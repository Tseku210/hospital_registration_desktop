package model;

import java.util.Date;

public class ScheduledState implements AppointmentState2{
    private Appointment appointment;

    public ScheduledState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {
        // юу ч хийхгүй
    }

    @Override
    public void cancel() {
        appointment.setState(new CancelledState(appointment));
    }

    @Override
    public void reschedule(Date newDate) {
        appointment.setState(new RescheduledState(appointment, newDate));
    }
}
