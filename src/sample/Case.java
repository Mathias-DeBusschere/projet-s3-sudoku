package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Case extends Parent {

    private TextField valeur = new TextField();
    private ArrayList<Integer> note;
    private Grille grille;


    public Case(int nb,Grille grille) {
        this.grille=grille;
        paramTextField();
        valeur.setText(String.valueOf(nb));
        this.note = new ArrayList<>();
        getChildren().add(valeur);

    }

    public Case() {
        paramTextField();
        valeur.setText(String.valueOf(0));
        this.note = new ArrayList<>();
        getChildren().add(valeur);
    }

    private void paramTextField() {
//        force l'utilisation d'un nombre
//        https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
//        regex tester : https://regex101.com/tests
        valeur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(?<!\\S)\\d(?![^\\s.,?!])")) {
                valeur.setText(newValue.replaceAll("^[(?<!\\S)\\d(?![^\\s.,?!])]", ""));
            }else{
                System.out.println("Valeur mise a : "+ this.getValeur());
            }
        });
        valeur.setPrefSize(600/9,600/9);
    }

    public void setValeur(int nb) {
        valeur.setText(String.valueOf(nb));

    }

    public int getValeur() {
        return parseInt(valeur.getText()) ;
    }

    public void addNote(int note) {
        this.note.add(note);
    }

    public void addBorder (String cote) {
        if (cote.matches("left")) {
            valeur.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        }
        if (cote.matches("down")) {
            valeur.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        }
        if (cote.matches("leftdown")) {
            valeur.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        }
    }

}
