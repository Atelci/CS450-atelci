package com.example.bugra.mapzz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bugra.mapzz.R;

public class PlantActivity extends AppCompatActivity {
    private TextView profiltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_plant);

        profiltext = findViewById(R.id.textView10);

        profiltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlantActivity.this, layout.class);
                startActivity(i);
            }
        });
    }
}
