package sample;


import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sample.solver.BackTrackingSolver;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Grille extends Parent {

    private Case [][] tableau;
    private final int taille;
    private Case caseselectionne;
    private Difficulte difficulte;
    private ControllerGrille parent;
    private boolean noteMode = false;

    public Grille(int length, Difficulte difficulte) {
        this.taille = length;
        this.difficulte=difficulte;
        this.tableau = new Case[taille][taille];
        this.caseselectionne=null;


        GridPane gridPane = new GridPane();
        gridPane.setPrefSize((600.0/taille)*taille,(600.0/taille)*taille);
        gridPane.setAlignment(Pos.CENTER);

        boolean caseinitiale;

        generateNewSudoku();

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                gridPane.add(tableau[j][i],i,j);
            }
        }
        System.out.println(gridPane);
//        gridPane.setStyle("-fx-border-color: red; -fx-border-width: 5");

        getChildren().add(gridPane);
        tableau[0][0].action();


        //PROBLEME DE PERTE DE FOCUS
        this.setOnKeyPressed(keyEvent -> {
            String caractere = keyEvent.getText();
//            System.out.println("code: " + keyEvent.getCode());
//            System.out.println("text: " + keyEvent.getText().equals(" "));
//            System.out.println("char: " + keyEvent.getCharacter());
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
                    case UP, Z -> {if(caseselectionne.getLigne() >= 1) tableau[caseselectionne.getLigne()-1][caseselectionne.getColonne()].action();
                    else tableau[taille-1][caseselectionne.getColonne()].action();}

                    case DOWN, S -> {if(caseselectionne.getLigne() < taille-1) tableau[caseselectionne.getLigne()+1][caseselectionne.getColonne()].action();
                    else tableau[0][caseselectionne.getColonne()].action();}

                    case RIGHT, D -> {if(caseselectionne.getColonne() < taille-1) tableau[caseselectionne.getLigne()][caseselectionne.getColonne()+1].action();
                    else tableau[caseselectionne.getLigne()][0].action();}

                    case LEFT, Q -> {if(caseselectionne.getColonne() >= 1) tableau[caseselectionne.getLigne()][caseselectionne.getColonne()-1].action();
                    else tableau[caseselectionne.getLigne()][taille-1].action();}

                    case TAB -> {
                        if(caseselectionne.getColonne() < taille-1){
                            tableau[caseselectionne.getLigne()][caseselectionne.getColonne()+1].action();
                        }else if(caseselectionne.getLigne() < taille-1)
                            tableau[caseselectionne.getLigne()+1][0].action();
                        else
                            tableau[0][0].action();
                    }

                }
                keyEvent.consume();
            }

        });

    }

    public Case[][] getTableau() {
        return tableau;
    }

    public boolean isNoteMode() {
        return noteMode;
    }

    public void setNoteMode(boolean noteMode) {
        this.noteMode = noteMode;
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
                chaine.append("[").append(tableau[i][j].getValeur()).append("]");
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
        return tableau[ligne][colonne];
    }

    public int getTaille() {
        return taille;
    }

    public boolean isCorrect() {
        boolean correct = true;
        for (int i=0;i<taille && correct;i++){
            for (int j=0;j<taille && correct;j++)
                correct = verifAlreadyInRow(tableau[i][j]) && verifAlreadyInLine(tableau[i][j]) && verifAlreadyInBlock(tableau[i][j]);
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
            if (cas != tableau[k][colonne]){
                if(tableau[k][colonne].getValeur() == tableau[cas.getLigne()][colonne].getValeur() && tableau[k][colonne]!= tableau[cas.getLigne()][colonne]){
                    correct = false;
                    if(difficulte == Difficulte.FACILE) tableau[k][colonne].setErrorStyle();
                }else{
                    tableau[k][colonne].setDefaultStyle();
                }
            }
        }
        return correct;
    }

    public boolean verifAlreadyInLine(Case cas){
        boolean correct = true;
        int ligne = cas.getLigne();
        for(int k =0; k<taille;k++) {
            if (cas != tableau[ligne][k]) {
                if (tableau[ligne][k].getValeur() == tableau[ligne][cas.getColonne()].getValeur() && tableau[ligne][k] != tableau[ligne][cas.getColonne()]) {
                    correct = false;
                    if(difficulte==Difficulte.FACILE) tableau[ligne][k].setErrorStyle();
                } else {
                    tableau[ligne][k].setDefaultStyle();
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
        int sqrtTaille = (int) Math.sqrt(taille);
        int lig = (cas.getLigne()/sqrtTaille)*sqrtTaille;
        int col = (cas.getColonne()/sqrtTaille)*sqrtTaille;

        for(int i = lig; i< lig+sqrtTaille; i++ ){
            liste.addAll(Arrays.asList(tableau[i]).subList(col, col + sqrtTaille));
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
                    if (difficulte==Difficulte.FACILE)
                        caseverif.setErrorStyle();
                } else
                    caseverif.setDefaultStyle();
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
                if (tableau[l][c].getValeur() == 0)
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

    public int coloneSimple() {
        int fin=-1;
        int min=1000;
        for (int c = 0; c < taille ; c++) {
            int temp = 0;
            for (int l = 0; l < taille ; l++) {
                if (tableau[l][c].getValeur() == 0)
                    temp++;
            }
            if (temp!=0)
                if (temp<min){
                    min=temp;
                    fin=c;
                }
        }
        return  fin;
    }

    public int carreSimple() {
        int min = 1000;
        int fin=-1;
        int sqrtTaille = (int)Math.sqrt(taille);
        int[][] sumPerBlock = new int[sqrtTaille][sqrtTaille];

        for (int l = 0; l < taille ; l++) {
            for (int c = 0; c < taille ; c++) {
                if (tableau[l][c].getValeur() == 0)
                    sumPerBlock[l/sqrtTaille][c/sqrtTaille] ++;
            }
        }
        for (int i = 0; i < sqrtTaille; i++) {
            for (int j = 0; j < sqrtTaille; j++) {
                if (min > sumPerBlock[i][j]) {
                    min = sumPerBlock[i][j];
                    fin = i*3 + j;
                }
            }
        }

        return fin;
    }

//    private boolean isFilled() {
//        boolean bool = true;
//        for (int i = 0; i < taille && bool; i++) {
//            for (int j = 0; j < taille && bool; j++) {
//                bool = grille[i][j].getValeur() != 0;
//            }
//        }
//        return bool;
//    }

    public boolean estRemplis() {
        boolean b=true;
        for (int l = 0; l < taille && b; l++) {
            for (int c = 0; c < taille && b; c++) {
                b = tableau[l][c].getValeur() != 0;
            }
       }
       return b;
    }

    public String toString2(){
        StringBuilder sudoOut = new StringBuilder();
        for(int j = 0; j<9; j++){
            sudoOut.append(" | ");
            for(int z = 0; z<9; z++){
                sudoOut.append(tableau[j][z].getValeur()).append(" ");

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

    public void generateNewSudoku(){
        int NBcaseVide;
        int aleatoireValeur;
        int aleatoireLigne;
        int aleatoireColr;
        Random r= new Random();


        for (int i =0;i<taille; i++){
            for(int j=0;j<taille;j++){
                Case case1 = new Case(0,this,i,j,false);
                tableau[i][j] = case1;
            }
        }

        if (difficulte==Difficulte.FACILE){
            NBcaseVide=taille*taille*4/10;
        } else if (difficulte==Difficulte.MOYEN){
            NBcaseVide=taille*taille*5/10;
        } else if (difficulte==Difficulte.DIFFICILE){
            NBcaseVide=taille*taille*6/10;
        } else {
            NBcaseVide=taille*taille*5/10;
        }

        int i =0;
        int quanOfRanNb = r.nextInt(taille) + 2;
        while( i < quanOfRanNb) {
            aleatoireValeur=r.nextInt(taille) + 1;
            aleatoireLigne=r.nextInt(taille);
            aleatoireColr=r.nextInt(taille);
            if (tableau[aleatoireLigne][aleatoireColr].getValeur() == 0) {
                tableau[aleatoireLigne][aleatoireColr].setValeurGen(aleatoireValeur);
                i++;
                if (!valueIsCorrectgen(tableau[aleatoireLigne][aleatoireColr])){
                    tableau[aleatoireLigne][aleatoireColr].setValeurGen(0);
                    i--;
                }
            }
        }

        BackTrackingSolver backTrackingSolver = new BackTrackingSolver(this,tableau);
        backTrackingSolver.solve();


        i=0;
        while (i < NBcaseVide ) {
            aleatoireLigne=r.nextInt(taille);
            aleatoireColr=r.nextInt(taille);
            if (tableau[aleatoireLigne][aleatoireColr].getValeur()!=0){
                tableau[aleatoireLigne][aleatoireColr].setValeurGen(0);
                i++;
            }
        }

        for (int i2 = 0; i2 < taille; i2++) {
            for (int j = 0; j < taille; j++) {
                if (tableau[i2][j].getValeur()!=0)
                    tableau[i2][j].setInitiale(true);
            }

        }
    }

}
