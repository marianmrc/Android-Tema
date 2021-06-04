package com.example.library.data.tasks;

import android.os.AsyncTask;

import com.example.library.data.BookDataBase;
import com.example.library.models.dbEntities.Book;

public class InsertBookTask extends AsyncTask<Book,Void,Void> {

    private BookDataBase bookDataBase;


    public InsertBookTask(BookDataBase bookDataBase){
        this.bookDataBase=bookDataBase;
    }

    @Override
    protected Void doInBackground(Book... books) {
        bookDataBase.bookDAO().insertBook(books[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
