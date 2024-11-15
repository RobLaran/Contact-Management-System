package org.cms.contactmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("Contact Manager");
        Image logo = new Image(App.class.getResourceAsStream("/img/cmLogo.png"));
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}