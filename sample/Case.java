package sample;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Case extends Parent {

    private int valeur;
    private ArrayList<Integer> note;
    private final Grille grille;
    private final int ligne;
    private final int colonne;
    private final boolean initiale;

    Region fond = new Region();
    Text text = new Text();

    public Case(int valeur,Grille grille1,int ligne, int colonne, boolean initiale) {
        this.valeur = valeur;
        grille=grille1;
        this.note = new ArrayList<>();
        this.ligne =ligne;
        this.colonne =colonne;
        this.initiale=initiale;

        fond.setPrefSize(600/9,600/9);
        fond.setStyle( "-fx-background-color: white; -fx-border-color: grey;");
        this.getChildren().add(fond);

        if (valeur == 0) text.setText("");
        else text.setText(String.valueOf(valeur));

        text.setFill(Color.BLACK);
        text.setX(30);
        text.setY(58);
        if(initiale){
            text.setStyle( "-fx-font-size: 30;-fx-font-weight: bold;");
        }else{
            text.setStyle("-fx-font-size: 30;");
        }

        this.getChildren().add(text);

        fond.setOnMouseClicked(mouseEvent -> action());
        text.setOnMouseClicked(mouseEvent -> action());
        fond.setOnMouseEntered(mouseEvent -> fond.setOpacity(0.9));
        fond.setOnMouseExited(mouseEvent -> fond.setOpacity(1));

    }



    public int getValeur() {
        return valeur;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    //Permet de changer la valeur et de verifier si la valeur rentrée n'est pas fausse
    public void setValeur(int valeur) {
        if(!initiale){
            this.valeur=valeur;
            text.setText(String.valueOf(valeur));
            grille.valueIsCorrect(this);}}

    //Permet d'effacer une valeur d'une case (set 0) et d'effacer les errorStyle de chaque case concernée.
    public void deleteValeur(){
        if(!initiale){
            this.valeur=0;
            text.setText("");
            //setDefaultStyle() pour les cases de la même ligne et de la même colonne
            for(int i =0;i<9;i++){
                grille.getCase(ligne,i).setDefaultStyle();
                grille.getCase(i,colonne).setDefaultStyle();
            }
            //setDefaultStyle() pour les cases du même bloc
            ArrayList<Case> liste = grille.getCaseInBlock(this);
            for (Case caseblock : liste){
                caseblock.setDefaultStyle();
            }
            //setSelectionStyle() pour this puisque elle est encore sélectionnée
            this.setSelectionStyle();}
    }

    /*
    Met l'attribut caseselectionner de grille à this
    Met en orange le contour de la case et l'enlève de celle qui n'est plus séléctionnée.
    */
    public void action(){

        if(grille.getCaseselectionne()!=null){
            grille.getCaseselectionne().setDefaultStyle();}
        grille.setCaseselectionne(this);
        this.setSelectionStyle();

    }

    public void setSelectionStyle(){
        fond.setStyle( "-fx-border-width: 2; -fx-background-color: white; -fx-border-color: orange;  -fx-border-radius: 3;" );
    }

    public void setErrorStyle(){
        fond.setStyle(fond.getStyle()+"-fx-background-color: #fd7575;");
    }

    public void setDefaultStyle(){

        fond.setStyle( "-fx-background-color: white; -fx-border-color: grey;" );
        if(initiale){ text.setStyle("-fx-font-size: 30; -fx-font-weight: bold ");}
        else{
            text.setStyle("-fx-font-size: 30;");
        }

    }

}
