package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

public class DodanieFiszki extends AppCompatActivity {

    Button dodaj_fiszke;
    EditText pl;
    EditText en;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodanie_fiszki);
        db=new Database(getContentResolver());

        dodaj_fiszke = (Button) findViewById(R.id.button6);
        pl = (EditText) findViewById(R.id.editText9);
        en = (EditText) findViewById(R.id.editText10);
        dodaj_fiszke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pobranie id aktualnego zestawu
                SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id_zestawu=p.getInt("ID",0);
                //dodanie fiszki do bazy
                if(pl.getText().toString().equals("")||en.getText().toString().equals("")) {
                   Toast.makeText(getApplicationContext(),"Pozostawiono puste pola",Toast.LENGTH_SHORT).show();
                }else{
                    Uri uri = db.insertIntoFlashCards(id_zestawu, pl.getText().toString(), en.getText().toString());
                }
                pl.setText("");
                en.setText("");
            }
        });
    }
    public void onBackPressed() {
        startActivity(new Intent(DodanieFiszki.this, MojeZestawy.class));
    }
}
