package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NaukaFiszki extends AppCompatActivity {

    Button fiszka;
    Boolean odwroc = false;
    ImageButton next;
    int i = 0;
    int kolor_i=0;
    ArrayList<String>kolory;
    ArrayList<String> pl;
    ArrayList<String>en;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauka_fiszki);
        db=new Database(getContentResolver());
        pl=new ArrayList<String>();
        en=new ArrayList<String>();
        fiszka = (Button) findViewById(R.id.button7);
        next = (ImageButton) findViewById(R.id.imageButton);
        kolory = new ArrayList<String>();
        kolory.add("#fcb8b8");
        kolory.add("#ffe4b7");
        kolory.add("#fdffc9");
        kolory.add("#c9fff9");
        kolory.add("#c1ffeb");
        kolory.add("#cfffc9");
        kolory.add("#d5c9ff");
        kolory.add("#ffc9f5");

        //pobranie ID aktualnego zestawu
        SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id_zestawu=p.getInt("ID",0);
        //pobranie fiszek z bazy dla konkretnego zestawu
        Cursor cursor = db.getFlashcards();
        while(cursor.moveToNext())
        {
            if(id_zestawu==cursor.getInt(1))
            {
                pl.add(cursor.getString(2));
                en.add(cursor.getString(3));
            }
        }

        fiszka.setText(pl.get(0)); //pierwszy element z arrayList
        fiszka.setBackgroundColor(Color.parseColor(kolory.get(0)));
        fiszka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (odwroc == true) {
                    fiszka.setText(pl.get(i));
                    fiszka.setBackgroundColor(Color.parseColor(kolory.get(kolor_i)));
                    odwroc = false;
                } else {
                    fiszka.setText(en.get(i));
                    fiszka.setBackgroundColor(Color.parseColor(kolory.get(kolor_i)));
                    odwroc = true;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kolejna fiszka
                if (i == pl.size() - 1) {
                    i = -1;
                }
                fiszka.setText(pl.get(i + 1));
                odwroc = false;
                i++;
                kolor_i++;
                if(kolor_i==kolory.size()){
                    kolor_i=0;
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(NaukaFiszki.this, BocznyPasekLewy.class));
    }
}
