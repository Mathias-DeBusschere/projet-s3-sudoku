package sample;


import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

public class Grille extends Parent {

    private Case [][] grille;
    private final int [][] solution;
    private final int taille;
    private Case caseselectionne;
    private Difficulte difficulte;

    public Grille(int[][] valeurs, int[][] solution) {
        this.taille = valeurs.length;
        this.caseselectionne=null;
        this.solution=solution;
        this.difficulte=Difficulte.FACILE;
        this.grille = new Case[taille][taille];

        GridPane gridPane = new GridPane();
        //noinspection IntegerDivisionInFloatingPointContext
        gridPane.setPrefSize((600/taille)*taille,(600/taille)*taille);
        gridPane.setAlignment(Pos.CENTER);

        boolean caseinitiale;
        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                caseinitiale= valeurs[i][j] != 0;
                Case case1 = new Case(valeurs[i][j],this,i,j,caseinitiale);
                grille[i][j] = case1;
                gridPane.add(case1,i,j);
            }
        }
        getChildren().add(gridPane);


        //PROBLEME DE PERTE DE FOCUS
        this.setOnKeyPressed(keyEvent -> {
            String caractere = keyEvent.getText();
            System.out.println(keyEvent.getCode());
            switch (caractere) {
                case "1" -> caseselectionne.setValeur(1);
                case "2" -> caseselectionne.setValeur(2);
                case "3" -> caseselectionne.setValeur(3);
                case "4" -> caseselectionne.setValeur(4);
                case "5" -> caseselectionne.setValeur(5);
                case "6" -> caseselectionne.setValeur(6);
                case "7" -> caseselectionne.setValeur(7);
                case "8" -> caseselectionne.setValeur(8);
                case "9" -> caseselectionne.setValeur(9);
            }
            if(caseselectionne!=null) switch (keyEvent.getCode()) {
                case BACK_SPACE -> caseselectionne.deleteValeur();
                case UP -> grille[caseselectionne.getLigne()][caseselectionne.getColonne() - 1].action();
                case DOWN -> grille[caseselectionne.getLigne()][caseselectionne.getColonne() + 1].action();
                case RIGHT -> grille[caseselectionne.getLigne() + 1][caseselectionne.getColonne()].action();
                case LEFT -> grille[caseselectionne.getLigne() - 1][caseselectionne.getColonne()].action();
            }

        });
    }

    @Override
    public String toString() {
        StringBuilder separator = new StringBuilder(" ");
        separator.append("-".repeat(Math.max(0, taille * 4)));
        StringBuilder chaine = new StringBuilder(separator + "\n");
        for(int i=0;i<taille;i++){
            for (int j=0; j<taille;j++){
                if(j%3 == 0){
                    chaine.append(" | ");
                }
                chaine.append("[").append(grille[i][j].getValeur()).append("]");
            }
            chaine.append("|\n");

            if((i+1)%3 == 0){
                chaine.append(separator).append("\n");
            }

        }
        return chaine.toString();
    }
    public String toStringSolution() {
        StringBuilder separator = new StringBuilder(" ");
        separator.append("-".repeat(Math.max(0, taille * 4)));
        StringBuilder chaine = new StringBuilder(separator + "\n");
        for(int i=0;i<taille;i++){
            for (int j=0; j<taille;j++){
                if(j%3 == 0){
                    chaine.append(" | ");
                }
                chaine.append("[").append(solution[i][j]).append("]");
            }
            chaine.append("|\n");

            if((i+1)%3 == 0){
                chaine.append(separator).append("\n");
            }

        }
        return chaine.toString();
    }

    public void setDifficulte(Difficulte difficulte){
        this.difficulte=difficulte;
    }

    public void setCaseselectionne(Case caseselectionne) {
        this.caseselectionne = caseselectionne;
    }

    public Case getCaseselectionne() {
        return caseselectionne;
    }

    public Case getCase(int ligne, int colonne){
        return grille[ligne][colonne];
    }

    public int getTaille() {
        return taille;
    }

    public boolean isCorrect(){
        boolean correct = true;
        for (int i=0;i<taille;i++){
            for (int j=0;j<taille;j++)
                if (grille[i][j].getValeur() != solution[i][j]) {
                    correct = false;
                    break;
                }
        }
        return correct;
    }

    //Obligé de faire de la concaténation, car java si il voit un False, il n'exécute pas le reste
    public boolean valueIsCorrect(Case cas){
        boolean correct = verifAlreadyInRow(cas);
        correct = verifAlreadyInLine(cas) && correct;
        correct = verifAlreadyInBlock(cas) && correct;
        if (difficulte == Difficulte.MOYEN || difficulte == Difficulte.FACILE) {
            if(!correct) {cas.setErrorStyle();}else{
                cas.setDefaultStyle();
            }
        }
        return correct;
    }

    public boolean verifAlreadyInRow(Case cas){
        boolean correct = true;
        int colonne = cas.getColonne();
        for(int k =0; k<taille;k++){
            if (cas != grille[k][colonne]){
                if(grille[k][colonne].getValeur() == grille[cas.getLigne()][colonne].getValeur() && grille[k][colonne]!=grille[cas.getLigne()][colonne]){
                    correct = false;
                    if(difficulte == Difficulte.FACILE) grille[k][colonne].setErrorStyle();
                }else{
                    grille[k][colonne].setDefaultStyle();
                }
            }
        }
        return correct;
    }

    public boolean verifAlreadyInLine(Case cas){
        boolean correct = true;
        int ligne = cas.getLigne();
        for(int k =0; k<taille;k++) {
            if (cas != grille[ligne][k]) {
                if (grille[ligne][k].getValeur() == grille[ligne][cas.getColonne()].getValeur() && grille[ligne][k] != grille[ligne][cas.getColonne()]) {
                    correct = false;
                    if(difficulte==Difficulte.FACILE) grille[ligne][k].setErrorStyle();
                } else {
                    grille[ligne][k].setDefaultStyle();
                }
            }
        }
        return correct;
    }

    //Cette fonction renvoie une liste de case qui sont l'ensemble des cases du block de la case donnée en paramètre.
    //La case elle même est également dans la liste renvoyée
    //Pré-requis : taille grille = 9
    public ArrayList<Case> getCaseInBlock(Case cas){
        ArrayList<Case> liste = new ArrayList<>();
        int lig,col;
        if(cas.getLigne()<3) lig = 0;
        else if(cas.getLigne()>5) lig = 6;
        else lig = 3;

        if(cas.getColonne()<3) col = 0;
        else if(cas.getColonne()>5) col = 6;
        else col = 3;

        for(int i = lig; i< lig+3; i++ ){
            liste.addAll(Arrays.asList(grille[i]).subList(col, col + 3));
        }
        return liste;
    }

    //Appel la fonction getCaseinBlock() puis pour chaque case, verifie la valeur.
    //Si même valeur = setErrorStyle(); sinon setDefaultStyle
    public boolean verifAlreadyInBlock(Case cas){
        ArrayList<Case> liste = getCaseInBlock(cas);
        boolean correct = true;
        for (Case caseverif : liste)
            if (cas != caseverif) {
                if (caseverif.getValeur() == cas.getValeur()) {
                    correct = false;
                    if (difficulte==Difficulte.FACILE)caseverif.setErrorStyle();
                } else caseverif.setDefaultStyle();
            }
        return correct;
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }

    public int[][] getSolution() {
        return solution;
    }

    public int ligneSimple(){
        int fin=0;
        int temp=0;
        int min=1000;
        for (int l = 0; l <9 ; l++) {
            for (int c = 0; c < 9 ; c++) {
                if (grille[l][c].getValeur() == 0) {
                    temp++;
                }
            }
            if (temp!=0){
                if (temp<min){
                    min=temp;
                    fin=l;
                }
            }
            temp=0;
        }
        return  fin;
    }

    public boolean soluc(int l, int c){
//        if (isSolved()){return true;}
        if (l==8 && c==9){
            return true;
        }
        if (c==9){
//            l++;
            l=ligneSimple();
            c=0;
        }
        if (grille[l][c].getValeur()!=0){
            return soluc(l,c+1);
        }
        for (int i = 1; i < 10; i++) {
            grille[l][c].setValeur(i);
            if (valueIsCorrect(grille[l][c])){
                if (soluc(l,c+1)){
                    return true;
                }
            } else {
                grille[l][c].setValeur(0);
            }
            grille[l][c].setValeur(0);
        }
        return false;
    }

//    public boolean remplis() {
//        boolean b=true;
//        for (int l = 0; l < 9; l++) {
//            for (int c = 0; c < 9; c++) {
//                if (sudoku.getPosition(l, c) == 0) {
//                    return false;
//                }
//            }
//
//        }
//        return b;
//    }
}
