package edu.utdallas.hpews.importer;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import edu.utdallas.hpews.R;

public class ImportServiceActivity extends AppCompatActivity {
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    public void launchPhotoPicker(View view){
        Intent pickPhotoIntent = new Intent();
        pickPhotoIntent.setType("image/*");
        pickPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickPhotoIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public void launchCamera(View view){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){
            Uri uri = data.getData();
            try{
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(image);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        if(requestCode ==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            try{
                Bundle extras = data.getExtras();
                image = (Bitmap) extras.get("data");
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(image);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void ProcessImage(View view){
        if (image != null){
            //Intent processImageIntent = new Intent();
            //TODO: implement
        }
        else{
            showErrorDialog();
        }
    }

    public void showErrorDialog(){
        DialogFragment errorDialog = new NoImageDialogFragment();
        errorDialog.show(getFragmentManager(), "NoImage");
    }
}
