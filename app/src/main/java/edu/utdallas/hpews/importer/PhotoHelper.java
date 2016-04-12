package edu.utdallas.hpews.importer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by imper on 4/3/2016.
 */
public class PhotoHelper {
    private static PhotoHelper ourInstance = new PhotoHelper();
    public static PhotoHelper getInstance() {
        return ourInstance;
    }

    private final static String CLASS_TAG = "PhotoHelper";
    private PhotoHelper() {
    }

    public static Uri getPhotoFileURI(Context context, String filename){
        Uri uri = null;
        if (isExteralStorageAvailable()){
            File mediaStorageDirectory= context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!mediaStorageDirectory.exists() && !mediaStorageDirectory.mkdirs()){
                Log.d(CLASS_TAG, "failed to create directory");
            }
            else{
                uri = Uri.fromFile(new File(mediaStorageDirectory.getPath() + File.separator + filename));
            }
        }else{
            Log.w(CLASS_TAG, "No external storage available");
        }
        return uri;
    }

    public static String getRealPathFromURI(Context context, Uri uri){
        String filepath = null;
        String fileID = DocumentsContract.getDocumentId(uri);
        String ID = fileID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, selection, new String[]{ID}, null);
        if (cursor == null)
            filepath = uri.getPath();
        else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filepath = cursor.getString(index);
        }
        cursor.close();
        return filepath;
    }

    public static Bitmap getPhotoFromURI(Context context, Uri imageurl){
        Bitmap image = null;
        try{
            image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageurl);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    private static boolean isExteralStorageAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static Bitmap touchUpPhoto(Bitmap bitmap, Uri imageurl){
        bitmap = rotatePhoto(bitmap, imageurl);
        bitmap = convertPhotoToARGB_8888(bitmap);
        return bitmap;
    }

    public static Bitmap rotatePhoto(Bitmap image, Uri imageurl){

        try{
            String path = imageurl.toString();
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
            );

            int rotationNeeded = 0;
            switch(exifOrientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationNeeded = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationNeeded = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationNeeded = 270;
                    break;
            }
            if (rotationNeeded != 0){
                int width = image.getWidth();
                int height = image.getHeight();
                Matrix matrix = new Matrix();
                matrix.preRotate(rotationNeeded);
                image = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    private static Bitmap convertPhotoToARGB_8888(Bitmap bitmap){
        return bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
