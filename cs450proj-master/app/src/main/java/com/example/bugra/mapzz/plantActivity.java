package com.example.bugra.mapzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class plantActivity extends AppCompatActivity {
    private TextView profiltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        profiltext = (TextView) findViewById(R.id.textView10);

        profiltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(plantActivity.this, layout.class);
                startActivity(i);
            }
        });
    }
}
