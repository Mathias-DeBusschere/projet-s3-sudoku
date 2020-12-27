package fr.umontpellier.iut.M3302.sudoku.generators;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.Difficulty;
import fr.umontpellier.iut.M3302.sudoku.checkers.Checker;
import fr.umontpellier.iut.M3302.sudoku.solvers.Solver;

import java.util.Random;

public class RndNonUniGen extends Generator {

    public RndNonUniGen(int size, Difficulty difficulty, Checker checker, Solver solver) {
        super(size, difficulty, checker, solver);
    }

    @Override
    public Case[][] generate() {
        Case[][] cases = new Case[getSize()][getSize()];
        int nbEmptyCase;
        int rndValue;
        int rndRow;
        int rndColumn;
        Random r = new Random();

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Case c = new Case(0, getSize());
                cases[i][j] = c;
            }
        }

        if (getDifficulty() == Difficulty.EASY) {
            nbEmptyCase = getSize() * getSize() * 4 / 10;
        } else if (getDifficulty() == Difficulty.NORMAL) {
            nbEmptyCase = getSize() * getSize() * 5 / 10;
        } else if (getDifficulty() == Difficulty.HARD) {
            nbEmptyCase = getSize() * getSize() * 6 / 10;
        } else {
            nbEmptyCase = getSize() * getSize() * 5 / 10;
        }

        int i = 0;
        int quanOfRanNb = r.nextInt((int) Math.sqrt(getSize())) + 1;
        while (i < quanOfRanNb) {
            rndValue = r.nextInt(getSize()) + 1;
            rndRow = r.nextInt(getSize());
            rndColumn = r.nextInt(getSize());
            if (cases[rndRow][rndColumn].getValue() == 0) {
                cases[rndRow][rndColumn].setValue(rndValue);
                i++;
                if (!getChecker().checkCase(cases, rndRow, rndColumn)) {
                    cases[rndRow][rndColumn].setValue(0);
                    i--;
                }
            }
        }

        cases = getSolver().solve(cases);

        i = 0;
        while (i < nbEmptyCase) {
            rndRow = r.nextInt(getSize());
            rndColumn = r.nextInt(getSize());
            if (cases[rndRow][rndColumn].getValue() != 0) {
                cases[rndRow][rndColumn].setValue(0);
                i++;
            }
        }

        for (int i2 = 0; i2 < getSize(); i2++) {
            for (int j = 0; j < getSize(); j++) {
                if (cases[i2][j].getValue() != 0)
                    cases[i2][j].setInitial(true);
            }

        }
        System.out.println("generated!");
        return cases;
    }

}
