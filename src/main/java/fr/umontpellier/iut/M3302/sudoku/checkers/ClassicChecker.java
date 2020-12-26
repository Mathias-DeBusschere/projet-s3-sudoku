package fr.umontpellier.iut.M3302.sudoku.checkers;

import fr.umontpellier.iut.M3302.sudoku.Case;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassicChecker extends Checker {
    /**
     * Default constructor.
     *
     * @param size size of grid.
     */
    public ClassicChecker(int size) {
        super(size);
    }

    @Override
    public boolean checkGrid(Case[][] cases) {
        boolean correct = true;
        for (int i = 0; i < getSize() && correct; i++) {
            for (int j = 0; j < getSize() && correct; j++)
                correct = verifAlreadyInRow(cases, i, j) && verifAlreadyInColumn(cases, i, j) &&
                        verifAlreadyInBlock(cases, i, j);
        }
        return correct;
    }

    @Override
    public boolean checkCase(Case[][] cases, int i, int j) {
        boolean correctRow = verifAlreadyInRow(cases, i, j);
        boolean correctColumn = verifAlreadyInColumn(cases, i, j);
        boolean correctBlock = verifAlreadyInBlock(cases, i, j);
        //TODO: set error style
//        if (difficulties == Difficulty.NORMAL || difficulties == Difficulty.EASY) {
//            if(!correct)
//                cas.setErrorStyle();
//            else
//                cas.setDefaultStyle();
//        }
        boolean correct = correctBlock && correctColumn && correctRow;
        cases[i][j].setError(!correct);
        return correct;
    }

    /**
     * TODO:
     * @param cases
     * @param i
     * @param j
     * @return
     */
    private boolean verifAlreadyInColumn(Case[][] cases, int i, int j) {
        boolean correct = true;
        for (int k = 0; k < getSize(); k++) {
            if (k != i) {
                if (cases[k][j].getValue() == cases[i][j].getValue()) {
                    cases[k][j].setError(true);
                    correct = false;
                }
            }
        }
        return correct;
    }

    /**
     * TODO:
     * @param cases
     * @param i
     * @param j
     * @return
     */
    private boolean verifAlreadyInRow(Case[][] cases, int i, int j) {
        boolean correct = true;
        for (int k = 0; k < getSize(); k++) {
            if (k != j) {
                if (cases[i][k].getValue() == cases[i][j].getValue()) {
                    cases[i][k].setError(true);
                    correct = false;
                }
            }
        }
        return correct;
    }

    /**
     * TODO:
     * @param cases
     * @param i
     * @param j
     * @return
     */
    private boolean verifAlreadyInBlock(Case[][] cases, int i, int j) {
        ArrayList<Case> list = getCaseInBlock(cases, i, j);
        boolean correct = true;
        for (Case c : list)
            if (c != cases[i][j]) {
                if (c.getValue() == cases[i][j].getValue()) {
                    c.setError(true);
                    correct = false;
                }
            }
        return correct;
    }

    /**
     * TODO:
     * @param cases
     * @param i
     * @param j
     * @return
     */
    private ArrayList<Case> getCaseInBlock(Case[][] cases, int i, int j) {
        ArrayList<Case> liste = new ArrayList<>();
        int sqrtTaille = (int) Math.sqrt(getSize());
        int lig = (i / sqrtTaille) * sqrtTaille;
        int col = (j / sqrtTaille) * sqrtTaille;

        for (int k = lig; k < lig + sqrtTaille; k++) {
            liste.addAll(Arrays.asList(cases[k]).subList(col, col + sqrtTaille));
        }
        return liste;
    }
}