package edu.utdallas.hpews.generator;

import edu.utdallas.hpews.model.Puzzle;

/**
 * Created by sasha on 3/26/16.
 */
public class GeneratorService {

    public static final int DEFAULT_DIMENSION = 25;

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

        // TODO: implement
        throw new UnsupportedOperationException();

    }

}