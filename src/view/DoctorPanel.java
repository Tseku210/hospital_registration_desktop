package view;

import model.Doctor;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DoctorPanel extends JFrame implements Observer {
    private String doctorName;

    public DoctorPanel(Doctor doctor) {
        this.doctorName = doctor.getName();

        setTitle("Doctor Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Create a label to display the doctor's name
        JLabel nameLabel = new JLabel("Welcome, Dr. " + doctorName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(nameLabel, BorderLayout.NORTH);

        // Create a list to display the appointments
        JList<String> appointmentList = new JList<>(new String[] {
                "Appointment 1",
                "Appointment 2",
                "Appointment 3"
        });
        JScrollPane scrollPane = new JScrollPane(appointmentList);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons to mark appointments as completed or remove them
        JButton completeButton = new JButton("Mark as Completed");
        JButton removeButton = new JButton("Remove Appointment");

        // Add the buttons to a panel and add the panel to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}