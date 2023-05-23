package model;

import java.util.Date;

public interface AppointmentState2 {
    void schedule();
    void cancel();
    void reschedule(Date newDate);
}
