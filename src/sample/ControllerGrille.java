package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGrille {

    public void initialize() {

        int[][] matrice = {
                /*{0,0,3,0,0,4,5,0,2},
                {0,5,0,0,0,3,0,0,0},
                {0,0,8,0,0,5,3,6,0},

                {0,0,0,2,0,0,7,4,3},
                {2,7,0,3,0,0,0,8,0},
                {3,4,0,7,5,0,0,0,0},

                {0,0,5,4,0,0,0,0,6},
                {9,0,2,0,0,0,0,5,0},
                {4,0,0,0,0,2,9,0,0},*/
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

        addElement(g2);
        g=g2;

    }

    @FXML
    private AnchorPane grille;
    @FXML
    private Grille g;
    @FXML
    private Button Valider;

    @FXML
    private Button Test;

    @FXML
    private void Valider(MouseEvent event) throws IOException {
        System.out.println("Validation Grille");
        System.out.println(g.isCorrect());
    }

    @FXML
    private void Test(MouseEvent event) throws IOException {
        System.out.println("Test");
        Test.setStyle("-fx-focus-color: red;");
        Test.setText("1");
    }

    public void addElement(Node node) {
        grille.getChildren().add(node);
    }

}
