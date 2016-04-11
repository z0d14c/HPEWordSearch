package edu.utdallas.hpews.solver;

import edu.utdallas.hpews.model.Coordinate;
import edu.utdallas.hpews.model.Direction;
import edu.utdallas.hpews.model.Solution;

/**
 * Created by sasha on 4/9/16.
 */
class CandidateVector {

    private Coordinate startingCoordinate;
    private Direction direction;
    private String vector;

    CandidateVector(Coordinate startingCoordinate, Direction direction, String vector) {
        this.startingCoordinate = startingCoordinate;
        this.direction = direction;
        this.vector = vector;
    }

    Coordinate getStartingCoordinate() {
        return startingCoordinate;
    }

    Direction getDirection() {
        return direction;
    }

    String getVector() {
        return vector;
    }



    public CandidateVector getOpposite() {
        return new CandidateVector(
                Coordinate.getCoordinate(this.startingCoordinate, this.direction, this.vector.length()),
                this.direction.getOpposite(),
                new StringBuilder(this.vector).reverse().toString()

        );
    }



    public Solution findSolution(String word) {
        Solution solution = null;

        int index = this.vector.indexOf(word);
        if (index != -1) {
            solution = new Solution(
                    word,
                    this.direction,
                    Coordinate.getCoordinate(this.startingCoordinate, this.direction, index)
            );
        }

        return solution;
    }

}