package edu.utdallas.hpews.solver;

import android.os.AsyncTask;

import java.util.List;

import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Solution;

/**
 * Created by sasha on 4/10/16.
 */
public class SolverTask extends AsyncTask<Puzzle, Integer, List<Solution>> {

    private SolverTaskProgressUpdateCallback progressUpdateCallback;
    private SolverTaskFinishedCallback finishedCallback;



    public SolverTask(SolverTaskProgressUpdateCallback progressUpdateCallback, SolverTaskFinishedCallback finishedCallback) {
        this.progressUpdateCallback = progressUpdateCallback;
        this.finishedCallback = finishedCallback;
    }



    @Override
    protected List<Solution> doInBackground(Puzzle... params) {

        Puzzle puzzle = params[0];

        SolverService.getInstance().solvePuzzle(
                puzzle,
                new SolverServiceProgressCallback() {
                    @Override
                    public void progressUpdate(int percent) {
                        publishProgress(percent);
                    }
                }
        );

        return puzzle.getSolutions();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        this.progressUpdateCallback.progressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(List<Solution> solutions) {
        this.finishedCallback.finished(solutions);
    }

}