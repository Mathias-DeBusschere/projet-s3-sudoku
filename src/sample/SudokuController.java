package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SudokuController extends Parent {
    //déclaration de la scène
    private Scene sceneSudoku;
    public void setSceneSudoku(Scene scene) {
        sceneSudoku = scene;
    }
    //déclaration des "variables" utilisées
    @FXML
    private Button lancerPartie;
    @FXML
    private Button fermerJeu;


    //BoutonLancer : au click => lancerPartie()
    //BoutonQuitter : au click => quitterJeu()   (fermer la fenêtre)

    public void lancerPartie(MouseEvent event) throws IOException {
    }

    public void fermerJeu(MouseEvent event) throws  IOException {
        Platform.exit();
    }

}
