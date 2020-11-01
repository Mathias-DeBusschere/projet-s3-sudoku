package sample;

import java.util.ArrayList;

public class Case {

    private int valeur;
    private ArrayList<Integer> note;

    public Case(int valeur) {
        this.valeur = valeur;
        this.note = new ArrayList();
    }

    public Case() {
        this.valeur = 0;
        this.note = new ArrayList();
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public void addNote(int note) {
        this.note.add(note);
    }

}
