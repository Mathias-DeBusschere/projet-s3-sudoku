package fr.umontpellier.iut.M3302.sudoku.solvers;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.checkers.Checker;

public abstract class Solver {
    private Checker checker;

    public Solver(Checker checker) {
        this.checker = checker;
    }

    public abstract Case[][] solve(Case[][] cases);

    public Checker getChecker() {
        return checker;
    }
}
