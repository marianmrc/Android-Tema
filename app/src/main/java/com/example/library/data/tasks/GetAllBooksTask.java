package com.example.library.data.tasks;

import android.os.AsyncTask;

import com.example.library.data.BookDataBase;
import com.example.library.data.BookRepository;
import com.example.library.models.dbEntities.Book;

import java.util.List;

public class GetAllBooksTask extends AsyncTask<Void,Void, List<Book>> {

    private BookDataBase bookDataBase;
    private BookRepository.OnGetBooksListener listener;

    public GetAllBooksTask(BookDataBase bookDataBase,BookRepository.OnGetBooksListener listener){

        this.bookDataBase=bookDataBase;
        this.listener=listener;
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        return bookDataBase.bookDAO().getAll();
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        listener.onSuccess(books);
    }
}
