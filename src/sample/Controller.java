package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller extends Parent {

    /** SUDOKU */
    private Scene scene;
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    //déclaration des "variables" utilisées
    @FXML
    private Button lancerPartie;
    @FXML
    private Button fermerJeu;


    //BoutonLancer : au click => lancerPartie()
    //BoutonQuitter : au click => quitterJeu()   (fermer la fenêtre)
    @FXML
    public void lancerPartie(MouseEvent event) throws IOException {
        Parent grille = FXMLLoader.load(getClass().getResource("Grille.fxml"));
        //getScene(new Scene(grille, 860, 700));
        ((Stage) getScene().getWindow()).setScene(new Scene(grille,860,700));
    }


    /*void actionFlecheRetour2(MouseEvent event) throws IOException {
        FXMLLoader cabine2Loader = new FXMLLoader(getClass().getResource("Cabine2.fxml"));
        Parent firstPane = cabine2Loader.load();

        Scene sceneCabine2 = new Scene(firstPane, 1280, 720);

        Cabine2Controller cabine2Controller = (Cabine2Controller) cabine2Loader.getController();
        cabine2Controller.setSceneCabine2(((Node)event.getSource()).getScene());

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(sceneCabine2);
    }*/

    public void fermerJeu(MouseEvent event) throws  IOException {
        Platform.exit();
    }
}
