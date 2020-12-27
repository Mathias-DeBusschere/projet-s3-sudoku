package fr.umontpellier.iut.M3302.sudoku.solvers;

import fr.umontpellier.iut.M3302.sudoku.Case;

import java.util.ArrayList;
import java.util.Arrays;

public class SolverHelper {

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

    public static int[] crossSimplestRowColumn(Case[][] cases) throws Throwable {
        if (!SolverHelper.isFilled(cases)) {
            int[] coord = new int[2];
            int l = simplestRow(cases, new int[cases.length]);
            ArrayList<Integer> integers = new ArrayList<>();
            for (int i = 0; i < cases.length; i++) {
                if (cases[l][i].getValue() == 0)
                    integers.add(i);
            }
            int[] ints = new int[integers.size()];
            for (int i = 0; i < integers.size(); i++) {
                ints[i] = integers.get(i);
            }
            coord[0] = l;
            coord[1] = simplestColumn(cases, ints);
            return coord;
        } else {
            throw new Throwable("Already filled");
        }
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

    protected static int simplestRow(Case[][] cases) {
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

    public static int simplestRow(Case[][] cases, int[] rowIndices) {
        int end = -1;
        int min = 1000;

        if (Arrays.equals(rowIndices, new int[rowIndices.length])) {
            rowIndices = new int[rowIndices.length];
            for (int i = 0; i < rowIndices.length; i++) {
                rowIndices[i] = i;
            }

        }

        for (int l : rowIndices) {
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

    public static int simplestColumn(Case[][] cases, int[] colIndices) {
        int fin = -1;
        int min = 1000;
        for (int c : colIndices) {
            int temp = 0;
            for (int l = 0; l < cases.length; l++) {
                if (cases[l][c].getValue() == 0)
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
}
