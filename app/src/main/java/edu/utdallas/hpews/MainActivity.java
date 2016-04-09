package edu.utdallas.hpews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.utdallas.hpews.importer.ImportServiceActivity;
import edu.utdallas.hpews.solver.DictionaryService;

/**
 * Created by imper on 3/26/2016.
 */
public class MainActivity extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // We need lazy (vs. static) initialization because we depend on the Context having been
        // initialized; after we make sure that the "small" size performs well, we can try to bump this
        // to MEDIUM or LARGE.
        DictionaryService.initialize(getApplicationContext(), DictionaryService.SIZE.SMALL);


    }

    // goes to PuzzleView. TODO: add puzzle param to pass to intent/activity
    private void goToPuzzle() {
        Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
        startActivity(intent);

    }

    private void launchGeneratorActivity(View view){
        Intent intent = new Intent(MainActivity.this, GeneratorServiceActivity.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();
    }

    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
    }

    // Import puzzle and go to Puzzle View
    public void launchImporter(View view){
        Intent intent = new Intent(this, ImportServiceActivity.class);
        startActivity(intent);
    }

    // Generate puzzle and go to Puzzle View
    public void launchGenerator() {

    }
}
