package fr.umontpellier.iut.M3302.sudoku.generators;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.Difficulty;
import fr.umontpellier.iut.M3302.sudoku.checkers.Checker;
import fr.umontpellier.iut.M3302.sudoku.solvers.Solver;

public abstract class Generator {
    private final int size;
    private final Difficulty difficulty;
    private final Checker checker;
    private final Solver solver;

    public Generator(int size, Difficulty difficulty, Checker checker, Solver solver) {
        this.size = size;
        this.difficulty = difficulty;
        this.checker = checker;
        this.solver = solver;
    }

    public abstract Case[][] generate();

    public int getSize() {
        return size;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Checker getChecker() {
        return checker;
    }

    public Solver getSolver() {
        return solver;
    }
}
