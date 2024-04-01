package com.example.hangedman.controller;

import com.example.hangedman.model.SecretWord;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class GameController {
    @FXML
    private HBox hboxLayout;

    private SecretWord secretWord;
    private TextField wordsTxts[];

    @FXML
    void onHandleButtonPlay(ActionEvent event) {
        wordsTxts = new TextField[secretWord.getWord().length()];

        for (int i=0; i<secretWord.getWord().length(); i++){
            TextField wordTxt = new TextField();
            hboxLayout.getChildren().add(wordTxt);
            keyPressed(wordTxt, i);
            wordsTxts[i] = wordTxt;
        }
    }

    @FXML
    void onHandleButtonHelp(ActionEvent event) {
        for (int i=0; i<wordsTxts.length; i++){
           if(wordsTxts[i].getText() == ""){
               System.out.println("Colocando palabra secreta");
               // Aqui deberian hacer la logica para que coloque una palabra secreta en el campo de texto.
               break;
           }
        }
    }

    @FXML
    private void keyPressed(TextField wordTxt, int position){
        wordTxt.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String letter = keyEvent.getText().trim();
                String[] arrayWord = secretWord.getArrayWord();
                System.out.println(letter + " " + arrayWord[position]);
            }
        });
    }

    public void setSecretWord(SecretWord secretWord) {
        this.secretWord = secretWord;
    }


}
