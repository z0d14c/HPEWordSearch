package edu.utdallas.hpews.solver;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.hpews.model.Coordinate;
import edu.utdallas.hpews.model.Direction;
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
        this.solvePuzzle(puzzle, new NoOpSolverServiceProgressCallback());
    }

    public void solvePuzzle(Puzzle puzzle, final SolverServiceProgressCallback progressCallback) {

        List<CandidateVector> candidateVectors = this.getCandidateVectors(puzzle);
        SolutionWordProcessor processor = new SolutionWordProcessor(candidateVectors);

        DictionaryService.getInstance().forEachWord(
                processor,
                new WordProcessorProgressCallback() {
                    @Override
                    public void progressUpdate(int percentComplete) {
                        progressCallback.progressUpdate(percentComplete);
                    }
                }
        );

        puzzle.getSolutions().addAll(processor.getSolutions());
    }



    private List<CandidateVector> getCandidateVectors(Puzzle puzzle) {

        List<CandidateVector> results = new ArrayList<>();
        Direction direction;
        StringBuffer vector;
        Coordinate startingCoordinate, currentCoordinate;

        direction = Direction._0;
        for (int x = 0; x < puzzle.getDimension(); x++) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(x, puzzle.getDimension() - 1);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }

        direction = Direction._45;
        for (int y = 0; y < puzzle.getDimension(); y++) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(0, y);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }
        for (int x = 1; x < puzzle.getDimension(); x++) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(x, puzzle.getDimension() - 1);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }

        direction = Direction._90;
        for (int y = 0; y < puzzle.getDimension(); y++) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(0, y);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }

        direction = Direction._135;
        for (int y = puzzle.getDimension() - 1; y >= 0; y--) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(0, y);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }
        for (int x = 1; x < puzzle.getDimension(); x++) {
            vector = new StringBuffer();
            startingCoordinate = currentCoordinate = new Coordinate(x, 0);
            boolean finished = false;
            while (finished != true) {
                try {
                    vector.append(puzzle.getCharacterAt(currentCoordinate));
                    currentCoordinate = currentCoordinate.getNext(direction);
                } catch (IllegalArgumentException ex) {
                    finished = true;
                }
            }
            results.add(new CandidateVector(startingCoordinate, direction, vector.toString()));
        }

        // reflect the rest
        for (int i = 0, j = results.size(); i < j; i++) {
            results.add(results.get(i).getOpposite());
        }

        return results;
    }

}