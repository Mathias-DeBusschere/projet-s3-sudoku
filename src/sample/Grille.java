package sample;


import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.lang.model.element.Element;
import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Grille extends Parent {

    private Case [][] grille;
    private final int taille;
    private Case caseselectionne;
    private Difficulte difficulte;
    private ControllerGrille parent;

    public Grille(int[][] valeurs) {
        this.taille = valeurs.length;
        this.difficulte=Difficulte.FACILE;
        this.grille = new Case[taille][taille];
        this.caseselectionne=null;


        GridPane gridPane = new GridPane();
        //noinspection IntegerDivisionInFloatingPointContext
        gridPane.setPrefSize((600/taille)*taille,(600/taille)*taille);
        gridPane.setAlignment(Pos.CENTER);

        boolean caseinitiale;
       /* for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                caseinitiale= valeurs[i][j] != 0;
                Case case1 = new Case(valeurs[i][j],this,i,j,caseinitiale);
                grille[i][j] = case1;
                gridPane.add(case1,j,i);
            }
        } */
        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                Case case1 = new Case(0,this,i,j,false);
                grille[i][j] = case1;
                gridPane.add(case1,i,j);
            }
        }
        generateur();
        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                caseinitiale= grille[i][j].getValeur() != 0;
                Case case1 = new Case(grille[i][j].getValeur(),this,i,j,caseinitiale);
                grille[i][j] = case1;
                gridPane.add(case1,i,j);
            }
        }
        getChildren().add(gridPane);
        grille[0][0].action();



        //PROBLEME DE PERTE DE FOCUS
        this.setOnKeyPressed(keyEvent -> {
            String caractere = keyEvent.getText();
//            System.out.println(keyEvent.getCode());
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
//            System.out.println(caseselectionne.getColonne()+"|"+caseselectionne.getLigne());
            if(caseselectionne!=null) {
                switch (keyEvent.getCode()) {
                    case BACK_SPACE -> caseselectionne.deleteValeur();
                    case UP, Z -> {if(caseselectionne.getLigne() >= 1)grille[caseselectionne.getLigne()-1][caseselectionne.getColonne()].action();
                    else grille[taille-1][caseselectionne.getColonne()].action();}

                    case DOWN, S -> {if(caseselectionne.getLigne() < taille-1) grille[caseselectionne.getLigne()+1][caseselectionne.getColonne()].action();
                    else grille[0][caseselectionne.getColonne()].action();}

                    case RIGHT, D -> {if(caseselectionne.getColonne() < taille-1)grille[caseselectionne.getLigne()][caseselectionne.getColonne()+1].action();
                    else grille[caseselectionne.getLigne()][0].action();}

                    case LEFT, Q -> {if(caseselectionne.getColonne() >= 1) grille[caseselectionne.getLigne()][caseselectionne.getColonne()-1].action();
                    else grille[caseselectionne.getLigne()][taille-1].action();}

                    case TAB -> {
                        if(caseselectionne.getColonne() < taille-1){
                            grille[caseselectionne.getLigne()][caseselectionne.getColonne()+1].action();
                        }else if(caseselectionne.getLigne() < taille-1)
                            grille[caseselectionne.getLigne()+1][0].action();
                        else
                            grille[0][0].action();
                    }

                }
                keyEvent.consume();
            }

        });

    }

    public void setParent(ControllerGrille parent) {
        this.parent = parent;
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

    public boolean isCorrect() {
        boolean correct = true;
        for (int i=0;i<taille && correct;i++){
            for (int j=0;j<taille && correct;j++)
                correct = verifAlreadyInRow(grille[i][j]) && verifAlreadyInLine(grille[i][j]) && verifAlreadyInBlock(grille[i][j]);
        }

        return correct;
    }

    public void isCorrectAnimation(boolean correct) {
        if(correct){
            Clip sonVictoire;
            try {
                sonVictoire = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/victoire.wav"));
                sonVictoire.open(inputStream);
                sonVictoire.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }


            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("congratulations.png")));
            img.setPreserveRatio(true);
            //noinspection IntegerDivisionInFloatingPointContext
            img.setFitWidth(600-600/taille);
            img.setTranslateY(-100);
//            img.setY(600/9);

            Label labelTimer = new Label();
            labelTimer.setText("39.56 min");
            labelTimer.setStyle("-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
            labelTimer.setTranslateY(50);
//            labelTimer.setTranslateX((600/taille)*((taille)/2.5));
//            labelTimer.setTranslateY((600/2)+ 20);

            Label labelScore = new Label();
            labelScore.setText("9670 points");
            labelScore.setStyle("-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
            labelScore.setTranslateY(150);
//            labelScore.setTranslateX((600/taille)*((taille)/2.5));
//            labelScore.setTranslateY((600/2)+ 120);


            Task<Void> sleep500ms = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            sleep500ms.setOnSucceeded(workerStateEvent -> parent.addElement(labelTimer));

            Task<Void> sleep1000ms = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            sleep1000ms.setOnSucceeded(workerStateEvent -> parent.addElement(labelScore));

            parent.addElement(img);
            new Thread(sleep500ms).start();
            new Thread(sleep1000ms).start();
        }
    }

    //Obligé de faire de la concaténation, car java si il voit un False, il n'exécute pas le reste
    public boolean valueIsCorrect(Case cas){
        boolean correct = verifAlreadyInRow(cas);
        correct = verifAlreadyInLine(cas) && correct;
        correct = verifAlreadyInBlock(cas) && correct;
        if (difficulte == Difficulte.MOYEN || difficulte == Difficulte.FACILE) {
            if(!correct)
                cas.setErrorStyle();
            else
                cas.setDefaultStyle();
        }
        return correct;
    }

    public boolean valueIsCorrectgen(Case cas){
        boolean correct = verifAlreadyInRow(cas);
        correct = verifAlreadyInLine(cas) && correct;
        correct = verifAlreadyInBlock(cas) && correct;
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

    public int ligneSimple(){
        int fin=-1;
        int min=1000;
        for (int l = 0; l < taille ; l++) {
            int temp = 0;
            for (int c = 0; c < taille ; c++) {
                if (grille[l][c].getValeur() == 0)
                    temp++;
            }
            if (temp!=0)
                if (temp<min){
                    min=temp;
                    fin=l;
                }
        }
        return  fin;
    }

    public boolean soluc(int l, int c){
        if (l==8 && c==9){
            return true;
        }
        if (c==9){
            l++;
            c=0;
        }
        if (grille[l][c].getValeur()!=0)
            return soluc(l,c+1);

        for (int i = 1; i < 10; i++) {
            grille[l][c].setValeurGen(i);
            if (valueIsCorrectgen(grille[l][c])){
                if (soluc(l,c+1)){
                    return true;
                }
            } else {
                grille[l][c].setValeurGen(0);
            }
            grille[l][c].setValeurGen(0);
        }
        return false;
    }

    public boolean remplis() {
        boolean b=true;
        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                if (grille[l][c].getValeur() == 0) {
                    return false;
                }
            }

       }
       return b;
    }

    public String toString2(){
        StringBuilder sudoOut = new StringBuilder(" ");
        for(int j = 0; j<9; j++){
            sudoOut.append(" | ");
            for(int z = 0; z<9; z++){
                sudoOut.append(grille[j][z].getValeur()).append(" ");

                if(z != 0 && (z+1)%3 == 0 && z+1 < 9){
                    sudoOut.append("| ");
                }
            }
            sudoOut.append("|\n");

            if(j != 0 && (j+1)%3 == 0 && j+1 < 9){
                sudoOut = new StringBuilder(sudoOut.toString());
            }

        }
        sudoOut = new StringBuilder(sudoOut.toString());
        return sudoOut.toString();
    }
    public void generateur(){
        int NBcaseVide;
        int aleatoireValeur;
        int aleatoireLigne;
        int aleatoireColr;
        Random r= new Random();

        if (difficulte==Difficulte.FACILE){
            NBcaseVide=45;
        } else if (difficulte==Difficulte.MOYEN){
            NBcaseVide=50;
        } else if (difficulte==Difficulte.DIFFICILE){
            NBcaseVide=60;
        } else {
            NBcaseVide=45;
        }

        int i =0;
        int quanOfRanNb = r.nextInt(8) + 2;
        while( i < quanOfRanNb) {
            aleatoireValeur=r.nextInt(9) + 1;
            aleatoireLigne=r.nextInt(9);
            aleatoireColr=r.nextInt(9);
            if (grille[aleatoireLigne][aleatoireColr].getValeur() == 0) {
                grille[aleatoireLigne][aleatoireColr].setValeurGen(aleatoireValeur);
                i++;
                if (!valueIsCorrectgen(grille[aleatoireLigne][aleatoireColr])){
                    grille[aleatoireLigne][aleatoireColr].setValeurGen(0);
                    i--;
                }
            }
        }
        soluc(0,0);

        i=0;
        while (i < NBcaseVide ) {
            aleatoireLigne=r.nextInt(9);
            aleatoireColr=r.nextInt(9);
            if (grille[aleatoireLigne][aleatoireColr].getValeur()!=0){
                grille[aleatoireLigne][aleatoireColr].setValeurGen(0);
                i++;
            }
        }
    }

}
