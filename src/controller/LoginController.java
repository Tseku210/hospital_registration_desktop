package controller;

import model.*;
import view.DoctorPanel;
import view.LoginSignupWindow;
import view.SignupWindow;
import view.UserPanel;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private LoginSignupWindow view;
    private UserDAOImpl userModel;
    private DoctorDAOImpl doctorModel;
    private NurseDAOImpl nurseModel;

    public LoginController(LoginSignupWindow view, UserDAOImpl model) {
        this.view = view;
        this.userModel = model;

        // Attach listeners to buttons
        view.getLoginButton().addActionListener(e -> switchToSignup());
        view.getSignupButton().addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public LoginController(LoginSignupWindow view, UserDAOImpl model, DoctorDAOImpl doctorModel, NurseDAOImpl nurseModel) {
        this.view = view;
        this.userModel = model;
        this.doctorModel = doctorModel;
        this.nurseModel = nurseModel;

        // Attach listeners to buttons
        view.getSignupButton().addActionListener(e -> switchToSignup());
        view.getLoginButton().addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void switchToSignup() {
        view.dispose();
        SignupWindow signupWindow = null;
        try {
            signupWindow = new SignupWindow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        signupWindow.setVisible(true);
    }

    private void login() throws SQLException {
        String username = view.getUsernameField().getText();
        String password = String.valueOf(view.getPasswordField().getPassword());
        String userType = (String) view.getUserTypeComboBox().getSelectedItem();

        Person person = null;
        switch (userType) {
            case "Хэрэглэгч" -> {
                person = userModel.authenticate(username, password);
            }
            case "Эмч" -> {
                person = doctorModel.authenticate(username, password);
            }
            case "Сувилагч" -> {
                person = nurseModel.authenticate(username, password);
            }
            default -> {
                JOptionPane.showMessageDialog(view, "Буруу хэрэглэгчийн төрөл");
                return;
            }
        }

        if (person == null) {
            JOptionPane.showMessageDialog(view, "Нэвтрэх нэр эсвэл нууц үг буруу");
        } else {
            // Open appropriate panel based on user type
            if (person instanceof User) {
                UserPanel userPanel = new UserPanel((User) person);
                userPanel.setVisible(true);
            } else if (person instanceof Doctor) {
                DoctorPanel doctorPanel = new DoctorPanel((Doctor) person);
                doctorPanel.setVisible(true);
            } else if (person instanceof Nurse) {
//                NursePanel nursePanel = new NursePanel((Nurse) person);
//                nursePanel.setVisible(true);
            }

            // Close login window
            view.dispose();
        }
    }

}
