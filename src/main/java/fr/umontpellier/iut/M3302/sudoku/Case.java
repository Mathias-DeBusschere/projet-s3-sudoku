package fr.umontpellier.iut.M3302.sudoku;

import java.util.Arrays;

public class Case {

    private final boolean[] notes;
    private int value;
    private boolean initial = false;
    private boolean hint = false;
    private boolean error = false;

    public Case(int value, int nbOfPossibilities) {
        this.value = value;
        notes = new boolean[nbOfPossibilities];
    }

    public Case(Case c) {
        this.value = c.value;
        this.notes = Arrays.copyOf(c.notes, c.notes.length);
        this.initial = c.initial;
        this.hint = c.hint;
        this.error = c.error;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!initial && !hint)
            this.value = value;
    }

    public void toggleValue(int i) {
        if (!initial)
            if (value == i) {
                value = 0;
            } else {
                value = i;
            }
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }

    public boolean[] getNotes() {
        return notes;
    }

    public void addNote(int value) {
        notes[value - 1] = true;
    }

    public void removeNote(int value) {
        notes[value - 1] = false;
    }

    public void toggleNote(int i) {
        notes[i - 1] = !notes[i - 1];
    }

    public void clearNotes() {
        Arrays.fill(notes, false);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
