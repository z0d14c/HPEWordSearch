package edu.utdallas.hpews.importer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import edu.utdallas.hpews.R;

/**
 * Created by imper on 3/26/2016.
 */
public class ImageHandler extends ContextWrapper {

    private final String CLASS_TAG = "ImageHandler";
    Bitmap image;
    Activity activityRef;
    public ImageHandler(Context context) {
        super(context);
        activityRef = (Activity)this.getBaseContext();
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    public void choosePicture() {
        Intent pickPhotoIntent = new Intent();
        pickPhotoIntent.setType("image/*");
        pickPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);

        activityRef.startActivityForResult(Intent.createChooser(pickPhotoIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public String photoFileName = "photo.jpg";

    public void takePicture() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoHelper.getPhotoFileURI( activityRef, photoFileName));

        if (takePhotoIntent.resolveActivity(getPackageManager()) != null){
            activityRef.startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
        else{
            Log.w(CLASS_TAG, "Need permission to use Camera.");
        }
    }

}
