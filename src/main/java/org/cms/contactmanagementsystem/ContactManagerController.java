package org.cms.contactmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.FXPermission;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ContactManagerController implements SceneLoader, Initializable {
    boolean selected = false;
    ContactManager contactManager;

    ObservableList<String> contacts;

    @FXML
    ListView<String> contactList;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField searchBar;

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    void openCreateContactForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateContact.fxml"));
            Parent sceneRoot = loader.load();

            CreateContactController controller = loader.getController();
            controller.getContactManager(contactManager);
            controller.getUser(contactManager.getUser());
            controller.addContactToList(contacts);

            Stage secondaryStage = new Stage();
            Scene secondaryScene = new Scene(sceneRoot);

            secondaryStage.setScene(secondaryScene);
            secondaryStage.setResizable(false);
            secondaryStage.sizeToScene();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setX(950);
            secondaryStage.setY(100);
            secondaryStage.show();
        } catch (IOException ioExc) {
            throw new RuntimeException(ioExc);
        }
    }

    @FXML
    void switchToLoginForm(ActionEvent event) {
        loadScene("LoginScene.fxml", event);
        contacts.clear();
    }

    @FXML
    void deleteContact(ActionEvent event) throws SQLException {
            String contactName = contactList.getSelectionModel().getSelectedItem();
        if(!contactList.getSelectionModel().isEmpty() && !contactName.isBlank()) {
            Contact contactToDelete = contactManager.getContact(contactName);

            contactManager.deleteContact(contactToDelete);
            contactList.getItems().remove(contactName);
        }

    }

    public void setUser(User user) {
        contactManager = new ContactManager();
        contactManager.setUser(user);
        userNameLabel.setText(user.getName());
    }

    public void loadContacts() throws SQLException {
        contactList.getItems().addAll(contactManager.loadContacts());
    }

    public void contactSelection(MouseEvent e) {
        if(selected) {
            selected = false;
            contactList.getSelectionModel().clearSelection();
        } else {
            selected = true;
        }

    }

    public void viewContact(ActionEvent e) {
        String contactName = contactList.getSelectionModel().getSelectedItem();

        if(contactName != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewContactScene.fxml"));
                Parent sceneRoot = loader.load();

                ViewContactController controller = loader.getController();
                controller.getContactManager(contactManager);
                controller.setCurrentContact(contactManager.getContact(contactName));
                controller.loadContactDetails(contactName);

                Scene secondaryScene = new Scene(sceneRoot);
                Stage secondaryStage = new Stage();
                secondaryStage.initStyle(StageStyle.UNDECORATED);
                secondaryStage.setScene(secondaryScene);
                secondaryStage.setResizable(false);
                secondaryStage.sizeToScene();
                secondaryStage.initModality(Modality.APPLICATION_MODAL);
                secondaryStage.setX(950);
                secondaryStage.setY(100);
                secondaryStage.show();
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }
    }

    public void searchContact(KeyEvent e) {
        if(!searchBar.getText().isEmpty() && e.getCode() == KeyCode.ENTER) {
            contactList.getSelectionModel().select(searchBar.getText());

            if(contactList.getSelectionModel().isEmpty()) {
                System.out.println("Contact not found");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contact not found", ButtonType.OK);
                alert.setWidth(220);
                alert.setResizable(false);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @Override
    public void loadScene(String fxml, ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            root = loader.load();

            LoginController controller = loader.getController();

            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ioExc) {
            throw new RuntimeException(ioExc);
        }
    }

    @Override
    public void loadScene(String fxml, MouseEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            root = loader.load();

            LoginController controller = loader.getController();

            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ioExc) {
            throw new RuntimeException(ioExc);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contacts = FXCollections.observableArrayList();
        contactList.setItems(contacts);
    }
}
