package com.montini.sporty;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.montini.sporty.model.Location;
import com.montini.sporty.util.ChangeWatcher;
import com.squareup.picasso.Picasso;

public class AddLocationActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "AddLocationActivity";
    static final int IMAGE_REQUEST_CODE = 0201;

    // vars
    private EditText name, address, maxCourts;
    private ImageButton btnSave;
    private ImageView logo;
    private Location cLocation;
    private int position;
    private ChangeWatcher changeWatcher = new ChangeWatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        logo = findViewById(R.id.choose_logo_Image);
        name = findViewById(R.id.editText1);
        name.addTextChangedListener(changeWatcher.getInstance());
        address = findViewById(R.id.editText2);
        address.addTextChangedListener(changeWatcher.getInstance());
        maxCourts = findViewById(R.id.editText3);
        maxCourts.addTextChangedListener(changeWatcher.getInstance());
        btnSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        cLocation = intent.getParcelableExtra("location");
        position = intent.getIntExtra("position", -1);

        if (cLocation != null) {
            // Picasso.with(this).setLoggingEnabled(true);
            Picasso.with(this).load(cLocation.getLogo()).error(R.drawable.placeholder_camera).resize(480, 480).centerInside().into(logo);
            // Log.d(TAG, "onCreate: path of image: " + cLocation.getLogo());
            name.setText(cLocation.getName());
            address.setText(cLocation.getAddress());
            maxCourts.setText(String.valueOf(cLocation.getMaxCourts()));
            Toast.makeText(this, cLocation.getLogo().toString(), Toast.LENGTH_LONG).show();
        } else cLocation = new Location();
    }

    public void btnSave_Click(View v) {
        Intent returnIntent = new Intent();
        if (changeWatcher.isContentChanged()) {

            cLocation.setName(String.valueOf(name.getText()));
            cLocation.setAddress(String.valueOf(address.getText()));
            cLocation.setMaxCourts(Integer.parseInt(maxCourts.getText().toString()));

            returnIntent.putExtra("location", (Parcelable) cLocation);
            returnIntent.putExtra("position", position);
            setResult(RESULT_OK, returnIntent);

        } else setResult(RESULT_CANCELED, returnIntent);

        finish();
    }

    public void logo_Click(View v) {
        //Create an Intent with action as ACTION_PICK
        requestPermission();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Sets the type as image/*. This ensures only components of type image are selected
        // intent.setType("image/*");
        // We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == IMAGE_REQUEST_CODE) && (resultCode == RESULT_OK)) {
            Uri selectedImage = data.getData();
            String picturePath = "file://" + getPathFromContentURI(selectedImage);
            // Log.d(TAG, "onActivityResult: THE IMAGE content: " + selectedImage.toString() + "\n PATH: " + getPathFromContentURI(selectedImage) + "\nNEW: " + picturePath);
            // logo.setTag(picturePath);
            cLocation.setLogo(Uri.parse(picturePath));
            // logo.setImageURI(selectedImage);
            // // writeUsingOutputStream(selectedImage.toString());
            Picasso.with(this).load(picturePath).resize(480, 480).centerInside().into(logo);
            changeWatcher.setContentChanged();
        }
    }

    // void saveLogoToCache(String logoFileName) {
    //         FileWriter cache = new FileWriter("cache/" + logoFileName);
    // }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    public String getPathFromContentURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    // public void writeUsingOutputStream(String data) {
    //     OutputStream os = null;
    //     try {
    //         os = new FileOutputStream(new File(getFileName(data)));
    //         os.write(data.getBytes(), 0, data.length());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }finally{
    //         try {
    //             os.close();
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

}
