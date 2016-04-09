package edu.utdallas.hpews.generator;

import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Coordinate;
import edu.utdallas.hpews.model.Direction;
import edu.utdallas.hpews.solver.DictionaryService;
import java.util.List;
import java.util.Random;


public class GeneratorService {

    public static final int DEFAULT_DIMENSION = 25;
    public static final int DEFAULT_SIZE = 10;

    private static GeneratorService ourInstance = new GeneratorService();
    public static GeneratorService getInstance() {
        return ourInstance;
    }


    private GeneratorService() {
    }


    public Puzzle generatePuzzle() {
        return this.generatePuzzle(DEFAULT_DIMENSION);
    }

    public Puzzle generatePuzzle(int dimension) {

        Puzzle puzzle = new Puzzle(dimension);

        // fill in words
        List<String> wordList = DictionaryService.getInstance().getWords(DEFAULT_SIZE);

        for (int i = 0; i < wordList.size(); i++) {
            boolean successful = false;
            while (successful) {
                try {
                    Coordinate coord = this.generateRandomCoord();
                    Direction dir = this.generateRandomDir();
                    puzzle.setWordAt(coord.getX(), coord.getY(), dir, wordList.get(i));
                    successful = true;
                }
                catch (IllegalArgumentException ex) {
                    System.err.println("IllegalArgumentException: " + ex.getMessage());
                }
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
        Random rand = new Random();
        int value = rand.nextInt(max) + min;

        return value;
    }

    private char generateRandomChar() {
        int min = 'a';
        int max = 26;

        int value = generateRandomInt(min, max);

        char randomChar = (char)value;


        return randomChar;
    }

    private Coordinate generateRandomCoord(){
        int min = 1;
        int max = DEFAULT_DIMENSION;

        int randomX = generateRandomInt(min,max);
        int randomY = generateRandomInt(min,max);

        Coordinate randomCoord = new Coordinate(randomX, randomY);

        return randomCoord;
    }

    private Direction generateRandomDir(){
        int min = 1;
        int max = 8;

        int randomDir = generateRandomInt(min,max);

        Direction direction;

        switch(randomDir) {
            case 1: direction = Direction._0;
                return direction;
            case 2: direction = Direction._45;
                return direction;
            case 3: direction = Direction._90;
                return direction;
            case 4: direction = Direction._135;
                return direction;
            case 5: direction = Direction._180;
                return direction;
            case 6: direction = Direction._225;
                return direction;
            case 7: direction = Direction._270;
                return direction;
            case 8: direction = Direction._315;
                return direction;
            default: direction = Direction._0;
                return direction;
        }
    }

}