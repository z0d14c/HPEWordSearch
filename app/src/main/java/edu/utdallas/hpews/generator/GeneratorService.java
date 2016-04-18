package edu.utdallas.hpews.generator;

import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Coordinate;
import edu.utdallas.hpews.model.Direction;
import edu.utdallas.hpews.solver.DictionaryService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class GeneratorService {

    public static final int DEFAULT_DIMENSION = 20;
    public static final int DEFAULT_SOLUTIONS = 10;
    public static final int MAX_ATTEMPTS = 1000;

    public static final int MIN_DIMENSION = 10;
    public static final int MAX_DIMENSION = 25;

    private static GeneratorService ourInstance = new GeneratorService();
    public static GeneratorService getInstance() {
        return ourInstance;
    }


    private GeneratorService() {
    }


    public Puzzle generatePuzzle() {
        return this.generatePuzzle(
                DEFAULT_DIMENSION,
                DictionaryService.getInstance().getWords(DEFAULT_SOLUTIONS)
        );
    }

    public Puzzle generatePuzzle(List<String> wordList) {
        return this.generatePuzzle(
                DEFAULT_DIMENSION,
                wordList
        );
    }

    public Puzzle generatePuzzle(int dimension, List<String> wordList) {

        if (dimension < MIN_DIMENSION || dimension > MAX_DIMENSION) {
            throw new IllegalArgumentException("dimension argument out of range");
        }

        Puzzle puzzle = new Puzzle(dimension);

        // sort words in reverse order of length to maximize chances of successful placement
        Collections.sort(wordList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return Integer.compare(rhs.length(), lhs.length());
            }
        });

        // fill in words
        for (int i = 0; i < wordList.size(); i++) {
            boolean successful = false;
            int attempts = 0;
            while (successful != true && attempts <= MAX_ATTEMPTS) {
                try {
                    Coordinate coord = this.generateRandomCoord(dimension);
                    Direction dir = this.generateRandomDir();
                    puzzle.setWordAt(coord.getX(), coord.getY(), dir, wordList.get(i));
                    successful = true;
                }
                catch (IllegalArgumentException ex) {

                } finally {
                    attempts++;
                }
            }

            if (successful != true) {
                System.out.println("GeneratorService: unable to set word '" + wordList.get(i) + "' after " + attempts + " attempts!");
            }
        }


        // fill in the blanks that are left for each row y in puzzle
        for (int y = 0; y < puzzle.getDimension(); y++){
            for (int x = 0; x < puzzle.getDimension(); x++) {
                if (puzzle.getCharacterAt(x, y) == null) {
                    char randomChar = this.generateRandomChar();
                    puzzle.setCharacterAt(x, y, randomChar);
                }

            }
        }

        return puzzle;

    }

    private int generateRandomInt(int min, int max){
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private char generateRandomChar() {
        int min = 'a';
        int max = 'z';

        int value = generateRandomInt(min, max);

        char randomChar = (char)value;


        return randomChar;
    }

    private Coordinate generateRandomCoord(int dimension){
        int min = 0;
        int max = dimension - 1;

        int randomX = generateRandomInt(min,max);
        int randomY = generateRandomInt(min,max);

        Coordinate randomCoord = new Coordinate(randomX, randomY);

        return randomCoord;
    }

    private Direction generateRandomDir(){
        int min = 0;
        int max = Direction.values().length - 1;

        int randomDir = generateRandomInt(min,max);

        return Direction.values()[randomDir];
    }

}