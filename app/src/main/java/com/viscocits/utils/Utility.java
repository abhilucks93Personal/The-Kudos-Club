package com.viscocits.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.viscocits.R;
import com.viscocits.home_post.model.postModels.ModelPostComments;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPostData;
import com.viscocits.home_post.model.postModels.ModelPostImages;
import com.viscocits.home_post.model.postModels.ModelPostLikes;
import com.viscocits.home_post.model.postModels.ModelPostMergedData;
import com.viscocits.home_post.model.postModels.ModelPostRecognizeImages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;

import static android.content.ContentValues.TAG;


/**
 * @author Wildnet technologies
 */
public class Utility {


    private static final int REQUEST_LOCATION = 2000;
    private static AlertDialog.Builder builder;
    private static AlertDialog alert;


    public static void datePickerDialog(Activity context, DatePickerDialog.OnDateSetListener listner) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context, listner,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        dialog.show();
    }

    public static void datePickerDialogDob(Activity context, DatePickerDialog.OnDateSetListener listner) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context, listner,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        dialog.show();
    }

    public static Uri getImageUri(Context context, Bitmap mBitmap) {
        Uri uri = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            // Calculate inSampleSize
            // options.inSampleSize = calculateInSampleSize(options, 100, 100);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 200, 200,
                    true);
            File file = new File(context.getFilesDir(), "Image"
                    + new Random().nextInt() + ".jpeg");
            FileOutputStream out = context.openFileOutput(file.getName(),
                    Context.MODE_WORLD_READABLE);

            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //Bitmap compressedImageBitmap = Compressor.getDefault(context).compressToBitmap(file1);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }


    @Nullable
    public static Uri saveBitmapToDisk(Context context, Bitmap bmp) {
        Uri finalUri = null;
        File file = new File(context.getFilesDir(), "Image" + new Random().nextInt() + ".png");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        try {
            file.createNewFile();
            boolean wasSuccessful = file.createNewFile();
            if (!wasSuccessful)
                Log.e("error", "failed");
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
            File file1 = new Compressor(context).compressToFile(file);
            finalUri = Uri.fromFile(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalUri;
    }

    public static String getRealPathFromUri(Context mContext, Uri mUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(mContext, mUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void setStatusBarTranslucent(Activity context, boolean makeTranslucent) {
        if (makeTranslucent) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void showSnackBar(Activity context, String str) {
        View view = context.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show();

    }

    public static void showSnackBar(View view, String str) {
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show();

    }

    public static void showEditTextsAsMandatory(TextInputLayout... ets) {
        for (TextInputLayout et : ets) {


            String hint = et.getHint().toString() + "  ";

            et.setHint(Html.fromHtml(hint + "<font color=\"#ff0000\">" + "* " + "</font>"));
        }
    }

    public static void showEditTextsAsMandatory(EditText... ets) {
        for (EditText et : ets) {


            String hint = et.getHint().toString() + "  ";

            et.setHint(Html.fromHtml(hint + "<font color=\"#ff0000\">" + "* " + "</font>"));
        }
    }

    public static void displayAlert(final Context context, String title, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setTitle(title).setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do your code here
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();

            }
        }).show();
    }

    private static void call() {


    }

    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            int res = context.checkCallingOrSelfPermission(permissions[0]);
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        //String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String convertDate(String str) {

        String[] separated = str.split("T");
        String date = formatDateFromString("yyyy-MM-dd", "MMM d, yyyy", separated[0]);

        return date;
    }

    public static String convertDateOnly(String str) {

        String[] separated = str.split("T");
        String date = separated[0];

        return date;
    }

   /* public static boolean checkGooglePlayServicesAvailable(Activity activity) {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            return true;
        }

        Log.e("LOGTAG", "Google Play Services not available: " + GooglePlayServicesUtil.getErrorString(status));

        if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            final Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(status, activity, 1);
            if (errorDialog != null) {
                errorDialog.show();
            }
        }

        return false;
    }*/

    public static String formatDateFromString(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void showDevelopmentToast(Context context) {
        Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
    }

    public static void addPreferences(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static void addPreferences(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        String text = prefs.getString(key, "");
        return text;
    }



    public static Boolean getPreferences(Context context, String key, boolean defaut) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        Boolean text = prefs.getBoolean(key, defaut);
        return text;
    }

    public static void clearPreferenceData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public static boolean lengthValidation(String str) {
        if (str.length() > 0)
            return true;
        else
            return false;
    }

    public static boolean isInternetConnected(Activity mContext) {

		/*
         * final ConnectivityManager connec = (ConnectivityManager)
		 * mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		 *
		 * if (connec != null&& (connec.getNetworkInfo(1).getState() ==
		 * NetworkInfo.State.CONNECTED)|| (connec.getNetworkInfo(0).getState()
		 * == NetworkInfo.State.CONNECTED)) { return true; } return false;
		 */
        if (mContext != null) {
            hideKeyboard(mContext);
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                showSnackBar(mContext, "offline msg");
            }
        }

        return false;
    }

    public static void showToast(Context mContext, String string) {
        Toast t = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
        t.show();

    }

    public static boolean isLocationEnabled(Context context) {

        LocationManager lm = null;
        boolean gps_enabled = false, network_enabled = false;
        if (lm == null) {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (gps_enabled == true && network_enabled == true) {
            return true;
        } else {
            return false;
        }

    }

    public static String getFormattedPostDate(String _endDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        _endDate = _endDate.replace("T", " ");

        Date endDate = null;
        try {
            endDate = dateFormat.parse(dateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.getDefault()).parse(_endDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //milliseconds
        StringBuilder updatedTime = new StringBuilder();


        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        Date startDate = null;
        try {
            startDate = dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (endDate != null) {
            long different = startDate.getTime() - endDate.getTime();


            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays,
                    elapsedHours, elapsedMinutes, elapsedSeconds);
            if (elapsedDays > 0) {
                updatedTime.append(elapsedDays + " days ago");
                return updatedTime.toString();

            }
            if (elapsedHours > 0) {
                updatedTime.append(elapsedHours + " hour ago");
                return updatedTime.toString();


            }
            if (elapsedMinutes > 0) {
                updatedTime.append(elapsedMinutes + " minutes ago");
                return updatedTime.toString();


            }
            if (elapsedSeconds > 0) {
                updatedTime.append(elapsedSeconds + " seconds ago");
                return updatedTime.toString();


            }
            return "Just now";
        } else
            return "";
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri imageUri) {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String getEncoded64ImageStringFromUri(Context context, Uri mImageUri) {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;

    }

    public static void password_visible(EditText et_password, ImageView iv) {
        if (et_password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            et_password.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            iv.setImageResource(R.drawable.ic_launcher);
        } else {
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            iv.setImageResource(R.drawable.ic_launcher);
        }
        et_password.setSelection(et_password.getText().length());
    }


   /* public static Uri saveBitmapToDisk(Context context, Bitmap bmp) {
        Uri finalUri = null;
        File file = new File(context.getFilesDir(), "Image" + new Random().nextInt() + ".png");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        try {
            file.createNewFile();
            boolean wasSuccessful = file.createNewFile();
            if (!wasSuccessful)
                Log.e("error", "failed");
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
            File file1 = new Compressor(context).compressToFile(file);
            finalUri = Uri.fromFile(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalUri;
    }
*/

    public static String getLoginErrorMessage(int loginType) {
        switch (loginType) {
            case 0:
                return "This email ID is already registered.";
            case 1:
                return "This email ID is already registered. Login with Facebook";
            case 2:
                return "This email ID is already registered. Login with Gmail";
        }
        return "This email ID is already registered.";
    }


    public static void showTextViewsAsMandatory(TextView... tvs) {
        for (TextView tv : tvs) {
            String hint = tv.getHint().toString() + " ";
            tv.setHint(Html.fromHtml(hint + "<font color=\"#ff0000\">" + "* " + "</font>"));
        }
    }

    public static void showDialog(Activity activity, String alert, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        // set title
        //alertDialogBuilder.setTitle(alert);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    public static void shareImageOnly(Activity activity, ImageView imageView) {
        try {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawable.getBitmap();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, Utility.getLocalBitmapUri(activity.getApplicationContext(), bitmap));
                activity.startActivity(Intent.createChooser(i, "Share with"));
            }
        } catch (Exception e) {
            Utility.showToast(activity.getApplicationContext(), "Cannot share this post");
        }

    }

    public static void shareImagesOnly(Activity activity, ArrayList<ImageView> imageViews) {
        try {
            ArrayList<Uri> files = new ArrayList<>();
            int index = 0;
            for (ImageView iv : imageViews) {
                BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                if (drawable != null) {
                    Bitmap bitmap = drawable.getBitmap();
                    Uri uri = Utility.getLocalBitmapUri2(activity.getApplicationContext(), bitmap, index);
                    files.add(uri);
                }
                index++;
            }

            Intent i = new Intent(Intent.ACTION_SEND_MULTIPLE);
            i.setType("image/*");

            i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            activity.startActivity(Intent.createChooser(i, "Share with"));

        } catch (Exception e) {
            Utility.showToast(activity.getApplicationContext(), "Cannot share this post");
        }

    }

    public static Intent shareImageIntent(Context context, ImageView imageView) {
        Intent i = null;
        try {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawable.getBitmap();
                i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, Utility.getLocalBitmapUri(context, bitmap));

            }
        } catch (Exception e) {
            Utility.showToast(context, "Cannot share this post");
        }

        return i;
    }

    public static Uri getLocalBitmapUri(Context context, Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image.png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static Uri getLocalBitmapUri2(Context context, Bitmap bmp, int i) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "" + i + "image.png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static void buildAlertMessageNoGps(final Activity _activity) {
        if (builder == null) {
            builder = new AlertDialog.Builder(_activity);
            builder.setMessage("Your GPS seems to be disabled.Please turn on the GPS to find businesses around you.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            _activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
        }
        if (alert == null)
            alert = builder.create();
        if (!alert.isShowing())
            alert.show();
    }

    public static boolean isGpsEnabled(Activity activity) {
        boolean isEnabled = false;
        LocationManager locationManager = (LocationManager) activity.getApplicationContext().getSystemService(activity.getApplicationContext().LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
                isEnabled = false;
            } else {
                isEnabled = true;
            }
        }

        return isEnabled;
    }

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLocality()).append(", ");
                        sb.append(address.getAdminArea()).append(", ");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }


   /* public static Bitmap getBarcodeImage(String barCodeData) {
        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(barCodeData, BarcodeFormat.CODE_128, 600, 300);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }*/

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    /* public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
         String contentsToEncode = contents;
         if (contentsToEncode == null) {
             return null;
         }
         Map<EncodeHintType, Object> hints = null;
         String encoding = guessAppropriateEncoding(contentsToEncode);
         if (encoding != null) {
             hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
             hints.put(EncodeHintType.CHARACTER_SET, encoding);
         }
         MultiFormatWriter writer = new MultiFormatWriter();
         BitMatrix result;
         try {
             result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
         } catch (IllegalArgumentException iae) {
             // Unsupported format
             return null;
         }
         int width = result.getWidth();
         int height = result.getHeight();
         int[] pixels = new int[width * height];
         for (int y = 0; y < height; y++) {
             int offset = y * width;
             for (int x = 0; x < width; x++) {
                 pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
             }
         }

         Bitmap bitmap = Bitmap.createBitmap(width, height,
                 Bitmap.Config.ARGB_8888);
         bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
         return bitmap;
     }
 */
    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    public static void OpenPlayStore(Activity activity) {
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getAppUrl(Activity activity) {
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        return "https://play.google.com/store/apps/details?id=" + appPackageName;

    }

    /*public static ArrayList<ModelResponseWallPostData> getPostDummyData() {

        ArrayList<ModelResponseWallPostData> datas = new ArrayList<>();

        ArrayList<ModelCommentsData> commentDatas;


// item 1

        commentDatas = new ArrayList<>();
        commentDatas.add(new ModelCommentsData("http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "Gab Lopez",
                "That was really fun. Thanks anshul.",
                "20 days ago"));

        datas.add(new ModelResponseWallPostData(
                "Anshul Jha",
                "20 days ago",
                "http://wallpapercave.com/wp/f5elcfO.jpg",
                "4",
                "Organize a birthday thingy for team member.",
                "https://cdn.tipjunkie.com/wp-content/uploads/2012/05/24-Best-Adult-Birthday-Party-Ideas-650x330.jpg",
                "0",
                commentDatas
        ));

// item 2

        commentDatas = new ArrayList<>();
        commentDatas.add(new ModelCommentsData("http://www.jurgita.com/images_new/models/M/portfolio-fashion/w422xh450/atul-k-266948-304652.jpg",
                "Mahbub Ahmed",
                "This is awsome.",
                "1 month ago"));

        commentDatas.add(new ModelCommentsData("http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "Gab Lopez",
                "Thanks Mr. Ahmed",
                "1 month ago"));

        commentDatas.add(new ModelCommentsData("http://wallpapercave.com/wp/f5elcfO.jpg",
                "Anshul Jha",
                "Loved it. Looking forward for next",
                "1 month ago"));

        commentDatas.add(new ModelCommentsData("http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "Gab Lopez",
                "Sure. Thanks Anshul",
                "1 month ago"));


        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "1 month ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "2",
                "Make a video of yourself showing you're dynamic and post it on Avintiy. ",
                "https://www.ihgplc.com/files/reports/ar2014/assets/images/pictures/video_thumb.jpg",
                "1",
                commentDatas
        ));

// item 3

        commentDatas = new ArrayList<>();

        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "2 months ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "2",
                "Choose a parameter you would like to challenge on a monthly basis. Increase this parameter by whatever percentage, but increase it for 6 months.",
                "https://www.leg.state.mn.us/docs/2002webarchive/departmentresults/doer/images/image016.gif",
                "0",
                commentDatas
        ));

// item 4

        commentDatas = new ArrayList<>();

        datas.add(new ModelResponseWallPostData(
                "Anshul Jha",
                "2 months ago",
                "http://wallpapercave.com/wp/f5elcfO.jpg",
                "4",
                "Master a new language to a A1 Level and ask a colleague for help to do so.",
                "http://www.olis.edu.mt/subrel/french/delf.jpg",
                "0",
                commentDatas
        ));

// item 5

        commentDatas = new ArrayList<>();
        commentDatas.add(new ModelCommentsData("http://www.jurgita.com/images_new/models/M/portfolio-fashion/w422xh450/atul-k-266948-304652.jpg",
                "Mahbub Ahmed",
                "That was creative.",
                "2 month ago"));

        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "2 months ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "4",
                "Think up an idea and present it to your team",
                "https://thumbs.dreamstime.com/x/business-office-team-work-12310964.jpg",
                "0",
                commentDatas
        ));

// item 6

        commentDatas = new ArrayList<>();


        datas.add(new ModelResponseWallPostData(
                "Ravi Poonia",
                "2 months ago",
                "https://s-media-cache-ak0.pinimg.com/originals/35/54/30/3554306d06a1cdeffffc9c914d1612d9.jpg",
                "1",
                "Find a .net ninja for Bulgaria to develop our sports betting platform",
                "https://evolvesitiosweb.com/wp-content/uploads/2014/03/Nosotros.jpg",
                "2",
                commentDatas
        ));

// item 7

        commentDatas = new ArrayList<>();


        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "2 months ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "4",
                "Create an easy profile of your team and present each member with the area the person can offer support and help in and publish it.",
                "https://www.mangoapps.com/website/images/product/team-collboration/profile.jpg",
                "1",
                commentDatas
        ));

// item 8

        commentDatas = new ArrayList<>();


        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "2 months ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "6",
                "Share your knowledge in a weekly knowledge session with an unexperienced colleague/newstarter. ",
                "https://i2officeblog.files.wordpress.com/2012/12/i2-office-team-december-2012.jpg",
                "0",
                commentDatas
        ));

// item 9

        commentDatas = new ArrayList<>();


        datas.add(new ModelResponseWallPostData(
                "Mahbub Ahmed",
                "2 months ago",
                "http://www.jurgita.com/images_new/models/M/portfolio-fashion/w422xh450/atul-k-266948-304652.jpg",
                "1",
                "creating test challenge for collaboration pillar.",
                "http://5linx.com/wp-content/uploads/2014/04/hi5-page1.jpg",
                "0",
                commentDatas
        ));

// item 10

        commentDatas = new ArrayList<>();

        datas.add(new ModelResponseWallPostData(
                "Gab Lopez",
                "3 months ago",
                "http://www.hdnicewallpapers.com/Walls/Big/Johnny%20Depp/Hollywood_Actor_Hohnny_Depp.jpg",
                "6",
                "Interview a random colleague to find out more about him or her and write one line about it on Avinity.",
                "https://everydayinterviewtips.com/wp-content/uploads/2014/10/59541335-WaveBreakMediaMicro-job-interview-positive-handshake.jpg",
                "0",
                commentDatas
        ));

// item 11

        commentDatas = new ArrayList<>();

        datas.add(new ModelResponseWallPostData(
                "Ravi Poonia",
                "3 months ago",
                "https://s-media-cache-ak0.pinimg.com/originals/35/54/30/3554306d06a1cdeffffc9c914d1612d9.jpg",
                "2",
                "Make and post a photo of your team being dynamic.   ",
                "https://www.rivs.com/wp-content/uploads/sites/8/2014/12/14485059353_8d009d4eb3_z.jpg",
                "6",
                commentDatas
        ));


        return datas;
    }*/

    public static int getPostColor(Activity context, String type) {

        int color = context.getResources().getColor(R.color.color_light_grey);


        switch (type) {

            case "1":
                color = context.getResources().getColor(R.color.colorPostTypeCollaboration);
                break;

            case "2":
                color = context.getResources().getColor(R.color.colorPostTypeTDynamic);
                break;

            case "3":
                color = context.getResources().getColor(R.color.colorPostTypeExceptional);
                break;

            case "4":
                color = context.getResources().getColor(R.color.colorPostTypeOwnership);
                break;

            case "5":
                color = context.getResources().getColor(R.color.colorPostTypeRecognition);
                break;

            case "6":
                color = context.getResources().getColor(R.color.colorPostTypeTransparency);
                break;


        }


        return color;


    }

    public static Drawable getPostLogo(Activity context, String type) {
        Drawable logo = context.getResources().getDrawable(R.drawable.image_thumb);


        switch (type) {

            case "1":
                logo = context.getResources().getDrawable(R.drawable.logo_collaboration);
                break;

            case "2":
                logo = context.getResources().getDrawable(R.drawable.logo_dynamic);
                break;

            case "3":
                logo = context.getResources().getDrawable(R.drawable.logo_transparency);
                break;

            case "4":
                logo = context.getResources().getDrawable(R.drawable.logo_ownership);
                break;

            case "5":
                logo = context.getResources().getDrawable(R.drawable.logo_transparency);
                break;

            case "6":
                logo = context.getResources().getDrawable(R.drawable.logo_transparency);
                break;


        }


        return logo;
    }


    public static ArrayList<ModelResponseWallPostData> getFIlteredPost(ArrayList<ModelResponseWallPostData> completeList
            , String typeFilter
            , String typeCountry) {

        ArrayList<ModelResponseWallPostData> filteredList = new ArrayList<>();

        if (typeFilter.equals("0"))
            return completeList;
        else {
            for (ModelResponseWallPostData data : completeList) {
                if ("2".equals(typeFilter)) {
                    filteredList.add(data);
                }
            }
            return filteredList;
        }
    }

    public static ArrayList<ModelPostMergedData> getMergedPostData(ArrayList<ArrayList<ModelResponseWallPostData>> data) {

        ArrayList<ModelPostMergedData> modelPostMergedDatas = new ArrayList<>();

        // Merge Post Array Data

        for (ModelResponseWallPostData modelPostData : data.get(0)) {

            ModelPostMergedData modelPostMergedData = new ModelPostMergedData();

            modelPostMergedData.setActive(modelPostData.getActive());
            modelPostMergedData.setAvatarExt(modelPostData.getAvatarExt());
            modelPostMergedData.setClient_Id(modelPostData.getClient_Id());
            modelPostMergedData.setCOMMENTCOUNT(modelPostData.getCOMMENTCOUNT());
            modelPostMergedData.setCountry_id(modelPostData.getCountry_id());
            modelPostMergedData.setEmail(modelPostData.getEmail());
            modelPostMergedData.setEngagement_Id(modelPostData.getEngagement_Id());
            modelPostMergedData.setGroupCountryIDs(modelPostData.getGroupCountryIDs());
            modelPostMergedData.setGrouped(modelPostData.getGrouped());
            modelPostMergedData.setInvolvementCOUNT(modelPostData.getInvolvementCOUNT());
            modelPostMergedData.setLIKECOUNT(modelPostData.getLIKECOUNT());
            modelPostMergedData.setLIKEDBYUSER(modelPostData.getLIKEDBYUSER());
            modelPostMergedData.setUserName(modelPostData.getUserName());
            modelPostMergedData.setSuggestedChallenge(modelPostData.getSuggestedChallenge());
            modelPostMergedData.setSPAMEDBYUSER(modelPostData.getSPAMEDBYUSER());
            modelPostMergedData.setReward_Id(modelPostData.getReward_Id());
            modelPostMergedData.setPostType(modelPostData.getPostType());
            modelPostMergedData.setPostId(modelPostData.getPostId());
            modelPostMergedData.setPostedDate(modelPostData.getPostedDate());
            modelPostMergedData.setPostedBy(modelPostData.getPostedBy());
            modelPostMergedData.setName(modelPostData.getName());
            modelPostMergedData.setMessage(modelPostData.getMessage());

            modelPostMergedDatas.add(modelPostMergedData);

        }

        // Merge Comments Array Data

        for (ModelPostMergedData modelPostMergedData : modelPostMergedDatas) {

            ArrayList<ModelPostComments> commentsDatas = new ArrayList<>();

            for (ModelResponseWallPostData modelPostData : data.get(1)) {
                if (modelPostMergedData.getPostId() == modelPostData.getPostId()) {

                    ModelPostComments commentsData = new ModelPostComments(
                            modelPostData.getPostId(),
                            modelPostData.getCommentedBy(),
                            modelPostData.getCommentedDate(),
                            modelPostData.getCommentId(),
                            modelPostData.getUpdatedBy(),
                            modelPostData.getUpdatedDate(),
                            modelPostData.getAvatarExt(),
                            modelPostData.getName(),
                            modelPostData.getMessage());

                    commentsDatas.add(commentsData);

                }
            }

            modelPostMergedData.setCommentsList(commentsDatas);

        }

       /* // Merge Comments Array Data

        for (ModelPostMergedData modelPostMergedData : modelPostMergedDatas) {

            ArrayList<ModelPostComments> commentsDatas = new ArrayList<>();

            for (ModelResponseWallPostData modelPostData : data.get(1)) {
                if (modelPostMergedData.getPostId() == modelPostData.getPostId()) {

                    ModelPostComments commentsData = new ModelPostComments(
                            modelPostData.getPostId(),
                            modelPostData.getCommentedBy(),
                            modelPostData.getCommentedDate(),
                            modelPostData.getCommentId(),
                            modelPostData.getUpdatedBy(),
                            modelPostData.getUpdatedDate());

                    commentsDatas.add(commentsData);

                }
            }

            modelPostMergedData.setCommentsList(commentsDatas);

        }*/

        // Merge Likes Array Data

        for (ModelPostMergedData modelPostMergedData : modelPostMergedDatas) {

            ArrayList<ModelPostLikes> likesDatas = new ArrayList<>();

            for (ModelResponseWallPostData modelPostData : data.get(2)) {
                if (modelPostMergedData.getPostId() == modelPostData.getPostId()) {

                    ModelPostLikes likesData = new ModelPostLikes(
                            modelPostData.getPostId(),
                            modelPostData.getLiked(),
                            modelPostData.getLikedBy(),
                            modelPostData.getLikedDate(),
                            modelPostData.getLikeId());

                    likesDatas.add(likesData);

                }
            }
            modelPostMergedData.setLikesList(likesDatas);

        }


        // Merge Post Images Array Data

        for (ModelPostMergedData modelPostMergedData : modelPostMergedDatas) {

            ArrayList<ModelPostImages> imagesDatas = new ArrayList<>();

            for (ModelResponseWallPostData modelPostData : data.get(3)) {
                if (modelPostMergedData.getPostId() == modelPostData.getPostId()) {

                    ModelPostImages postImages = new ModelPostImages(
                            modelPostData.getPostId(),
                            modelPostData.getContentType(),
                            modelPostData.getCreatedBy(),
                            modelPostData.getCreatedDate(),
                            modelPostData.getImagePath(),
                            modelPostData.getUrlLink(),
                            modelPostData.getUserActEvidence_Id(),
                            modelPostData.getUserActivity_Id()
                    );

                    imagesDatas.add(postImages);

                }
            }
            modelPostMergedData.setPostImagesList(imagesDatas);
        }


        // Merge Recognize Images Array Data

        for (ModelPostMergedData modelPostMergedData : modelPostMergedDatas) {

            ArrayList<ModelPostRecognizeImages> recognizeImages = new ArrayList<>();

            for (ModelResponseWallPostData modelPostData : data.get(4)) {
                if (modelPostMergedData.getPostId() == modelPostData.getPostId()) {

                    ModelPostRecognizeImages postRecognizeImages = new ModelPostRecognizeImages(
                            modelPostData.getPostId(),
                            modelPostData.getRecogImage_Id(),
                            modelPostData.getRecognition_Id(),
                            modelPostData.getUploadedBy(),
                            modelPostData.getUploadedDate(),
                            modelPostData.getImagePath());

                    recognizeImages.add(postRecognizeImages);
                }
            }
            modelPostMergedData.setRecognizeImagesList(recognizeImages);
        }

        return modelPostMergedDatas;
    }

    public static ArrayList<String> getAllPostImages(ArrayList<ModelPostImages> postImagesList, ArrayList<ModelPostRecognizeImages> recognizeImagesList) {
        ArrayList<String> allPostImages = new ArrayList<>();

        for (ModelPostImages postImages : postImagesList) {
            allPostImages.add(postImages.getImagePath());
        }
        // allPostImages.add("test");
        for (ModelPostRecognizeImages recognizeImages : recognizeImagesList) {
            allPostImages.add(recognizeImages.getImagePath());

        }

        return allPostImages;
    }

    public static String getFormattedTime(String commentedDate) {

       /*
            Date today = new Date();
            Date dateObj = new Date(millis);
            long diff = 0;
            String displayDate = "";
            try {
                if ((today.getTime() - dateObj.getTime()) < (60000)) {
                    displayDate = "Just Now";
                    return displayDate;
                } else {
                    diff = (today.getTime() - dateObj.getTime()) / (86400000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (diff == 0) {
                displayDate = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(dateObj);
            } else if (diff == 1) {
                displayDate = "Yesterday";
            } else if (diff > 1) {
                displayDate = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(dateObj);
            }

            return displayDate;*/

        return "";


    }








   /*






    public void startImageDialog(String imageUrl) {
        DialogFragment frag = ImageDialogFragment.newInstance("",imageUrl);
        frag.show(getSupportFragmentManager(), "imageDialog");
    }









   private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<CategoriesResponse.CategoriesResponseData> allCategories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private int categoryID = -1;

     private void showFilterCategoryDialog() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_select_category, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog b = dialogBuilder.create();

        final ListView lvCategory = (ListView) dialogView.findViewById(R.id.dialog_lv_filter_category);
        ProgressBar mProgressBar = (ProgressBar) dialogView.findViewById(R.id.dialog_filter_category_progress);
        categoryList.clear();
        allCategories.clear();

        getAllCategoriesApi(mProgressBar, b);
        categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
        lvCategory.setAdapter(categoryAdapter);

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryID = allCategories.get(i).getCategoryId();
                et_category.setText(allCategories.get(i).getCategoryName());
                b.dismiss();
            }
        });

        b.show();
    }

    private void getAllCategoriesApi(final ProgressBar mProgressBar, final android.app.AlertDialog categoryDialog) {

        RetrofitClient.getClient().getAllCategoriesInFilter()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoriesResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e("response", "");
                        if (mProgressBar.getVisibility() == View.VISIBLE)
                            mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("response", "");
                        mProgressBar.setVisibility(View.GONE);
                        Utility.showSnackBar(getActivity(), "Please try again");
                        categoryDialog.dismiss();
                    }

                    @Override
                    public void onNext(CategoriesResponse categoriesResponse) {
                        Log.e("response", "" + categoriesResponse);
                        allCategories.addAll(categoriesResponse.getData());
                        for (int i = 0; i < categoriesResponse.getData().size(); i++) {
                            categoryList.add(categoriesResponse.getData().get(i).getCategoryName());
                        }
                        categoryAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }
*/

}
