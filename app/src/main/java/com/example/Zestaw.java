package com.example;

import java.io.Serializable;

public class Zestaw implements Serializable{

    private int id;
    private int id_uzytkownika;
    private String nazwa;
    private int ilość;

    public Zestaw(int id, int id_uzytkownika,String nazwa, int ilość) {
        this.id=id;
        this.nazwa = nazwa;
        this.id_uzytkownika=id_uzytkownika;
        this.ilość=ilość;
    }
    public String getNazwa() {
        return nazwa;
    }

    public int getIdUzytkownika() { return id_uzytkownika; }

    public int getId() { return id; }

    public int getIlość() { return ilość; }

}
