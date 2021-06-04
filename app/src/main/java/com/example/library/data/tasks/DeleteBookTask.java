package com.example.library.data.tasks;

import android.os.AsyncTask;

import com.example.library.data.BookDataBase;
import com.example.library.models.dbEntities.Book;

public class DeleteBookTask extends AsyncTask<Book,Void,Void>{


        private BookDataBase bookDataBase;
        public DeleteBookTask(BookDataBase toDoDataBase)
        {
            this.bookDataBase=toDoDataBase;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDataBase.bookDAO().delete(books[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

}
