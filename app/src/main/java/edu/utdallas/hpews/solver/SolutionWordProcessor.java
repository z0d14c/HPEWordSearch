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

        vectorLoop:
        for (CandidateVector candidateVector : this.candidateVectors) {
            Solution solution = candidateVector.findSolution(word);
            if (solution != null) {

                // look for any existing conflicts
                List<Solution> conflicts = new ArrayList<>();
                for (Solution existing : solutions) {
                    if (existing.conflictsWith(solution)) {
                        if (existing.getLength() < solution.getLength()) {
                            conflicts.add(existing);
                        } else {
                            // if we come across any conflicting existing solution that's longer, move on to the next vector
                            continue vectorLoop;
                        }
                    }
                }

                // remove any found (smaller) conflicts
                for (Solution conflict : conflicts) {
                    solutions.remove(conflict);
                }

                // add the newly found solution
                solutions.add(solution);

                // we're only looking for the first occurrence; if there are multiple occurrences,
                // only the first one will be identified
                break;
            }
        }

    }
}