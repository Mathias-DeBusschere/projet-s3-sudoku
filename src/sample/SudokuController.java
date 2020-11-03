package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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


}
