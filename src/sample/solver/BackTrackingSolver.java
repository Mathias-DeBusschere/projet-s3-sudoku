package sample.solver;

import sample.Case;
import sample.Grille;

public class BackTrackingSolver {
    private final Grille grille;
    private final Case[][] tab;
    private boolean solved;

    public BackTrackingSolver(Grille grille, Case[][] tab) {
        this.grille = grille;
        this.tab = tab;
    }

    public boolean isSolved() {
        return solved;
    }

    public boolean solve() {
        boolean b = solve(0,0);
        solved = b;
        return b;
    }

    private boolean solve(int l, int c){
        if (grille.estRemplis()){
            return true;
        }
        if (c==grille.getTaille()){
            return solve(grille.ligneSimple(),0);
        }

        for (int i = 1; i <= grille.getTaille(); i++) {
            tab[l][c].setValeurGen(i);
            if (grille.valueIsCorrectgen(tab[l][c])){
                if (solve(l,c+1)){
                    return true;
                }
            } else {
                tab[l][c].setValeurGen(0);
            }
            tab[l][c].setValeurGen(0);
        }
        return false;
    }

}
