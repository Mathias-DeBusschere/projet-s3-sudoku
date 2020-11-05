package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Case extends Parent {

    private TextField valeur = new TextField();
    private ArrayList<Integer> note;


    public Case(int nb) {
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
        valeur.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    valeur.setText(newValue.replaceAll("[^\\d]", ""));
                }
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

}
