package edu.utdallas.hpews.importer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import edu.utdallas.hpews.R;

/**
 * Created by imper on 4/1/2016.
 */
public class ImportService extends ContextWrapper{
//    private static ImportService ourInstance = new ImportService();
//    public static ImportService getInstance() {
//        return ourInstance;
//    }

    private Activity activityRef;
    private ImageProcessor imageProcessor;
    private ImageHandler imageHandler;
    private Bitmap image;


    public ImportService(Context context){
        super(context);
        activityRef = (Activity)this.getBaseContext();
        imageProcessor = new ImageProcessor(context);
        imageHandler = new ImageHandler(context);
    }

    public void takePicture(){
        imageHandler.takePicture();
    }

    public void choosePicture(){
        imageHandler.choosePicture();
     }

    public void ProcessImage(){
        if (image != null){
            String recognizedText = imageProcessor.getOCRText(image);
            TextView OCRText = (TextView) activityRef.findViewById(R.id.OCRText);
            OCRText.setText(recognizedText);
        }
        else{
            Log.w("ProcessImage", "No image selected, cannot process");
            showErrorDialog();
        }
    }

    public void showErrorDialog(){
        DialogFragment errorDialog = new NoImageDialogFragment();
        errorDialog.show(activityRef.getFragmentManager(), "NoImage");
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public void handleActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData()!=null){
            Uri uri = data.getData();
            try{
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                setImageView(R.id.imageView, image);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        if(requestCode ==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            try{
                Bundle extras = data.getExtras();
                image = (Bitmap) extras.get("data");
                setImageView(R.id.imageView, image);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setImageView(int imageViewID, Bitmap image){
        ImageView imageView = (ImageView) activityRef.findViewById(imageViewID);
        imageView.setImageBitmap(image);

    }
}
