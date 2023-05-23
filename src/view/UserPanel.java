package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.List;

public class UserPanel extends JFrame implements Observer {
    private JComboBox<String> checkupComboBox;
    private JComboBox<String> treatmentComboBox;

    JPanel buttonPanel;
    JList<String> appointmentPanel;

    private User user;

    public UserPanel(User user) {
        this.user = user;
        AppointmentDAOImpl.getInstance().addObserver(this);

        setTitle("Хэрэглэгч " + user.getName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        checkupComboBox = new JComboBox<>(generateTimes());

        treatmentComboBox = new JComboBox<>(generateTimes());


        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Үзлэгийн цаг:"));
        comboBoxPanel.add(checkupComboBox);
        comboBoxPanel.add(new JLabel("Эмчилгээний цаг:"));
        comboBoxPanel.add(treatmentComboBox);
        add(comboBoxPanel, BorderLayout.NORTH);

        appointmentPanel = new JList<>();
        List<Appointment> appointments = AppointmentDAOImpl.getInstance().getAppointments();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Appointment appointment : appointments) {
            User appointmentUser = appointment.getUser();
            if (appointmentUser != null && appointmentUser.getUsername().equals(user.getUsername())) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMdd HH:mm", new Locale("mn"));
                String formattedDate = dateFormat.format(appointment.getTime());
                model.addElement(formattedDate);
            }
        }
        appointmentPanel.setModel(model);
        add(appointmentPanel, BorderLayout.CENTER);

        JButton btnScheduleCheckup = new JButton("Үзлэгийн цаг авах");
        JButton btnScheduleTreatment = new JButton("Эмчилгээний цаг авах");
        JButton btnCancel = new JButton("Cancel");

        buttonPanel = new JPanel();
        buttonPanel.add(btnScheduleCheckup);
        buttonPanel.add(btnScheduleTreatment);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnScheduleCheckup.addActionListener(e -> scheduleCheckup());
        btnScheduleTreatment.addActionListener(e -> scheduleTreatment());
        btnCancel.addActionListener(e -> cancelAppointment());
    }

    private String[] generateTimes() {
        ArrayList<String> times = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMd HH:mm", new Locale("mn"));

        for (int hour = 9; hour <= 17; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                LocalDateTime dateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);
                if (dateTime.getDayOfWeek().getValue() >= 1 && dateTime.getDayOfWeek().getValue() <= 5) {
                    String formatted = dateTime.format(formatter);
                    times.add(formatted);
                }
            }
        }

        return times.toArray(new String[0]);
    }

    private void scheduleCheckup() {
        String selectedTime = (String) checkupComboBox.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMdd HH:mm", new Locale("mn"));
        Date date = null;
        try {
            date = dateFormat.parse(selectedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Appointment appointment = AppointmentFactory.createAppointment(user, date, AppointmentType.CHECKUP);

        if (AppointmentDAOImpl.getInstance().addAppointment(appointment)) {
            JOptionPane.showMessageDialog(this, "Амжилттай үзлэгийн цаг авлаа");

        } else {
            JOptionPane.showMessageDialog(this, "Цаг товлоход алдаа гарлаа. Дахин оролдоно уу");
        }
    }

    private void scheduleTreatment() {

    }

    private void cancelAppointment() {
        String selectedTime = (String) appointmentPanel.getSelectedValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMdd HH:mm", new Locale("mn"));
        Date date = null;
        try {
            date = dateFormat.parse(selectedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AppointmentDAOImpl.getInstance().getAppointment(date, user);
        AppointmentDAOImpl.getInstance().cancelAppointment(appointment);
        JOptionPane.showMessageDialog(this, "Aмжилттай цуцлагдлаа");
    }

    private void rescheduleAppointment() {
        // TODO: Implement reschedule appointment functionality
    }

    @Override
    public void update(Observable observable, Object o) {
        List<Appointment> appointments = AppointmentDAOImpl.getInstance().getAppointments();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Appointment appointment : appointments) {
            User appointmentUser = appointment.getUser();
            if (appointmentUser != null && appointmentUser.getUsername().equals(user.getUsername())) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMdd HH:mm", new Locale("mn"));
                String formattedDate = dateFormat.format(appointment.getTime());
                model.addElement(formattedDate);
            }
        }
        appointmentPanel.setModel(model);
    }
}
