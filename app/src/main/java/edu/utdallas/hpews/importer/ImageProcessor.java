package edu.utdallas.hpews.importer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.utdallas.hpews.model.Puzzle;

/**
 * Created by imper on 3/26/2016.
 */
public class ImageProcessor {

    private TessBaseAPI mTess;
    public Context context;
    private String datapath = "";

    public ImageProcessor(Context ctx){
        context = ctx;
        datapath = context.getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();

        String language = "eng";
        File dir = new File(datapath + "/tessdata");
        if (!dir.exists()){
            Log.d("ImageProcessor", "Directory " + datapath + "Not found!");
            if(dir.mkdirs()){
                copyFiles();
                Log.d("ImageProcessor", "Copied training data to device");
            }
            else{
                Log.d("ImageProcessor", "Unable to create tesseract directory");
            }
        }
        if(dir.exists()){
            Log.d("ImageProcessor", "Directory " + datapath + " found!");

            File td = new File(datapath + "/tessdata/eng.traineddata");
            if (!td.exists()){
                Log.d("ImageProcessor", "No training data file found. Copying to device...");
                copyFiles();
            }
            else{
                Log.d("ImageProcessor", "Training data file found! Size : " + td.length() + " bytes");
            }
            Log.v("ImageProcessor", "Attempting to init Tess");
            mTess.init(datapath, language);
        }

    }

    private void copyFiles(){
        try{

            InputStream instream = null;
            OutputStream outstream = null;

            String fullpath = datapath + "tessdata/eng.traineddata";
            AssetManager assetManager = context.getAssets();
            instream = assetManager.open("tessdata/eng.traineddata");
            outstream = new FileOutputStream(fullpath);

            byte[]buffer = new byte[1024];
            int read;
            while((read = instream.read(buffer))!= -1){
                outstream.write(buffer, 0,read );
            }

            instream.close();
            outstream.flush();
            outstream.close();
            instream = null;
            outstream = null;


            File file = new File(fullpath);
            if (!file.exists())
                Log.v("copyFiles", "Was unable to create file eng.traineddata");
            Log.v("copyFiles","Size of copied file : " + file.length());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    public String getOCRText(Bitmap bitmap, Uri imageurl){
        String OCRresult = null;
        try{
            Bitmap touchedUpImage = PhotoHelper.touchUpPhoto(bitmap, imageurl);
            mTess.setImage(touchedUpImage);
            OCRresult = mTess.getUTF8Text();
        }
    catch(Exception e){
            e.printStackTrace();
        }
        return OCRresult;
    }

    //TODO: quality check method
    //TODO: processImage: This method turns OCR text into a puzzle.

    public Puzzle processImage(Bitmap bitmap){

        throw new UnsupportedOperationException();
    }
}
