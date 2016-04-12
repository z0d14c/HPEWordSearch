package edu.utdallas.hpews.solver;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.hpews.model.Solution;

/**
 * Created by sasha on 4/10/16.
 */
public class SolutionWordProcessor implements WordProcessor {

    private List<CandidateVector> candidateVectors;
    private List<Solution> solutions;

    public SolutionWordProcessor(List<CandidateVector> candidateVectors) {
        this.candidateVectors = candidateVectors;
        this.solutions = new ArrayList<>();
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    @Override
    public void processWord(String word) {

        for (CandidateVector candidateVector : this.candidateVectors) {
            Solution solution = candidateVector.findSolution(word);
            if (solution != null) {

                // only add this solution if its the first at this location/direction
                // (shortest, by definition, because we are processing dictionary alphabetically)
                if (this.solutions.contains(solution) != true) {
                    this.solutions.add(solution);
                }

                // we're only looking for the first occurrence; if there are multiple occurrences,
                // only the first one will be identified
                break;
            }
        }

    }
}