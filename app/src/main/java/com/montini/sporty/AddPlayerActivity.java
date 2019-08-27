package com.montini.sporty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.montini.sporty.model.Location;
import com.montini.sporty.model.Player;
import com.montini.sporty.util.ChangeWatcher;
import com.squareup.picasso.Picasso;

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
            Picasso.with(this).load(cPlayer.getLogo()).error(R.drawable.placeholder_camera).resize(480, 480).centerInside().into(logo);
            // Log.d(TAG, "onCreate: path of image: " + cPlayer.getLogo());
            name.setText(cPlayer.getName());
            Toast.makeText(this, cPlayer.getLogo().toString(), Toast.LENGTH_LONG).show();
        } else cPlayer = new Player();
    }


}
