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
        /*Parent grille = FXMLLoader.load(getClass().getResource("Grille.fxml"));
        //getScene(new Scene(grille, 860, 700));
        ((Stage) getScene().getWindow()).setScene(new Scene(grille,860,700));*/


        FXMLLoader grilleLoader = new FXMLLoader(getClass().getResource("Grille.fxml"));
        Parent firstPane = grilleLoader.load();

        Scene sceneGrille = new Scene(firstPane, 860, 700);

        Controller controller = (Controller) grilleLoader.getController();
        controller.setScene(((Node)event.getSource()).getScene());

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(sceneGrille);
    }

    public void fermerJeu(MouseEvent event) throws  IOException {
        Platform.exit();
    }
}
