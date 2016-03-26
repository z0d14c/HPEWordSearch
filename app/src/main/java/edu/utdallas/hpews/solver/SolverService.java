package edu.utdallas.hpews.solver;

import edu.utdallas.hpews.model.Puzzle;

/**
 * Created by sasha on 3/26/16.
 */
public class SolverService {

    private static SolverService ourInstance = new SolverService();
    public static SolverService getInstance() {
        return ourInstance;
    }



    private SolverService() {
    }



    public void solvePuzzle(Puzzle puzzle) {

        // TODO: implement
        throw new UnsupportedOperationException();

    }
}