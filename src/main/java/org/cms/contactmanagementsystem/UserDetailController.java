package org.cms.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.cms.jdbc.DatabaseConnectivity;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    private ContactManagerController cmController;
    private ContactManager contactManager;
    boolean edited = false;

    @FXML
    private Button closeViewContact;

    @FXML
    private ToggleButton editContact;

    @FXML
    private TextArea notes;

    @FXML
    private TextField numberOfContacts;

    @FXML
    private TextField viewAddress;

    @FXML
    private TextField viewEmail;

    @FXML
    private TextField viewName;

    @FXML
    private TextField viewPhone;

    public void loadUserDetail() {
        try {
            ResultSet resultSet = DatabaseConnectivity.fetchUserByID(contactManager.getUser().getID());

            while(resultSet.next()) {
                viewName.setText(resultSet.getString("username"));
                viewEmail.setText(resultSet.getString("email"));
                viewAddress.setText(resultSet.getString("address"));
                viewPhone.setText(resultSet.getString("phone_number"));
                notes.setText(resultSet.getString("note"));
                numberOfContacts.setText(resultSet.getString("number_of_contacts"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEdit(ActionEvent e) {
        if(editContact.isSelected()) {
            viewName.setEditable(true);
            viewEmail.setEditable(true);
            viewAddress.setEditable(true);
            viewPhone.setEditable(true);
            notes.setEditable(true);

            edited = true;
        } else {
            viewName.setEditable(false);
            viewEmail.setEditable(false);
            viewAddress.setEditable(false);
            viewPhone.setEditable(false);
            notes.setEditable(false);
        }
    }

    public void getContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void setCmController(ContactManagerController cmController) {
        this.cmController = cmController;
    }

    public void close(ActionEvent e) throws SQLException {
        if(edited) {
            String name = viewName.getText();
            String email = viewEmail.getText();
            String address = viewAddress.getText();
            String number = viewPhone.getText();
            String note = notes.getText();

            contactManager.getUser().setName(name);
            contactManager.getUser().setPhoneNumber(Long.parseLong(number));
            contactManager.getUser().setEmail(email);
            contactManager.getUser().setAddress(address == null ? "" : address);
            contactManager.getUser().setNote(note == null ? "" : note);

            contactManager.editUser();
            edited = false;
        }
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.close();
        cmController.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
