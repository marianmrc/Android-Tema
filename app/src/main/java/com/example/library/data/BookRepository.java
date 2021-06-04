package com.example.library.data;

import com.example.library.ApplicationController;
import com.example.library.data.tasks.DeleteBookTask;
import com.example.library.data.tasks.GetAllBooksTask;
import com.example.library.data.tasks.InsertBookTask;
import com.example.library.data.tasks.UpdateBookTask;
import com.example.library.models.dbEntities.Book;

import java.util.List;

public class BookRepository {

    public static interface OnGetBooksListener {

        void onSuccess(List<Book> items);
    }

    private BookDataBase bookDataBase;

    public BookRepository() {
        bookDataBase = ApplicationController.getBookDataBase();
    }

    public void insertBook(Book book) {
        new InsertBookTask(bookDataBase).execute(book);
    }

    public void getAllBooks(OnGetBooksListener listener) {
        new GetAllBooksTask(bookDataBase, listener).execute();
    }


    public void updateBook(Book book) {
        new UpdateBookTask(bookDataBase).execute(book);
    }

    public void deleteBook(Book book) {
        new DeleteBookTask(bookDataBase).execute(book);
    }
}
