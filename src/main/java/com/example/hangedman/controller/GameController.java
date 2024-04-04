package com.example.hangedman.controller;

import com.example.hangedman.model.SecretWord;
import com.example.hangedman.view.GameStage;
import com.example.hangedman.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;

public class GameController {
    @FXML
    private TextField[] wordsTxts;
    @FXML
    private TextField hint1;
    @FXML
    private TextField hint2;
    @FXML
    private TextField hint3;
    @FXML
    private TextField[] hintTextFields;
    @FXML
    private TextField letterField;
    @FXML
    private Label livesLabel;
    @FXML
    private Button playButton;
    @FXML
    private ImageView gameBackgroundImageView;
    private String [] hintsWord;
    private int helpAttempts = 3;
    private int whichHint = 0;
    private int lives = 6;
    private int j = 0;

    @FXML
    private HBox hboxLayout;
    @FXML
    private ImageView imageView;

    @FXML
    private void initialize() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), gameBackgroundImageView);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToY(100);
        translateTransition.setToX(1000);

        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void setHints(String [] hints){this.hintsWord = hints;}

    @FXML
    private void zoomAnimation() {
        // Crear la transición de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), playButton);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), playButton);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        scaleTransition.setOnFinished(event -> {
            fadeTransition.play();
        });
        scaleTransition.play();
    }

    @FXML
    void onHandleButtonPlay(ActionEvent event) {
        SecretWord secretWord = new SecretWord(hintsWord[0]);
        zoomAnimation();
        playButton.setDisable(true);
        updateHangmanImage();

        wordsTxts = new TextField[secretWord.getWord().length()];
        hintTextFields = new TextField[]{hint1, hint2, hint3};
        for (int i = 0; i < 3; i++) {
            hintTextFields[i].setText(hintsWord[i+1]);
        }

        hboxLayout.getChildren().clear();

        for (int i = 0; i < secretWord.getWord().length(); i++) {
            TextField wordTxt = new TextField();
            wordTxt.setStyle("-fx-background-color: 0; -fx-border-color: #664783; -fx-border-width: 0 0 3 0; -fx-text-fill: white");
            wordTxt.setAlignment(Pos.CENTER);
            hboxLayout.getChildren().add(wordTxt);
            wordsTxts[i] = wordTxt;
        }
    }

    @FXML
    void onHandleButtonHelp(ActionEvent event) {
            MotionBlur clearMotionBlurEffect = new MotionBlur();
            clearMotionBlurEffect.setRadius(0);

            if (helpAttempts != 0) {
                hintTextFields[whichHint].setEffect(clearMotionBlurEffect);
                whichHint++;
                helpAttempts--;
            } else {
                showRemainingAttemptsAlert();
            }
        }

    private boolean fillLetters(char letter) {
        SecretWord secretWord = new SecretWord(hintsWord[0]);
        boolean filled = false;
        for (int i = 0; i < secretWord.getWord().length(); i++) {
            if (secretWord.getWord().charAt(i) == letter && wordsTxts[i].getText().isEmpty()) {
                wordsTxts[i].setText(Character.toString(letter));
                filled = true;
            }
        }
        return filled;
    }

    private void showRemainingAttemptsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Intentos agotados");
        alert.setHeaderText(null);
        alert.setContentText("Ya has utilizado tus 3 intentos de ayuda.");
        alert.showAndWait();
    }

    @FXML
    void OnHandleButtonBack(ActionEvent event) throws IOException {
        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }

    @FXML
    void OnHandleButtonValidateLetter(ActionEvent event) {
        SecretWord secretWord = new SecretWord(hintsWord[0]);
        if (isGameFinished()) {
            return;
        }

        String letter = letterField.getText().toLowerCase();

        if (isValidLetter(letter)) {
            boolean found = false;

            for (int i = 0; i < secretWord.getWord().length(); i++) {
                if (secretWord.getArrayWord()[i].equals(letter)) {
                    wordsTxts[i].setText(letter);
                    found = true;
                }
            }

            if (!found) {
                lives--;
                livesLabel.setText(Integer.toString(lives));
                showAlert("Letra incorrecta", "La letra " + letter + " no está en la palabra secreta.");

                j++;
                updateHangmanImage();

                if (lives == 0) {
                    showAlert("¡Oh no!", "Has perdido todas tus vidas. ¡Inténtalo de nuevo!");
                }
            } else {

                if (isWordCompleted()) {
                    showAlert("¡Felicidades!", "¡Has ganado! Has adivinado la palabra secreta.");
                }
            }
        } else {
            showAlert("Entrada inválida", "Por favor ingresa una sola letra válida.");
        }
    }

    private boolean isValidLetter(String letter) {
        return letter.matches("[a-zA-Z]") && letter.length() == 1;
    }

    private boolean isWordCompleted() {
        for (TextField wordTxt : wordsTxts) {
            if (wordTxt.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateHangmanImage() {
        String imagePath = "/com/example/hangedman/images/" + j + ".png";
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(image);
    }

    private boolean isGameFinished() {
        return lives == 0 || isWordCompleted();
    }
}
