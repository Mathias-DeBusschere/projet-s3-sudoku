package fr.umontpellier.iut.M3302.sudoku;

import fr.umontpellier.iut.M3302.sudoku.checkers.Checker;
import fr.umontpellier.iut.M3302.sudoku.generators.Generator;
import fr.umontpellier.iut.M3302.sudoku.solvers.Solver;
import fr.umontpellier.iut.M3302.sudoku.solvers.SolverHelper;

public class Game {
    private final int size;
    private final Generator generator;
    private final Checker checker;
    private final Solver solver;
    private Case[][] cases;

    public Game(int size, Generator generator, Checker checker, Solver solver) {
        this.size = size;
        this.generator = generator;
        this.checker = checker;
        this.solver = solver;
        cases = this.generator.generate();
    }

    public Case getCase(int i, int j) {
        return cases[i][j];
    }


    public void setValue(int value, int i, int j) {
        checker.cleanError(cases,i,j);
        if (value > 0 && value <= size) {
            cases[i][j].setValue(value);
            checker.checkCase(cases, i, j);
        } else {
            cases[i][j].setValue(0);
            cases[i][j].setError(false);
        }
        //TODO: throw error ?
    }

    public boolean hasValue(int i, int j) {
        return cases[i][j].getValue() != 0;
    }

    public void restart() {
        cases = this.generator.generate();
    }

    public void solve() {
        cases = solver.solve(cases);
    }

    public void addIndice() {
        //TODO: indice, in solver ?
    }

    public int getSize() {
        return size;
    }

    public boolean validate() {
        return SolverHelper.isFilled(cases) && checker.checkGrid(cases);
    }
}
