package fr.umontpellier.iut.M3302.sudoku.checkers;

import fr.umontpellier.iut.M3302.sudoku.Case;

/**
 * Abstract representation of a grid checker.
 */
public abstract class Checker {
    private final int size;

    /**
     * Default constructor.
     * @param size size of grid.
     */
    public Checker(int size) {
        this.size = size;
    }

    /**
     * Check via array of cases & flag cases with errors.
     * @param cases array of cases
     * @return true when all good, false when error.
     */
    public abstract boolean checkGrid(Case[][] cases);

    /**
     * Check one case via array of cases & flag with errors.
     * @param cases array of cases
     * @param i row.
     * @param j column.
     * @return true when all good, false when error.
     */
    public abstract boolean checkCase(Case[][] cases, int i, int j);

    /**
     * Getter for size.
     * @return size
     */
    public int getSize() {
        return size;
    }
}
