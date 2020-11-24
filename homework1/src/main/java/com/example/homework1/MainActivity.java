package com.example.homework1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create toast after pressing the info button
        Button infoButton = findViewById(R.id.infoButton);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "EN, RU, UA, PL", Toast.LENGTH_SHORT).show();
            }
        };

        infoButton.setOnClickListener(onClick);
    }

}