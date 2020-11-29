package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ControllerSudoku {

    public void initialize() {
        difficulte.getSelectionModel().select(0);
        difficulte.valueProperty().addListener((observableValue, d, d1) -> difficulte.getSelectionModel().select(d1));
        taille.getSelectionModel().select(1);
        taille.valueProperty().addListener(((observableValue, integer, t1) -> taille.getSelectionModel().select(t1)));
    }

    //déclaration des "variables" utilisées
    @FXML
    private AnchorPane contain;
    @FXML
    private Button lancerPartie;
    @FXML
    private Button fermerJeu;
    @FXML
    private Button regles;
    @FXML
    private ChoiceBox<Difficulte> difficulte;
    @FXML
    private ChoiceBox<Integer> taille;

    //BoutonLancer : au click => lancerPartie()
    @FXML
    private void lancerPartie(MouseEvent event) throws IOException {
        FXMLLoader grilleLoader = new FXMLLoader(getClass().getResource("Grille.fxml"));

        ControllerGrille controllerGrille = new ControllerGrille(taille.getValue(),difficulte.getValue());
        grilleLoader.setController(controllerGrille);

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
