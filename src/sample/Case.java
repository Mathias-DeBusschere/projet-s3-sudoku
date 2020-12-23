package sample;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Case extends Parent {

    private int valeur;
    private ArrayList<Integer> note;
    private final Grille grille;
    private final int ligne;
    private final int colonne;
    private boolean initiale;
    private boolean indice = false;

//    Region fond = new Region();
    StackPane fond = new StackPane();
    Text text = new Text();
    GridPane notes = new GridPane();

    public Case(int valeur,Grille grille,int ligne, int colonne, boolean initiale) {
        this.valeur = valeur;
        this.grille = grille;
        this.note = new ArrayList<>();
        this.ligne = ligne;
        this.colonne = colonne;
        this.initiale = initiale;

        //noinspection IntegerDivisionInFloatingPointContext
        double size = 600/grille.getTaille();
        fond.setPrefSize(size,size);
        fond.setStyle( "-fx-background-color: white; -fx-border-color: grey;");
        this.getChildren().add(fond);

        if (valeur == 0)
            text.setText("");
        else
            text.setText(String.valueOf(valeur));

        text.setFill(Color.BLACK);
        text.setY(60);
        if(initiale)
            text.setStyle( "-fx-font-size: 30;-fx-font-weight: bold;");
        else
            text.setStyle("-fx-font-size: 30;");


        fond.getChildren().add(text);

        fond.setOnMouseClicked(mouseEvent -> action());
        text.setOnMouseClicked(mouseEvent -> action());
        fond.setOnMouseEntered(mouseEvent -> fond.setOpacity(0.9));
        fond.setOnMouseExited(mouseEvent -> fond.setOpacity(1));

    }

    public boolean isInitiale() {
        return initiale;
    }

    public void setInitiale(boolean initiale) {
        this.initiale = initiale;
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
        if(!initiale && !indice && !grille.isNoteMode()){
            this.valeur=valeur;
            fond.getChildren().remove(0);
            text.setText(String.valueOf(valeur));
            fond.getChildren().add(text);
            grille.valueIsCorrect(this);

            //Génére un int aléatoire entre 1 et 4 inclus, puis joue un des 4 sons d'écriture
            Clip sonEcriture;
            try {

                Random random = new Random();
                int randomInt = random.nextInt(5 - 1) + 1;

                sonEcriture = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/"+randomInt+".wav"));
                sonEcriture.open(inputStream);
                sonEcriture.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        } else if (!initiale && !indice && grille.isNoteMode()) {
            note.add(valeur);

            Text textNote = new Text();
            textNote.setText(String.valueOf(valeur));
            textNote.setFill(Color.BLACK);
            textNote.setFont(new Font(10));

            int sqrtTaille = (int) Math.sqrt(grille.getTaille());
            int size = 600/grille.getTaille()-5;
            notes.setPrefSize(size, size);

            fond.getChildren().remove(0);
            fond.getChildren().add(notes);

            switch (valeur) {
                case 1 -> notes.add(textNote,0,0);
                case 2 -> notes.add(textNote,1,0);
                case 3 -> notes.add(textNote,2,0);
                case 4 -> notes.add(textNote,0,1);
                case 5 -> notes.add(textNote,1,1);
                case 6 -> notes.add(textNote,2,1);
                case 7 -> notes.add(textNote,0,2);
                case 8 -> notes.add(textNote,1,2);
                case 9 -> notes.add(textNote,2,2);
            }

            System.out.println(notes.getChildren());

//            displayNote();

            //Génére un int aléatoire entre 1 et 4 inclus, puis joue un des 4 sons d'écriture
            Clip sonEcriture;
            try {

                Random random = new Random();
                int randomInt = random.nextInt(5 - 1) + 1;

                sonEcriture = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/"+randomInt+".wav"));
                sonEcriture.open(inputStream);
                sonEcriture.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setValeurGen(int valeur) {
        this.valeur=valeur;
        if (valeur != 0)
            text.setText(String.valueOf(valeur));
        else
            text.setText("");
    }

    //Permet d'effacer une valeur d'une case (set 0) et d'effacer les errorStyle de chaque case concernée.
    public void deleteValeur(){
        if(!initiale && !indice){
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
            this.setSelectionStyle();
            Clip sonEfface;
            try {
                sonEfface = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/efface.wav"));
                sonEfface.open(inputStream);
                sonEfface.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
//        ImageView img = new ImageView(new Image(getClass().getResource("congratulations.png").toExternalForm()));
//        this.getChildren().add(img);
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

    public void setIndiceStyle(){
        if (!initiale) {
            text.setFill(Color.GREEN);
            indice = true;
        }
    }

    @Override
    public String toString() {
        return "Case{" +
                "valeur=" + valeur +
                ", text=" + text +
                '}';
    }
}
