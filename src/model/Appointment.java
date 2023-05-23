package model;

import java.util.Date;

public class Appointment {
    private int id;
    private Date time;
    private User user;
    private AppointmentState2 state;

    public Appointment(int id, Date time, User user) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.state = new ScheduledState(this);
    }

    public Appointment(Date time, User user) {
        this.time = time;
        this.user = user;
        this.state = new ScheduledState(this);
    }

    public void schedule() {
        state.schedule();
    }

    public void reschedule(Date newDate) {
        state.reschedule(newDate);
    }

    public void cancel() {
        state.cancel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppointmentState2 getState() {
        return state;
    }

    public void setState(AppointmentState2 state) {
        this.state = state;
    }
}
