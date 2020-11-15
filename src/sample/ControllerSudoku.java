package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerSudoku {

    //déclaration des "variables" utilisées
    @FXML
    private Button lancerPartie;
    @FXML
    private Button fermerJeu;

    //BoutonLancer : au click => lancerPartie()
    @FXML
    private void lancerPartie(MouseEvent event) throws IOException {
        FXMLLoader grilleLoader = new FXMLLoader(getClass().getResource("Grille.fxml"));
        Parent firstPane = grilleLoader.load();

        Scene sceneGrille = new Scene(firstPane, 880, 700);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(sceneGrille);
    }

    //BoutonQuitter : au click => quitterJeu()   (fermer la fenêtre)
    @FXML
    private void fermerJeu(MouseEvent event) {
        Platform.exit();
    }
}
