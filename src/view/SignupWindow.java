package view;

import controller.SignupController;
import model.*;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignupWindow extends JFrame{
    private JComboBox<String> userTypeComboBox;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel passwordLabel;
    private JLabel userTypeLabel;
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    public SignupWindow() throws SQLException {
        setTitle("Бүртгүүлэх");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        add(centerPanel, BorderLayout.CENTER);

        usernameLabel = new JLabel("Нэвтрэх нэр:");
        centerPanel.add(usernameLabel);

        usernameField = new JTextField();
        centerPanel.add(usernameField);

        nameLabel = new JLabel("Нэр:");
        centerPanel.add(nameLabel);

        nameField = new JTextField();
        centerPanel.add(nameField);

        phoneLabel = new JLabel("Утас:");
        centerPanel.add(phoneLabel);

        phoneField = new JTextField();
        centerPanel.add(phoneField);

        passwordLabel = new JLabel("Нууц үг:");
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        centerPanel.add(passwordField);

        userTypeLabel = new JLabel("Хэрэглэгчийн төрөл:");
        centerPanel.add(userTypeLabel);

        userTypeComboBox = new JComboBox<>(new String[]{"Хэрэглэгч", "Эмч", "Сувилагч"});
        centerPanel.add(userTypeComboBox);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        add(bottomPanel, BorderLayout.SOUTH);

        signupButton = new JButton("Signup");
        bottomPanel.add(signupButton);

        loginButton = new JButton("Login");
        bottomPanel.add(loginButton);

        UserDAOImpl userModel = UserDAOImpl.getInstance();
        DoctorDAOImpl doctorModel = DoctorDAOImpl.getInstance();
        NurseDAOImpl nurseModel = NurseDAOImpl.getInstance();
        new SignupController(this, userModel, doctorModel, nurseModel);
    }

    public JComboBox<String> getUserTypeComboBox() {
        return userTypeComboBox;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public static void main(String[] args) {
        LoginSignupWindow window = new LoginSignupWindow();
        window.setVisible(true);
    }
}
