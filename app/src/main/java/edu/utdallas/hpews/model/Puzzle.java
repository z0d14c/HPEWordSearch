package edu.utdallas.hpews.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sasha on 3/26/16.
 */
public class Puzzle implements Serializable {

    private final int dimension;
    private final Character[][] data;

    private final List<Solution> solutions;



    public Puzzle(int dimension) {
        this.dimension = dimension;
        this.data = new Character[dimension][dimension];
        this.solutions = new ArrayList<>();
    }



    public Character getCharacterAt(Coordinate coordinate) {
        return this.getCharacterAt(coordinate.getX(), coordinate.getY());
    }

    public Character getCharacterAt(int x, int y) {
        if (x < 0 || x >= this.dimension || y < 0 || y >= this.dimension) {
            throw new IllegalArgumentException("requested coordinates out of range; puzzle dimension is " + this.dimension);
        }

        return this.data[y][x];
    }

    public void setCharacterAt(int x, int y, char character) {
        if (x < 0 || x >= this.dimension || y < 0 || y >= this.dimension) {
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

    public List<String> getSolutionWords() {
        List<String> results = new ArrayList<>();
        for (Solution solution : this.solutions) {
            results.add(solution.getWord());
        }

        Collections.sort(results);

        return results;
    }

    public Solution getWordSolution(String word) {
        Solution result = null;
        for (Solution solution : this.solutions) {
            if (solution.getWord().equals(word)) {
                result = solution;
                break;
            }
        }
        return result;
    }



    public void print() {
        for (int x = 0; x < this.getDimension(); x++) {
            for (int y = 0; y < this.getDimension(); y++) {
                System.out.print(this.data[x][y]);
            }
            System.out.print(System.lineSeparator());
        }
    }

}