package sample;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Grille extends Parent {

    private Case [][] grille;
    private int taille;

    public Grille(int taille) {
        this.taille = taille;
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize((600/taille)*taille,(600/taille)*taille);
        gridPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setGridLinesVisible(true);
        this.grille = new Case[taille][taille];
        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                Case case1 = new Case();
                grille[i][j] = case1;
                gridPane.add(case1,i,j);
                if ((i+1)%sqrt(taille)==0 && i+1!=taille){
                    case1.addBorder("left");
                }
                if ((j+1)%sqrt(taille)==0 && j+1!=taille){
                    case1.addBorder("down");
                }
                if ((i+1)%sqrt(taille)==0 && i+1!=taille && (j+1)%sqrt(taille)==0 && j+1!=taille) {
                    case1.addBorder("leftdown");
                }
            }
        }
        getChildren().add(gridPane);
    }

    public Grille(int[][] valeurs) {
        this.taille = valeurs.length;
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize((600/taille)*taille,(600/taille)*taille);
        gridPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setGridLinesVisible(true);
        this.grille = new Case[taille][taille];
        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                Case case1 = new Case(valeurs[i][j]);
                grille[i][j] = case1;
                gridPane.add(case1,i,j);
                if ((i+1)%sqrt(taille)==0 && i+1!=taille){
                    case1.addBorder("left");
                }
                if ((j+1)%sqrt(taille)==0 && j+1!=taille){
                    case1.addBorder("down");
                }
                if ((i+1)%sqrt(taille)==0 && i+1!=taille && (j+1)%sqrt(taille)==0 && j+1!=taille) {
                    case1.addBorder("leftdown");
                }
            }
        }
        getChildren().add(gridPane);
    }

    private void visualGen() {

    }

    @Override
    public String toString() {
        String separator =" ";
        for (int n=0; n<taille*4;n++){
            separator = separator + "-";
        }
        String chaine = separator + "\n";
        for(int i=0;i<taille;i++){
            for (int j=0; j<taille;j++){
                if(j%3 == 0){
                    chaine =chaine +" | ";
                }
                chaine = chaine + "[" + grille[i][j].getValeur() +"]";
            }
            chaine = chaine + "|\n";

            if((i+1)%3 == 0){
                chaine= chaine + separator + "\n";
            }

        }
        return chaine ;
    }

    public void addValue(int i, int j, int value){
        grille[j][i].setValeur(value);
    }

    public void removeValue(int i, int j){
        grille[j][i].setValeur(0);
    }

    public void addNote(int i, int j, int note){
        grille[j][i].addNote(note);
    }

    public boolean isCorrect(){
        boolean correct = true;
        int ligne = 0; int col = 0;
        while(ligne < taille && correct){
            while(col < taille && correct){
                correct=verifAlreadyInLine(ligne,col) && verifAlreadyInRow(ligne,col) && verifAlreadyInBlock(ligne,col);
            }
        }
        return correct;
    }

    public boolean valueIsCorrect(int ligne, int col){
        return verifAlreadyInRow(ligne,col) && verifAlreadyInLine(ligne,col) && verifAlreadyInBlock(ligne,col);
    }

    public boolean verifAlreadyInRow(int ligne, int col){
        boolean correct = true;
        for(int k =0; k<taille;k++){
            if(grille[k][col].getValeur() == grille[ligne][col].getValeur() && grille[k][col]!=grille[ligne][col]) correct = false;
        }
        return correct;
    }

    public boolean verifAlreadyInLine(int ligne, int col){
        boolean correct = true;
        for(int k =0; k<taille;k++){
            if(grille[ligne][k].getValeur() == grille[ligne][col].getValeur() && grille[ligne][k]!=grille[ligne][col]) correct = false;
        }
        return correct;
    }

    public boolean verifAlreadyInBlock(int ligne, int col){
        int x,y;
        if(ligne<3) x = 0;
        else if(ligne>5) x = 6;
        else x = 3;

        if(col<3) y = 0;
        else if(col>5) y = 6;
        else y = 3;

        boolean correct = true;
        for(int i = x; i< x+3; i++ ){
            for ( int j = y; j<y+3;j++){
                if(grille[i][j].getValeur() == grille[ligne][col].getValeur() && grille[i][j] != grille[ligne][col]){
                    correct = false;
                }
            }
        }
        return correct;
    }


    @Override
    public Node getStyleableNode() {
        return null;
    }




}
