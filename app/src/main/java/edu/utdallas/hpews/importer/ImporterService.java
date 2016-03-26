package edu.utdallas.hpews.importer;

import android.graphics.Bitmap;
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

    private ImageHandler ImgHandler;
    private ImageProcessor ImgProcessor;

    private ImporterService() {
        ImgHandler = new ImageHandler();
        ImgProcessor = new ImageProcessor();
    }



    public Puzzle importPuzzle(Image image) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    public Bitmap takeImage(){
        // TODO: implement
        throw new UnsupportedOperationException();
    }


    public Puzzle processImage(Image image){
        // TODO: implement
        throw new UnsupportedOperationException();
    }
}
