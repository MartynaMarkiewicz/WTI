package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

public class EdytowanieFiszki extends AppCompatActivity {

    EditText pl;
    EditText en;
    Button edytuj;
    Database db;
    int id_zestawu;
    int id_fiszki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytowanie_fiszki);

        pl = (EditText) findViewById(R.id.textView19);
        en = (EditText) findViewById(R.id.editText20);
        edytuj = (Button) findViewById(R.id.button11);
        db=new Database(getContentResolver());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String p = extras.getString("pl");
            pl.setText(p);
            String e = extras.getString("en");
            en.setText(e);
        }

        SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id_zestawu=p.getInt("ID",0);
        id_fiszki=p.getInt("idFiszki",0);

        edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update danych w bazie
                String zmienione_pl=pl.getText().toString();
                String zmienione_en=en.getText().toString();
                db.updateFlashcards(id_fiszki,id_zestawu,zmienione_pl,zmienione_en);

                startActivity(new Intent(EdytowanieFiszki.this, EdytowanieZestawu.class));
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(EdytowanieFiszki.this, MojeZestawy.class));
    }
}
