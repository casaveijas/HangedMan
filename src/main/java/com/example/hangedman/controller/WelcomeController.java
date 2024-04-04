package com.example.hangedman.controller;

import com.example.hangedman.model.SecretWord;
import com.example.hangedman.view.GameStage;
import com.example.hangedman.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private TextField secretWordTextField;
    @FXML
    private TextField hintText1;
    @FXML
    private TextField hintText2;
    @FXML
    private TextField hintText3;
    @FXML
    private ImageView titleImageView;
    @FXML
    private ImageView backgroundImageView;

    private SecretWord secretWord;

    @FXML
    private Button startButton;

    @FXML
    private void initialize() {
        //animación del button
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), startButton);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), startButton);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        startButton.setOnMouseEntered(event -> {
            scaleIn.play();
        });

        startButton.setOnMouseExited(event -> {
            scaleOut.play();
        });
        //animación del fondo
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), backgroundImageView);
        translateTransition.setFromY(0);
        translateTransition.setToY(-100);
        translateTransition.setFromX(0);
        translateTransition.setToX(-1000);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        //animación del titulo
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), titleImageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.15);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.play();
    }
    @FXML
    void onHandleButtonPlay(ActionEvent event) throws IOException {
        String word = secretWordTextField.getText().trim().toLowerCase();
        String [] hintsWord = {word,hintText1.getText().trim(),hintText2.getText().trim(),hintText3.getText().trim()};
        if (isValidWord(word) && isValidHint(hintsWord[1]) && isValidHint(hintsWord[2]) && isValidHint(hintsWord[3])) {
            WelcomeStage.deleteInstance();
            GameStage.getInstance().getGameController().setHints(hintsWord);
        } else {
            showAlert("Error", "La palabra ingresada no es válida o la pista es muy larga.");
        }
    }

    private boolean isValidWord(String word) {
        if (word.length()>=3 && word.length()<=11){
            return word.matches("[a-z]+");
        }
        else{
            return false;
        }
    }

    private boolean isValidHint(String word) {
        return word.length() <= 30;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}