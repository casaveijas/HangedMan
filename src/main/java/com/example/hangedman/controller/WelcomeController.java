package com.example.hangedman.controller;

import com.example.hangedman.model.SecretWord;
import com.example.hangedman.view.GameStage;
import com.example.hangedman.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private TextField secretWordTextField;

    private SecretWord secretWord;

    @FXML
    void onHandleButtonPlay(ActionEvent event) throws IOException {
        secretWord = new SecretWord(secretWordTextField.getText().trim());
        WelcomeStage.deleteInstance();
        GameStage.getInstance().getGameController().setSecretWord(secretWord);
    }

    private boolean isValidWord(String word) {
        return word.matches("[a-zA-Z]+");
    }

}

