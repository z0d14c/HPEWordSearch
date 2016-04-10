package edu.utdallas.hpews.importer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import edu.utdallas.hpews.R;

/**
 * Created by imper on 4/10/2016.
 */
public class UnableToProcessDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_unableToProcess_error)
                .setTitle(R.string.dialog_error_title)
                .setNeutralButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){}
                });
        return builder.create();
    }

}