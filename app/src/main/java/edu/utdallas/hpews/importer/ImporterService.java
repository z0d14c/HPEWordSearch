package edu.utdallas.hpews.importer;

import android.media.Image;

import edu.utdallas.hpews.model.Puzzle;

/**
 * Created by sasha on 3/26/16.
 */
public class ImporterService {

    private static ImporterService ourInstance = new ImporterService();
    public static ImporterService getInstance() {
        return ourInstance;
    }



    private ImporterService() {
    }



    public Puzzle importPuzzle(Image image) {

        // TODO: implement
        throw new UnsupportedOperationException();

    }
}
