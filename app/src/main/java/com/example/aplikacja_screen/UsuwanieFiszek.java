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
import android.widget.ListView;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

import java.util.ArrayList;

public class UsuwanieFiszek extends AppCompatActivity {

    ArrayList<String>pl;
    ArrayList<String>en;
    String polskie, angielskie;
//    int liczba; //indeks edytowanej fiszki
    ArrayList<String> zaznaczone_el = new ArrayList<>();
    ListView lv1;
    Button usun_fiszke;
    Database db;
    int idFiszki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuwanie_fiszek);

        usun_fiszke = (Button) findViewById(R.id.usun_fiszke);
        pl=new ArrayList<String>();
        en=new ArrayList<String>();
        db=new Database(getContentResolver());
        lv1 = (ListView) findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_SINGLE);

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
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 s= (String) lv.getItemAtPosition(i); //tutaj dostajemy całą linijkę
                polskie = pl.get(i);
                angielskie = en.get(i);
                //id fiszki
                Cursor cursor1=db.getFlashcards();
                while(cursor1.moveToNext()){
                    String nazwa_pl=cursor1.getString(2);
                    if(polskie.equals(nazwa_pl))
                    {
                        idFiszki=cursor1.getInt(0);
                    }
                }
            }
        });

        usun_fiszke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //usuwanie fiszki z bazy
                db.deleteFromFlashcards(idFiszki);
                startActivity(new Intent(UsuwanieFiszek.this,UsuwanieFiszek.class));
            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(UsuwanieFiszek.this,MojeZestawy.class));
    }
}
