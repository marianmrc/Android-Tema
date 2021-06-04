package com.example.library;

import android.app.Application;

import androidx.room.Room;

import com.example.library.data.BookDataBase;

public class ApplicationController extends Application {

    public static ApplicationController getInstance(){
        return instance;
    }

    private static BookDataBase bookDataBase;
    private static ApplicationController instance;
    private final String bookDataBaseName="BookDB";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        setUpDataBase();
    }

    void setUpDataBase(){
        bookDataBase= Room.databaseBuilder(
                getApplicationContext(),
                BookDataBase.class,
                bookDataBaseName)
                .fallbackToDestructiveMigration()
                .build();
    }

    public static BookDataBase getBookDataBase() {
        return bookDataBase;
    }

}
