package com.jhotel.steven.jhotel_android_nurhazbiy.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

 /**
 *  This class is used for creating alert dialog from anywhere
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class AlertDialogAdapter{
     /**
      * Creating alert dialog from anywhere with modified value
      *
      * @param context activity's context where the alert dialog created from
      * @param title alert dialog's title
      * @param message alert dialog's message
      * @param onClickListener alert dialog's button behavior
      */
    public void showAlertDialog(Context context, String title, String message,DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting OK Button
        alertDialog.setNeutralButton("OK",onClickListener);

        // Setting Cancelable to false
        alertDialog.setCancelable(false);

        // Creating Alert Message
        alertDialog.create();

        // Showing Alert Message
        alertDialog.show();
    }
}
