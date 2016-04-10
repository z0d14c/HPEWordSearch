package edu.utdallas.hpews.solver;

/**
 * Created by sasha on 4/10/16.
 */
public class NoOpSolverServiceProgressCallback implements SolverServiceProgressCallback {
    @Override
    public void progressUpdate(int percent) {
        // noop
    }
}