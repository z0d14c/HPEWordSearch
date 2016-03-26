package edu.utdallas.hpews.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasha on 3/26/16.
 */
public class Puzzle {

    private final int dimension;
    private final Character[][] data;

    private final List<Solution> solutions;



    public Puzzle(int dimension) {
        this.dimension = dimension;
        this.data = new Character[dimension][dimension];
        this.solutions = new ArrayList<>();
    }



    public Character getCharacterAt(int x, int y) {
        if (x >= this.dimension || y >= this.dimension) {
            throw new IllegalArgumentException("requested coordinates out of range; puzzle dimension is " + this.dimension);
        }

        return this.data[y][x];
    }

    public void setCharacterAt(int x, int y, char character) {
        if (x >= this.dimension || y >= this.dimension) {
            throw new IllegalArgumentException("requested coordinates out of range; puzzle dimension is " + this.dimension);
        }

        if (this.data[y][x] != null && this.data[y][x] != character) {
            throw new IllegalArgumentException("requested coordinates are already set with the letter " + this.data[y][x]);
        }

        this.data[y][x] = character;
    }

    public void setWordAt(int x, int y, Direction direction, String word) {

        Coordinate coordinate = new Coordinate(x, y);

        for (int i = 0; i < word.length(); i++) {

            this.setCharacterAt(coordinate.getX(), coordinate.getY(), word.charAt(i));

            coordinate = Coordinate.getNextCoordinate(coordinate, direction);

        }
    }

    public int getDimension() {
        return dimension;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

}