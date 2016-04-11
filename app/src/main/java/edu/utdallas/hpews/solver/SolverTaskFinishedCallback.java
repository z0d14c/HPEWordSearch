package edu.utdallas.hpews.solver;

import java.util.List;

import edu.utdallas.hpews.model.Solution;

/**
 * Created by sasha on 4/10/16.
 */
public interface SolverTaskFinishedCallback {
    void finished(List<Solution> solutions);
}
