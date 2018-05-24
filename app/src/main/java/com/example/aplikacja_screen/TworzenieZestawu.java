package com.example.aplikacja_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class TworzenieZestawu extends AppCompatActivity {

    private static final String TAG ="TworzenieZestawu";
    Button tworzenie_zestawu;
    EditText nazwa_zestawu;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tworzenie_zestawu);
        tworzenie_zestawu = (Button) findViewById(R.id.button2);
        nazwa_zestawu=(EditText)findViewById(R.id.editText2);
        db = new Database(getContentResolver());


        tworzenie_zestawu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nazwa_zestaw=nazwa_zestawu.getText().toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userID = prefs.getString("id", "0");
                //Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
                boolean czy_istnieje=false;
                Cursor cursor = db.getSets();
//                Cursor cursor2 = db.getUser(Integer.parseInt(userID));
//                while(cursor2.moveToNext()) {
//                    while (cursor.moveToNext()) {
//                        if(Integer.parseInt(userID)==cursor.getInt(1)) {
//                            if (nazwa_zestaw.equalsIgnoreCase(cursor.getString(2))) {
//                                czy_istnieje = true;
//                            }
//                            //Toast.makeText(getApplicationContext(), "id set: " + String.valueOf(cursor.getInt(0)), Toast.LENGTH_SHORT).show();
//                            //Toast.makeText(getApplicationContext(), "nazwa: " + cursor.getString(2), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }

                if(czy_istnieje==false && !nazwa_zestaw.equals(""))
                {
                    Uri uri=db.insertIntoSets(Integer.parseInt(userID),nazwa_zestaw);
                    //przejscie do listy moich zestawow
                    startActivity(new Intent(TworzenieZestawu.this, MojeZestawy.class));
                }else if(czy_istnieje==true){
                    Toast.makeText(getApplicationContext(),"Istnieje już zestaw o podanej nazwie.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Pozostawiono puste pole.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}