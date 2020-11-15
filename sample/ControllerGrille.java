package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGrille {

    public void initialize() {

        int[][] matrice = {
                {0,0,3,0,0,4,5,0,2},
                {0,5,0,0,0,3,0,0,0},
                {0,0,8,0,0,5,3,6,0},

                {0,0,0,2,0,0,7,4,3},
                {2,7,0,3,0,0,0,8,0},
                {3,4,0,7,5,0,0,0,0},

                {0,0,5,4,0,0,0,0,6},
                {9,0,2,0,0,0,0,5,0},
                {4,0,0,0,0,2,9,0,0},
        };

        int[][] matriceCorrect = {
                {7,9,3,8,6,4,5,1,2},
                {6,5,4,1,2,3,8,9,7},
                {1,2,8,9,7,5,3,6,4},

                {5,8,6,2,1,9,7,4,3},
                {2,7,9,3,4,6,1,8,5},
                {3,4,1,7,5,8,6,2,9},

                {8,3,5,4,9,1,2,7,6},
                {9,1,2,6,3,7,4,5,8},
                {4,6,7,5,8,2,9,3,1},
        };


        Grille g2 = new Grille(matrice,matriceCorrect);

        //noinspection IntegerDivisionInFloatingPointContext
        double size= (600/matrice.length)*matrice.length;
        this.grille.setPrefSize(size,size);

        g2.setDifficulte(Difficulte.FACILE);
        addElement(g2);
        g2.setFocusTraversable(true);
        g=g2;

    }

    @FXML
    private StackPane grille;
    @FXML
    private Grille g;
    @FXML
    private Button valider;
    @FXML
    private Button recommencer;
    @FXML
    private Button fermerJeu;
    @FXML
    private Button pause;
    @FXML
    private Button reprendre;
    @FXML
    private Button indice;


    @FXML
    private void recommencer(MouseEvent event) throws IOException {
        FXMLLoader grilleLoader = new FXMLLoader(getClass().getResource("Grille.fxml"));
        Parent firstPane = grilleLoader.load();

        Scene sceneGrille = new Scene(firstPane, 880, 700);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(sceneGrille);
    }

    @FXML
    private void pause(MouseEvent event) {
        //timer stop
        //timer required
    }

    @FXML
    private void reprendre(MouseEvent event) {
        //timer play
        //timer required
    }

    @FXML
    private void indice(MouseEvent event) {

    }

    @FXML
    private void fermerJeu(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    private void valider(MouseEvent event) {
        System.out.println("Validation Grille");
        System.out.println(g.isCorrect());
    }

    @FXML
    private void actionBouton_1() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(1);}
    @FXML
    private void actionBouton_2() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(2);}
    @FXML
    private void actionBouton_3() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(3);}

    @FXML
    private void actionBouton_4() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(4);}
    @FXML
    private void actionBouton_5() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(5);}
    @FXML
    private void actionBouton_6() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(6);}

    @FXML
    private void actionBouton_7() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(7);}
    @FXML
    private void actionBouton_8() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(8);}
    @FXML
    private void actionBouton_9() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(9);}
    @FXML
    private void effacer() {if(!(g.getCaseselectionne() ==null))g.getCaseselectionne().setValeur(0);}


    public void addElement(Node node) {
        grille.setAlignment(Pos.BASELINE_CENTER);
        grille.getChildren().add(node);
    }

}
