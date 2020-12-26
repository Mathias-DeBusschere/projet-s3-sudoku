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
        int NBcaseVide;
        int aleatoireValeur;
        int aleatoireLigne;
        int aleatoireColr;
        Random r = new Random();

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Case case1 = new Case(0, getSize());
                cases[i][j] = case1;
            }
        }

        if (getDifficulty() == Difficulty.EASY) {
            NBcaseVide = getSize() * getSize() * 4 / 10;
        } else if (getDifficulty() == Difficulty.NORMAL) {
            NBcaseVide = getSize() * getSize() * 5 / 10;
        } else if (getDifficulty() == Difficulty.HARD) {
            NBcaseVide = getSize() * getSize() * 6 / 10;
        } else {
            NBcaseVide = getSize() * getSize() * 5 / 10;
        }

        int i = 0;
        int quanOfRanNb = r.nextInt(getSize()) + 2;
        while (i < quanOfRanNb) {
            aleatoireValeur = r.nextInt(getSize()) + 1;
            aleatoireLigne = r.nextInt(getSize());
            aleatoireColr = r.nextInt(getSize());
            if (cases[aleatoireLigne][aleatoireColr].getValue() == 0) {
                cases[aleatoireLigne][aleatoireColr].setValue(aleatoireValeur);
                i++;
                if (!getChecker().checkCase(cases, aleatoireLigne, aleatoireColr)) {
                    cases[aleatoireLigne][aleatoireColr].setValue(0);
                    i--;
                }
            }
        }

        cases = getSolver().solve(cases);

        i = 0;
        while (i < NBcaseVide) {
            aleatoireLigne = r.nextInt(getSize());
            aleatoireColr = r.nextInt(getSize());
            if (cases[aleatoireLigne][aleatoireColr].getValue() != 0) {
                cases[aleatoireLigne][aleatoireColr].setValue(0);
                i++;
            }
        }

        for (int i2 = 0; i2 < getSize(); i2++) {
            for (int j = 0; j < getSize(); j++) {
                if (cases[i2][j].getValue() != 0)
                    cases[i2][j].setInitial(true);
            }

        }
        return cases;
    }

}
