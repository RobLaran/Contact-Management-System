package org.cms.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController implements SceneLoader {
    private final UserAuthentication authentication = new UserAuthentication();

    @FXML
    private TextField usernameLogin, emailLogin;

    @FXML
    private Button loginButton;

    @FXML
    private Pane pane;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Label switchRegister;

    private Parent root;
    private Scene scene;
    private Stage stage;


    @FXML
    void fill(KeyEvent event) {
        if(event.isAltDown()) {
            usernameLogin.setText("rob");
            emailLogin.setText("laranrobelleney@gmail.com");
            passwordLogin.setText("laran");
        }
    }

    @FXML
    void loginUser(ActionEvent event) {
        if(authentication.verifyUser(usernameLogin.getText(), passwordLogin.getText(), emailLogin.getText())) {
                loadScene("ContactManagerScene.fxml", event);
        }
    }

    @FXML
    void switchToRegisterForm(MouseEvent event) {
        loadScene("RegisterScene.fxml", event);
    }

    @Override
    public void loadScene(String fxml, ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            root = loader.load();

            ContactManagerController controller = loader.getController();
            controller.setUser(authentication.getUser());
            controller.loadContacts();

            scene = new Scene(root);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();

            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void loadScene(String fxml, MouseEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            root = loader.load();

            RegisterController controller = loader.getController();


            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
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
