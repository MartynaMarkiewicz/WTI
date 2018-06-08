package com.example.aplikacja_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Database.Database;
import com.example.m.aplikacja_screen.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tlumaczenie extends AppCompatActivity {
    Button sprawdz_poprawnosc, dalej, sprawdz;
    EditText en;
    TextView pl;

    ArrayList<String> polskie;
    ArrayList<String> angielskie;

    Database db;
    int i=0;

    Handler h = new Handler();
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlumaczenie);

        //REKLAMY
//        AdLoader adLoader = new AdLoader.Builder(getApplicationContext(), "ca-app-pub-3940256099942544/2247696110")
//                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//                    @Override
//                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                        // Show the ad.
//                    }
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        // Handle the failure by logging, altering the UI, and so on.
//                    }
//                })
//                .withNativeAdOptions(new NativeAdOptions.Builder()
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())
//                .build();
//
//        if (adLoader.isLoading()) {
//            // The AdLoader is still loading ads.
//            // Expect more adLoaded or onAdFailedToLoad callbacks.
//        } else {
//            // The AdLoader has finished loading ads.
//        }
//
//        adLoader.loadAd(new AdRequest.Builder().build());
        //REKLAMY



        sprawdz_poprawnosc = (Button) findViewById(R.id.button17);
        dalej = (Button) findViewById(R.id.button20);
        sprawdz = (Button) findViewById(R.id.button38);
        pl = (TextView) findViewById(R.id.textView21);
        en = (EditText) findViewById(R.id.textView22);
        polskie = new ArrayList<String>();
        angielskie = new ArrayList<String>();
        db = new Database(getContentResolver());

        cursor = db.getFlashcards();

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"Brak słówek",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                polskie.add(cursor.getString(2));
                angielskie.add(cursor.getString(3));
            }

            pl.setText(polskie.get(0));
            sprawdz_poprawnosc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (en.getText().toString().equals(angielskie.get(i).toString())) {

                        sprawdz.setVisibility(View.INVISIBLE);
                        sprawdz_poprawnosc.setText("Poprawna odpowiedź");
                        sprawdz_poprawnosc.setBackgroundColor(Color.parseColor("#38ea3e"));

                        sprawdz_poprawnosc.setClickable(false);
                        dalej.setVisibility(View.VISIBLE);
                    } else {
                        sprawdz_poprawnosc.setText("Niepoprawna odpowiedź");
                        sprawdz_poprawnosc.setBackgroundColor(Color.parseColor("#d11f34"));

                        sprawdz.setVisibility(View.VISIBLE);
                        sprawdz_poprawnosc.setClickable(false);
                        sprawdz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog alertDialog = new AlertDialog.Builder(Tlumaczenie.this).create();
                                alertDialog.setTitle("Poprawna odpowiedz");
                                alertDialog.setMessage(polskie.get(i) + " - " + angielskie.get(i));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                                sprawdz_poprawnosc.setText("Sprawdź poprawność");
                                sprawdz_poprawnosc.setBackgroundColor(Color.parseColor("#e6e1e1"));
                            }
                        });

                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sprawdz_poprawnosc.setText("Sprawdź poprawność");
                                //sprawdz.setVisibility(View.VISIBLE);
                                sprawdz_poprawnosc.setVisibility(View.VISIBLE);
                                sprawdz_poprawnosc.setClickable(true);
                                sprawdz_poprawnosc.setBackgroundColor(Color.parseColor("#e6e1e1"));
                            }
                        }, 3000);
                    }
                }
            });

            dalej.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 4) {
                        startActivity(new Intent(Tlumaczenie.this, BocznyPasekLewy.class));
                    }
                    en.setText("");
                    sprawdz_poprawnosc.setClickable(true);
                    sprawdz_poprawnosc.setText("Sprawdź poprawność");
                    sprawdz_poprawnosc.setBackgroundColor(Color.parseColor("#e6e1e1"));
                    i++;

                    dalej.setVisibility(View.INVISIBLE);
                    if (i <= 4) {
                        wczytaj_slowa(i);
                    }
                    if (i == 4) {
                        dalej.setText("Zakończ lekcje");
                    }
                }
            });
        }
    }

    public void wczytaj_slowa(int index)
    {
        pl.setText(polskie.get(index));
    }

    public void onBackPressed() {
        startActivity(new Intent(Tlumaczenie.this, BocznyPasekLewy.class));
    }
}
