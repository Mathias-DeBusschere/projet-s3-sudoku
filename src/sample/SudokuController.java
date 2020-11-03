package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.IOException;

public class SudokuController {
    //déclaration de la scène
    private Scene sceneSudoku;
    public void setSceneSudoku(Scene scene) {
        sceneSudoku = scene;
    }
    //déclaration des "variables" utilisées
    @FXML
    private Button lancerPartie;
    @FXML
    private Button quitterJeu;


    //BoutonLancer : au click => lancerPartie()
    //BoutonQuitter : au click => quitterJeu()   (fermer la fenêtre)

    public void lancerPartie(ActionEvent event) throws IOException {
        //fonction lancer la partie
    }

    public void quitterJeu(ActionEvent event) throws  IOException {
        //fonction quitter le jeu
    }

}
