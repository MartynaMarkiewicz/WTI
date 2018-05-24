package com.example.aplikacja_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

public class MainActivity extends AppCompatActivity {
    public static final String PATH = "/sdcard/Download/";

    CardView zaloguj;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(getContentResolver());
        zaloguj = (CardView) findViewById(R.id.cardView);


        zaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BocznyPasekLewy.class);
                //startActivity(new Intent(MainActivity.this, MojeZestawy.class));
                startActivity(intent);
            }
        });
    }
}
