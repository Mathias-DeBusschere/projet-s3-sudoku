package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Sudoku.fxml"));
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root, 860, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//
//        GrilleController g1 = new GrilleController(9);
//        System.out.println(g1.toString());
//
//        int[][] matrice = {
//                {0,0,3,0,0,4,5,0,2},
//                {0,5,0,0,0,3,0,0,0},
//                {0,0,8,0,0,5,3,6,0},
//
//                {0,0,0,2,0,0,7,4,3},
//                {2,7,0,3,0,0,0,8,0},
//                {3,4,0,7,5,0,0,0,0},
//
//                {0,0,5,4,0,0,0,0,6},
//                {9,0,2,0,0,0,0,5,0},
//                {4,0,0,0,0,2,9,0,0},
//        };
//
//        int[][] matriceCorrect = {
//                {7,9,3,8,6,4,5,1,2},
//                {6,5,4,1,2,3,8,9,7},
//                {1,2,8,9,7,5,3,6,4},
//
//                {5,8,6,2,1,9,7,4,3},
//                {2,7,9,3,4,6,1,8,5},
//                {3,4,1,7,5,8,6,2,9},
//
//                {8,3,5,4,9,1,2,7,6},
//                {9,1,2,6,3,7,4,5,8},
//                {4,6,7,5,8,2,9,3,1},
//        };
//
//
//        GrilleController g2 = new GrilleController(matrice);
//        GrilleController g3 = new GrilleController(matriceCorrect);
//        System.out.println(g2.toString());
//        System.out.println(g2.valueIsCorrect(0,2));

    }

}
