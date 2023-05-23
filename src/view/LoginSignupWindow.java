package view;

import controller.LoginController;
import controller.SignupController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginSignupWindow extends JFrame {
    private JComboBox<String> userTypeComboBox;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel userTypeLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginSignupWindow() {
        setTitle("Нэвтрэх");
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

        loginButton = new JButton("Login");
        bottomPanel.add(loginButton);

        signupButton = new JButton("Signup");
        bottomPanel.add(signupButton);

        UserDAOImpl userModel = null;
        DoctorDAOImpl doctorModel = null;
        NurseDAOImpl nurseModel = null;
        try {
            userModel = UserDAOImpl.getInstance();
            doctorModel = DoctorDAOImpl.getInstance();
            nurseModel = NurseDAOImpl.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        new LoginController(this, userModel, doctorModel, nurseModel);
    }

    public JComboBox<String> getUserTypeComboBox() {
        return userTypeComboBox;
    }

    public void setUserTypeComboBox(JComboBox<String> userTypeComboBox) {
        this.userTypeComboBox = userTypeComboBox;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public void setSignupButton(JButton signupButton) {
        this.signupButton = signupButton;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JLabel getUserTypeLabel() {
        return userTypeLabel;
    }

    public void setUserTypeLabel(JLabel userTypeLabel) {
        this.userTypeLabel = userTypeLabel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public static void main(String[] args) {
        LoginSignupWindow window = new LoginSignupWindow();

        window.setVisible(true);
    }
}