package edu.utdallas.hpews.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sasha on 3/26/16.
 */
public class Solution {

    private final String word;
    private final Direction direction;
    private final Coordinate startingCoordinate;



    public Solution(String word, Direction direction, Coordinate startingCoordinate) {
        this.word = word;
        this.direction = direction;
        this.startingCoordinate = startingCoordinate;
    }



    public String getWord() {
        return this.word;
    }

    public int getLength() {
        return this.word.length();
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Coordinate getStartingCoordinate() {
        return this.startingCoordinate;
    }



    public List<Coordinate> getCoordinates() {

        List<Coordinate> coordinates = new ArrayList<>();

        Coordinate coordinate = this.startingCoordinate;
        for (int i = 0; i < this.word.length(); i++) {
            coordinates.add(coordinate);
            coordinate = Coordinate.getNextCoordinate(coordinate, this.direction);
        }

        return Collections.unmodifiableList(coordinates);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        if (direction != solution.direction) return false;
        return startingCoordinate.equals(solution.startingCoordinate);

    }

    @Override
    public int hashCode() {
        int result = direction.hashCode();
        result = 31 * result + startingCoordinate.hashCode();
        return result;
    }
}