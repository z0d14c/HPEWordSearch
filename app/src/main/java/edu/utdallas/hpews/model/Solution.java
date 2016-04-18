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
    public String toString() {
        return this.word;
    }



    public boolean conflictsWith(Solution other) {
        if (this.direction == other.direction || this.direction.getOpposite() == other.direction) {
            List<Coordinate> theseCoordinates = this.getCoordinates();
            List<Coordinate> otherCoordinates = other.getCoordinates();

            for (Coordinate thisCoordinate : theseCoordinates) {
                for (Coordinate otherCoordinate : otherCoordinates) {
                    if (thisCoordinate.equals(otherCoordinate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}