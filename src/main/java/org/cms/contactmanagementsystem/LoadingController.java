package org.cms.contactmanagementsystem;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    User user;

    @FXML
    private ProgressBar loadingBar;

    @FXML
    private Button updateBtn;

    @FXML
    private Label percentage;
    private ChangeListener<? super Number> Listener;

    public void passUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingBar.setProgress(0);

        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(1);
        timeLine.setAutoReverse(false);

        KeyValue kv = new KeyValue(loadingBar.progressProperty(), 1);
        KeyFrame kf = new KeyFrame(Duration.millis(1900), kv);
        timeLine.getKeyFrames().add(kf);
        timeLine.play();

        loadingBar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int percent = (int)(observableValue.getValue().doubleValue() * 100);

                Stage stage = (Stage) ((Node) loadingBar.getParent()).getScene().getWindow();
                if(percent == 100) {
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactManagerScene.fxml"));

                    try {
                        Parent root = loader.load();

                        ContactManagerController controller = loader.getController();
                        controller.setUser(user);
                        controller.loadContacts();

                        Scene scene = new Scene(root);
                        stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.setTitle("Contact Manager");
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/cmLogo.png")));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
}
