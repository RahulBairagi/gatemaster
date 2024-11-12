package utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyBoard(Activity context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(context.findViewById(android.R.id.content)
                .getWindowToken(), 0);
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public static void showAlert(Context ctx, final String message, final DialogInterface.OnClickListener positiveClick) {
        final AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", positiveClick)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();

    }


    public static void showOKAlert(Context ctx, final String message) {
        final AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();

    }

    public String convertTimeFormat(String inputTime, String inputFormat, String outputFormat) {
        try {
            // Create SimpleDateFormat objects for the input and output formats
            SimpleDateFormat inputSDF = new SimpleDateFormat(inputFormat);
            SimpleDateFormat outputSDF = new SimpleDateFormat(outputFormat);

            // Parse the input string to a Date object
            Date date = inputSDF.parse(inputTime);

            // Format the Date object into the desired output format
            return outputSDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if the format is incorrect
        }
    }

    public static long getDifferenceInHours(String startDateString, String endDateString, String dateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

            // Parse the date strings into Date objects
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            // Calculate the difference in milliseconds
            long diffInMillis = endDate.getTime() - startDate.getTime();

            // Convert the difference to hours
            long diffInHours = diffInMillis / (1000 * 60 * 60);  // Convert milliseconds to hours

            return diffInHours;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // Return -1 in case of an error (e.g., invalid date format)
        }
    }

    public static String getcurrenttime (){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }

    public static void pushNext(Context ctx, Class activity) {
        Intent intent = new Intent(ctx, activity);
        ctx.startActivity(intent);
    }

    public static void pushwithFinish(Context ctx, Class activity) {
        Intent intent = new Intent(ctx, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);
    }
}
