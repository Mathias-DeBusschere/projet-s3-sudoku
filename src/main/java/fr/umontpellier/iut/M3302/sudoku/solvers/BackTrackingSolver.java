package fr.umontpellier.iut.M3302.sudoku.solvers;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.checkers.Checker;

public class BackTrackingSolver extends Solver {
    public BackTrackingSolver(Checker checker) {
        super(checker);
    }

    @Override
    public Case[][] solve(Case[][] cases) {
        Case[][] solution = new Case[cases.length][cases.length];
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[i].length; j++) {
                solution[i][j] = new Case(cases[i][j]);
            }
        }
        solve(solution, SolverHelper.simplestRow(cases), 0);
        for (int i = 0; i < getChecker().getSize(); i++) {
            for (int j = 0; j < getChecker().getSize(); j++) {
                solution[i][j].setError(false);
            }
        }
        return solution;
    }

    @Override
    public int addHint(Case[][] cases, int i, int j) {
        return solve(cases)[i][j].getValue();
    }

    private boolean solve(Case[][] cases, int l, int c) {
        if (SolverHelper.isFilled(cases)) {
            return true;
        }
        if (c == cases.length) {
            return solve(cases, SolverHelper.simplestRow(cases), 0);
        }

        for (int i = 1; i <= cases.length; i++) {
            cases[l][c].setValue(i);
            if (getChecker().checkCase(cases, l, c)) {
                if (solve(cases, l, c + 1)) {
                    return true;
                }
            } else {
                cases[l][c].setValue(0);
            }
            cases[l][c].setValue(0);
        }
        return false;
    }
}
