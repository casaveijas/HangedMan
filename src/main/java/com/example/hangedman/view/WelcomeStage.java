package com.example.hangedman.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeStage extends Stage {

    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/hangedman/welcome-view.fxml"));
        Parent root = loader.load();
        setTitle("Ahorcado");
        //System.out.println(getClass().getResource("/com/example/hangedman/images/favicon.png"));
        Image icon = new Image(getClass().getResourceAsStream("/com/example/hangedman/images/favicon.png"));
        getIcons().add(icon);
        Scene scene = new Scene(root);
        setScene(scene);
        show();
        setResizable(false);
    }

    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }

    public static WelcomeStage getInstance() throws IOException {
        return WelcomeStageHolder.INSTANCE = new WelcomeStage();
    }

    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }
}
