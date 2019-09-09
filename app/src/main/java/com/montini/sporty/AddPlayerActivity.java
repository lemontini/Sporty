package com.montini.sporty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.montini.sporty.model.Player;
import com.montini.sporty.util.ChangeWatcher;

import com.squareup.picasso.Picasso;

import static com.montini.sporty.AddLocationActivity.IMAGE_REQUEST_CODE;
import static com.montini.sporty.util.WorkFlow.getPathFromContentURI;
import static com.montini.sporty.util.WorkFlow.requestPermission;

public class AddPlayerActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "AddPlayerActivity";

    // vars
    private EditText name;
    private ImageButton btnSave;
    private ImageView logo;
    private Player cPlayer;
    private int position;
    private ChangeWatcher changeWatcher = new ChangeWatcher();

    // constructors

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        logo = findViewById(R.id.choose_logo_Image);
        name = findViewById(R.id.editText1);
        name.addTextChangedListener(changeWatcher.getInstance());
        btnSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        cPlayer = intent.getParcelableExtra("player");
        position = intent.getIntExtra("position", -1);

        if (cPlayer != null) {
            // Picasso.with(this).setLoggingEnabled(true);
            Picasso.get().load(cPlayer.getLogo()).error(R.drawable.placeholder_camera).resize(480, 480).centerInside().into(logo);
            // Log.d(TAG, "onCreate: path of image: " + cPlayer.getLogo());
            name.setText(cPlayer.getName());
            Toast.makeText(this, cPlayer.getLogo().toString(), Toast.LENGTH_LONG).show();
        } else cPlayer = new Player();
    }

    public void logo_Click(View v) {
        //Create an Intent with action as ACTION_PICK
        requestPermission(this);
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
            cPlayer.setLogo(Uri.parse(picturePath));
            // logo.setImageURI(selectedImage);
            // // writeUsingOutputStream(selectedImage.toString());
            Picasso.get().load(picturePath).resize(480, 480).centerInside().into(logo);
            changeWatcher.setContentChanged();
        }
    }

}
