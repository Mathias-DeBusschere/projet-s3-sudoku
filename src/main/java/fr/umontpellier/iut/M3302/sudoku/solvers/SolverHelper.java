package fr.umontpellier.iut.M3302.sudoku.solvers;

import fr.umontpellier.iut.M3302.sudoku.Case;

public class SolverHelper {

    public static int simplestRow(Case[][] cases) {
        int end = -1;
        int min = 1000;
        for (int l = 0; l < cases.length; l++) {
            int temp = 0;
            for (int c = 0; c < cases.length; c++) {
                if (cases[l][c].getValue() == 0)
                    temp++;
            }
            if (temp != 0)
                if (temp < min) {
                    min = temp;
                    end = l;
                }
        }
        return end;
    }

    public static int simplestColumn(Case[][] cases) {
        int fin = -1;
        int min = 1000;
        for (int c = 0; c < cases.length; c++) {
            int temp = 0;
            for (Case[] aCase : cases) {
                if (aCase[c].getValue() == 0)
                    temp++;
            }
            if (temp != 0)
                if (temp < min) {
                    min = temp;
                    fin = c;
                }
        }
        return fin;
    }

    public static int simplestBlock(Case[][] cases) {
        int min = 1000;
        int fin = -1;
        int sqrtTaille = (int) Math.sqrt(cases.length);
        int[][] sumPerBlock = new int[sqrtTaille][sqrtTaille];

        for (int l = 0; l < cases.length; l++) {
            for (int c = 0; c < cases.length; c++) {
                if (cases[l][c].getValue() == 0)
                    sumPerBlock[l / sqrtTaille][c / sqrtTaille]++;
            }
        }
        for (int i = 0; i < sqrtTaille; i++) {
            for (int j = 0; j < sqrtTaille; j++) {
                if (min > sumPerBlock[i][j]) {
                    min = sumPerBlock[i][j];
                    fin = i * 3 + j;
                }
            }
        }

        return fin;
    }

    public static boolean isFilled(Case[][] cases) {
        boolean bool = true;
        for (int i = 0; i < cases.length && bool; i++) {
            for (int j = 0; j < cases.length && bool; j++) {
                bool = cases[i][j].getValue() != 0;
            }
        }
        return bool;
    }
}
