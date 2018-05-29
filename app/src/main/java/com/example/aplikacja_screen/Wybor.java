package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
public class Wybor extends AppCompatActivity {
    Button b1, b2, b3, b4, nastepne_slowo;
    Button t1;
    Database db;
    Cursor cursor;
    int idKategorii;
    ArrayList<String> pl;
    ArrayList<String> en;
    Random r;
    int ilosc_slow;
    int random_pl;
    int random_en1;
    int random_en2;
    int random_en3;
    int random_en4;
    int index_pl;
    int index_en;
    ArrayList<Integer> randomowe_ustawienie_en;
    int ilosc_iteracji=0;
    int id_typu_zadania;
    int id_zadania;
    int poprawne_odp=0;
    int bledne_odp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor);

        b1 = (Button) findViewById(R.id.button40);
        b2 = (Button) findViewById(R.id.button41);
        b3 = (Button) findViewById(R.id.button42);
        b4 = (Button) findViewById(R.id.button43);
        t1 = (Button) findViewById(R.id.button14);
        nastepne_slowo = (Button) findViewById(R.id.button39);

        db = new Database(getContentResolver());

        pl = new ArrayList<String>();
        en = new ArrayList<String>();

        cursor = db.getFlashcards();
        if(cursor.getCount()<4){
            Toast.makeText(getApplicationContext(),"Brak słówek",Toast.LENGTH_SHORT).show();
        }
        while(cursor.moveToNext()){
            pl.add(cursor.getString(2));
            en.add(cursor.getString(3));
        }
        randomowe_ustawienie_en = new ArrayList<Integer>();

        r = new Random();

        ilosc_slow = pl.size();
        //ustawianie na początku
        random_pl = r.nextInt(ilosc_slow)+1;
        random_en1 = r.nextInt(ilosc_slow)+1;
        random_en2 = r.nextInt(ilosc_slow)+1;
        random_en3 = r.nextInt(ilosc_slow)+1;
        random_en4 = random_pl;


        t1.setText(pl.get(random_pl).toString());
        t1.setTextSize(15);
        b1.setText(en.get(random_en4).toString());
        b2.setText(en.get(random_en1).toString());
        b3.setText(en.get(random_en2).toString());
        b4.setText(en.get(random_en3).toString());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blokowanie przyciskow
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);

                index_en=en.indexOf(b1.getText().toString()); //liczony od zera
                index_pl=pl.indexOf(t1.getText().toString());
                if(index_en==index_pl) {
                    b1.setBackgroundResource(R.drawable.zielony_gradient);
                    poprawne_odp++;
                }else{
                    bledne_odp++;
                    //co zrobic w przypadku zlej odpowiedzi
                    b1.setBackgroundResource(R.drawable.button_border_red);
                    int index_btn2=en.indexOf(b2.getText().toString());
                    int index_btn3=en.indexOf(b3.getText().toString());
                    int index_btn4=en.indexOf(b4.getText().toString());
                    if(index_pl==index_btn2)
                    {
                        b2.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn3){
                        b3.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn4){
                        b4.setBackgroundResource(R.drawable.zielony_gradient);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blokowanie przyciskow
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                index_en=en.indexOf(b2.getText().toString()); //liczony od zera
                index_pl=pl.indexOf(t1.getText().toString());
                if(index_en==index_pl) {
                    b2.setBackgroundResource(R.drawable.zielony_gradient);
                    poprawne_odp++;
                }else{
                    bledne_odp++;
                    b2.setBackgroundResource(R.drawable.button_border_red);
                    int index_btn1=en.indexOf(b1.getText().toString());
                    int index_btn3=en.indexOf(b3.getText().toString());
                    int index_btn4=en.indexOf(b4.getText().toString());
                    if(index_pl==index_btn1)
                    {
                        b1.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn3){
                        b3.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn4){
                        b4.setBackgroundResource(R.drawable.zielony_gradient);
                    }
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blokowanie przyciskow
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                index_en=en.indexOf(b3.getText().toString()); //liczony od zera
                index_pl=pl.indexOf(t1.getText().toString());
                if(index_en==index_pl) {
                    b3.setBackgroundResource(R.drawable.zielony_gradient);
                    poprawne_odp++;
                }else{
                    bledne_odp++;
                    b3.setBackgroundResource(R.drawable.button_border_red);
                    int index_btn2=en.indexOf(b2.getText().toString());
                    int index_btn1=en.indexOf(b1.getText().toString());
                    int index_btn4=en.indexOf(b4.getText().toString());
                    if(index_pl==index_btn2)
                    {
                        b2.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn1){
                        b1.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn4){
                        b4.setBackgroundResource(R.drawable.zielony_gradient);
                    }
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blokowanie przyciskow
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                index_en=en.indexOf(b4.getText().toString()); //liczony od zera
                index_pl=pl.indexOf(t1.getText().toString());
                if(index_en==index_pl) {
                    poprawne_odp++;
                    b4.setBackgroundResource(R.drawable.zielony_gradient);
                }else{
                    bledne_odp++;
                    b4.setBackgroundResource(R.drawable.button_border_red);
                    int index_btn2=en.indexOf(b2.getText().toString());
                    int index_btn3=en.indexOf(b3.getText().toString());
                    int index_btn1=en.indexOf(b1.getText().toString());
                    if(index_pl==index_btn1)
                    {
                        b1.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn3){
                        b3.setBackgroundResource(R.drawable.zielony_gradient);
                    }else if(index_pl==index_btn2){
                        b2.setBackgroundResource(R.drawable.zielony_gradient);
                    }
                }
            }
        });
        nastepne_slowo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blokowanie przyciskow
                b1.setClickable(true);
                b2.setClickable(true);
                b3.setClickable(true);
                b4.setClickable(true);

                if(ilosc_iteracji==13)
                {
                    nastepne_slowo.setText("Zakończ lekcję");
                }
                if(ilosc_iteracji==14)
                {
                    startActivity(new Intent(Wybor.this,BocznyPasekLewy.class));
                }
                if(ilosc_iteracji<14) {
                    random_pl = r.nextInt(ilosc_slow);
                    random_en1 = r.nextInt(ilosc_slow);
                    if (random_en1 == random_pl) {
                        do {
                            random_en1 = r.nextInt(ilosc_slow);
                        } while (random_pl == random_en1);
                    }
                    random_en2 = r.nextInt(ilosc_slow);
                    if (random_en2 == random_pl | random_en2 == random_en1) {
                        do {
                            random_en2 = r.nextInt(ilosc_slow);
                        } while (random_pl == random_en2 & random_en2 == random_en1);
                    }
                    random_en3 = r.nextInt(ilosc_slow);
                    if (random_en3 == random_pl | random_en3 == random_en1 | random_en3 == random_en2) {
                        do {
                            random_en3 = r.nextInt(ilosc_slow);
                        }
                        while (random_pl == random_en3 & random_en3 == random_en1 & random_en3 == random_en2);
                    }
                    random_en4 = random_pl;

                    randomowe_ustawienie_en.add(random_en1);
                    randomowe_ustawienie_en.add(random_en2);
                    randomowe_ustawienie_en.add(random_en3);
                    randomowe_ustawienie_en.add(random_en4);
                    int a, b, c, d;
                    a = r.nextInt(4);
                    b = r.nextInt(4);
                    if (b == a) {
                        do {
                            b = r.nextInt(4);
                        } while (b == a);
                    }
                    c = r.nextInt(4);
                    if (c == b | c == a) {
                        do {
                            c = r.nextInt(4);
                        } while (c == a | c == b);
                    }
                    d = r.nextInt(4);
                    if (d == c | d == b | d == a) {
                        do {
                            d = r.nextInt(4);
                        } while (d == a | d == b | d == c);
                    }
                    //czyszczenie buttonow background
                    b1.setBackgroundColor(Color.parseColor("#e6dcdc"));
                    b2.setBackgroundColor(Color.parseColor("#e6dcdc"));
                    b3.setBackgroundColor(Color.parseColor("#e6dcdc"));
                    b4.setBackgroundColor(Color.parseColor("#e6dcdc"));
                    t1.setText(pl.get(random_pl).toString());
                    b1.setText(en.get(randomowe_ustawienie_en.get(b)).toString());
                    b2.setText(en.get(randomowe_ustawienie_en.get(c)).toString());
                    b3.setText(en.get(randomowe_ustawienie_en.get(d)).toString());
                    b4.setText(en.get(randomowe_ustawienie_en.get(a)).toString());
                    randomowe_ustawienie_en.clear();
                    ilosc_iteracji++;
                }
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(Wybor.this,BocznyPasekLewy.class));
    }


}
