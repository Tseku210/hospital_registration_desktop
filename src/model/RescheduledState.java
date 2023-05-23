package model;

import java.util.Date;

public class RescheduledState implements AppointmentState2 {
    private Appointment appointment;
    private Date newDate;

    public RescheduledState(Appointment appointment, Date newDate) {
        this.appointment = appointment;
        this.newDate = newDate;
    }

    @Override
    public void schedule() {
        throw new UnsupportedOperationException("Та захиалсан байна");
    }

    @Override
    public void cancel() {
        appointment.setState(new CancelledState(appointment));
    }

    @Override
    public void reschedule(Date newDate) {
        appointment.setState(new RescheduledState(appointment, newDate));
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }
}
