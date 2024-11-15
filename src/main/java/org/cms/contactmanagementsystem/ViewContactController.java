package org.cms.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.cms.jdbc.DatabaseConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ViewContactController {
    ContactManager contactManager;
    Contact contact;
    boolean edited = false;

    @FXML
    private Button closeViewContact;

    @FXML
    private ToggleButton editContact;

    @FXML
    private TextArea notes;

    @FXML
    private TextField viewName, viewEmail, viewAddress, viewPhone, viewRelationship;

    public void loadContactDetails(String name) {
        try {
            ResultSet resultSet = DatabaseConnectivity.fetchContactWithName(name, contactManager.getUser().getID());

            while(resultSet.next()) {
                viewName.setText(resultSet.getString("contact_name"));
                viewEmail.setText(resultSet.getString("email"));
                viewAddress.setText(resultSet.getString("address"));
                viewPhone.setText(resultSet.getString("contact_number"));
                viewRelationship.setText(resultSet.getString("relationship"));
                notes.setText(resultSet.getString("note"));
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
            viewRelationship.setEditable(true);
            notes.setEditable(true);

            edited = true;
        } else {
            viewName.setEditable(false);
            viewEmail.setEditable(false);
            viewAddress.setEditable(false);
            viewPhone.setEditable(false);
            viewRelationship.setEditable(false);
            notes.setEditable(false);
        }
    }

    public void getContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void setCurrentContact(Contact contact) {
        this.contact = contact;
    }

    public void close(ActionEvent e) {
        if(edited) {
            String name = viewName.getText();
            String email = viewEmail.getText();
            String address = viewAddress.getText();
            String number = viewPhone.getText();
            String relationship = viewRelationship.getText();
            String note = notes.getText();

            this.contact.setName(name);
            this.contact.setPhoneNumber(number);
            this.contact.setEmail(email);
            this.contact.setAddress(address);
            this.contact.setRelationship(relationship);
            this.contact.setDescription(note);

            contactManager.editContact(this.contact);
            edited = false;
        }
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
