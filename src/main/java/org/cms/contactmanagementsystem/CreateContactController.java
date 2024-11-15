package org.cms.contactmanagementsystem;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;

public class CreateContactController {
    ContactManager contactManager;
    User user;

    public ObservableList<String> contacts;

    @FXML
    private TextField contactAddress;

    @FXML
    private TextField contactEmail;

    @FXML
    private TextField contactName;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField contactRelationship;

    ContactManagerController controller;
    Contact newContact;

    @FXML
    void addNewContact(ActionEvent event) throws IOException, SQLException {
        String name = contactName.getText();
        String phone = contactNumber.getText();
        String email = contactEmail.getText();
        String address = contactAddress.getText();
        String relationship = contactRelationship.getText();

        if(name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill in the name", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setWidth(220);
            alert.showAndWait();
            return;
        } else if(phone.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill in a phone number", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setWidth(220);
            alert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(phone);
        } catch(NumberFormatException numExc) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter phone number", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setWidth(220);
            alert.showAndWait();
            return;
        }

        contacts.add(name);
        contacts.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        newContact = contactManager.createContact(name,phone,email,address,relationship);
        contactManager.addContact(newContact);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void getUser(User user) {
        this.user = user;
    }

    public void addContactToList(ObservableList<String> contacts) {
        this.contacts = contacts;
    }

    public void getContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void close(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
