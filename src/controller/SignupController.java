package controller;

import model.*;
import view.LoginSignupWindow;
import view.SignupWindow;

import javax.print.Doc;
import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;

public class SignupController {
    private SignupWindow view;
    private UserDAOImpl userModel;
    private DoctorDAOImpl doctorModel;
    private NurseDAOImpl nurseModel;

    public SignupController(SignupWindow view, UserDAOImpl model) {
        this.view = view;
        this.userModel = model;

        // Attach listeners to buttons
        view.getLoginButton().addActionListener(e -> switchToLogin());
        view.getSignupButton().addActionListener(e -> {
            try {
                signup();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public SignupController(SignupWindow view, UserDAOImpl model, DoctorDAOImpl doctorModel, NurseDAOImpl nurseModel) {
        this.view = view;
        this.userModel = model;
        this.doctorModel = doctorModel;
        this.nurseModel = nurseModel;

        // Attach listeners to buttons
        view.getLoginButton().addActionListener(e -> switchToLogin());
        view.getSignupButton().addActionListener(e -> {
            try {
                signup();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void switchToLogin() {
        view.dispose();
        LoginSignupWindow loginWindow = new LoginSignupWindow();
        loginWindow.setVisible(true);
    }

    private void signup() throws SQLException {
        String name = view.getNameField().getText();
        String username = view.getUsernameField().getText();
        String phoneNumber = view.getPhoneField().getText();
        String password = String.valueOf(view.getPasswordField().getPassword());
        String userType = (String) view.getUserTypeComboBox().getSelectedItem();

        if (name.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Бүгдийг нь бөглөнө үү");
            return;
        }

        Person person = null;
        switch (userType) {
            case "Хэрэглэгч" -> {
                person = new User(name, username, phoneNumber, password);
                userModel.addUser((User) person);
            }
            case "Эмч" -> {
                person = new Doctor(name, username, phoneNumber, password);
                doctorModel.addDoctor((Doctor) person);
            }
            case "Сувилагч" -> {
                person = new Nurse(name, username, phoneNumber, password);
                nurseModel.addNurse((Nurse) person);
            }
            default -> {
            }
        }

        JOptionPane.showMessageDialog(view, userType + " төрлийн " + username + " нэвтрэх нэртэй хэрэглэгч " + "амжилттай бүртгүүллээ");

        view.getNameField().setText("");
        view.getUsernameField().setText("");
        view.getPhoneField().setText("");
        view.getPasswordField().setText("");
    }
}
