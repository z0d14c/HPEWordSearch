package edu.utdallas.hpews.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import edu.utdallas.hpews.R;

/**
 * Created by imper on 3/26/2016.
 */
public class MainActivity extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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

}
