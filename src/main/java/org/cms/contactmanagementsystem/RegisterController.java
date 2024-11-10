package org.cms.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController implements SceneLoader{
    private final UserAuthentication authentication = new UserAuthentication();

    @FXML
    private TextField emailRegister;

    @FXML
    private PasswordField passwordRegister;

    @FXML
    private TextField phoneRegister;

    @FXML
    private PasswordField rtPasswordRegister;

    @FXML
    private TextField usernameRegister;

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    void registerAccount(ActionEvent event) {
        if(authentication.verifyAccount(usernameRegister.getText(), passwordRegister.getText(), rtPasswordRegister.getText(), emailRegister.getText(), phoneRegister.getText())) {

            try {
                authentication.registerAccount(usernameRegister.getText(), passwordRegister.getText(), Long.parseLong(phoneRegister.getText()), emailRegister.getText());
                loadScene("LoginScene.fxml", event);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    void switchToLoginForm(MouseEvent event) {
        loadScene("LoginScene.fxml", event);
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
