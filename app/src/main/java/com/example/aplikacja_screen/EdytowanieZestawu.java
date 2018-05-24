package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

import java.util.ArrayList;

public class EdytowanieZestawu extends AppCompatActivity {

    ArrayList<String>pl;
    ArrayList<String>en;
    String polskie, angielskie;
    ArrayList<String> zaznaczone_el = new ArrayList<>();
    ListView lv;
    Button edytuj_fiszke;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytowanie_zestawu);

        edytuj_fiszke = (Button) findViewById(R.id.edytuj_fiszke);
        pl=new ArrayList<String>();
        en=new ArrayList<String>();
        db=new Database(getContentResolver());
        lv = (ListView) findViewById(R.id.listView);
        lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);


        //ID aktualnego zestawu
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

        //polaczenie danych (słówka pl+en) z dwóch tablic
        String[] polaczenie = new String[pl.size()];
        for (int i = 0; i < pl.size(); i++) {
            polaczenie[i] = pl.get(i) + " - " + en.get(i);
        }

        //arrayList w adapterze i wyswietlanie w listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_checkbox, R.id.checkedTextView, polaczenie);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 s= (String) lv.getItemAtPosition(i); //tutaj dostajemy całą linijkę
                polskie = pl.get(i);
                angielskie = en.get(i);

                Cursor cursor1=db.getFlashcards();
                while(cursor1.moveToNext()){
                    String nazwa_pl=cursor1.getString(2);
                    if(polskie.equals(nazwa_pl))
                    {
                        int id=cursor1.getInt(0);
                        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor e = sp.edit();
                        e.putInt("idFiszki",id);
                        e.commit();
                    }
                }
            }
        });

        edytuj_fiszke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EdytowanieZestawu.this, EdytowanieFiszki.class);
                intent.putExtra("pl", polskie);
                intent.putExtra("en", angielskie);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(EdytowanieZestawu.this, MojeZestawy.class));
    }
}
