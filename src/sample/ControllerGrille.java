package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

        Grille g2 = new Grille(matrice);
        addElement(g2);

    }

    @FXML
    private AnchorPane grille;

    public void addElement(Node node) {
        grille.getChildren().add(node);
    }

}
