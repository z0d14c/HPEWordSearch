package edu.utdallas.hpews.importer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.utdallas.hpews.MainActivity;
import edu.utdallas.hpews.R;

//TODO: Delegate imageHandler and importService methods
/**
 * Import Service Activity is resposible for binding UI events to their backend handler.
 * All back end logic needs to happen in the handler.
 * **/
public class ImportServiceActivity extends AppCompatActivity {
    Bitmap image;
    private ImportService importService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        importService = new ImportService(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        importService.handleActivityResult(requestCode, resultCode, data);
    }

    public void askForPhotoType(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ImportServiceActivity.this);
        builder.setTitle(R.string.import_dialog)
                .setItems(R.array.import_dialog_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                launchPhotoPicker(view);
                                break;
                            case 1:
                                launchCamera(view);
                        }
                    }
                });
        builder.show();
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    public void launchPhotoPicker(View view){
        importService.choosePicture();
    }


    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public void launchCamera(View view){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void ProcessImage(View view){
        Log.v("ProcessImage", "Starting OCR");
        importService.ProcessImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
