package org.cms.contactmanagementsystem;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.cms.jdbc.DatabaseConnectivity;
import org.cms.utilities.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAuthentication {
    User user;

    private Statement statement;
    private String userQuery;
    private ResultSet resultSet;

    private int userID;
    private String username;
    private Long userPhoneNumber;
    private String userEmail;
    private String userPassword;

    private Alert alert;

    int ID;
    String userName;
    Long number;
    String email;
    String password;

    public UserAuthentication() {
        this.statement = DatabaseConnectivity.setupConnection();
    }

    public boolean verifyAccount(String name, String password, String retypePassword, String email, String phoneNumber) {
        try {
            if(name.isBlank() || email.isBlank() || password.isBlank() || retypePassword.isBlank() || phoneNumber.isBlank()) {
                showAlert("Fill in the blank", Alert.AlertType.WARNING);
                return false;
            } else {
                if(registerUsername(name)) {
                    showAlert("Username already used", Alert.AlertType.WARNING);
                    return false;
                } else if(!Validator.validateEmail(email)) {
                    showAlert("Email incorrect", Alert.AlertType.WARNING);
                    return false;
                } else if(!checkRetypePassword(password, retypePassword)) {
                    showAlert("Password mismatch", Alert.AlertType.WARNING);
                    return false;
                } else if(!phoneNumberValidation(phoneNumber)) {
                    showAlert("Incorrect phone number or already used", Alert.AlertType.WARNING);
                    return false;
                } else if(registerEmail(email)) {
                    showAlert("Email already used", Alert.AlertType.WARNING);
                    return false;
                }
            }

            showAlert("You are now registered.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean registerUsername(String name) {
        return DatabaseConnectivity. findUsername(name);
    }

    private boolean registerEmail(String email) {
        return DatabaseConnectivity.findEmail(email);
    }

    public boolean verifyUser(String name, String password, String email)  {
        try {
            loadResultSet(name, password, email);

            if(name.isBlank() || password.isBlank() || email.isBlank()) {
                showAlert("Fill in the blank", Alert.AlertType.WARNING);
                return false;
            } else {
                if(!checkUsername(name)) {
                    showAlert("Incorrect Username", Alert.AlertType.WARNING);
                    return false;
                } else if(!checkPassword(name, password)){
                    showAlert("Incorrect Password", Alert.AlertType.WARNING);
                    return false;
                } else if(!checkEmail(name, password, email)) {
                    showAlert("Incorrect Email", Alert.AlertType.WARNING);
                    return false;
                }
            }

            showAlert("Successfully logged in", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void showAlert(String message , Alert.AlertType type) {
        alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setWidth(220);
        alert.showAndWait();
    }

    private boolean checkUsername(String name) throws SQLException {
        return DatabaseConnectivity.getUsername(name);
    }

    private boolean checkPassword(String name, String password) throws SQLException {
        return DatabaseConnectivity.getPassword(name, password);
    }

    private boolean checkEmail(String name, String password, String email) throws SQLException {
        return DatabaseConnectivity.getEmail(name, password, email);
    }

    private boolean phoneNumberValidation(String phoneNumber) {
        return Validator.validatePhoneNumber(phoneNumber) && !DatabaseConnectivity.findPhoneNumber(phoneNumber);
    }

    private boolean checkRetypePassword(String password, String retypePassword) throws SQLException {
        return password.equals(retypePassword);
    }

    public void registerAccount(String name, String password, Long phone, String email) throws SQLException {
        DatabaseConnectivity.insertUserData(name,password, phone, email);
    }

    public void loadResultSet(String name, String password, String email) throws SQLException {
        if(!name.isBlank()) {
            resultSet = DatabaseConnectivity.fetchUser(name, password, email);

            while(resultSet.next()) {
                user = new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getLong("phone_number"), resultSet.getString("email"),
                        resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setNumberOfContacts(resultSet.getInt("number_of_contacts"));
                user.setNote(resultSet.getString("note"));
            }
        }
    }

    private void setUser(int ID, String name, Long number, String email, String password) {
        this.user = new User(ID, name, number, email, password);
    }

    public User getUser() {
        return this.user;
    }

}
