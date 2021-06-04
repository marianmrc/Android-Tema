package com.example.library.interfaces;

import com.example.library.models.BookElement;
import com.example.library.models.dbEntities.Book;

public interface OnItemClickListener {
    public void onBookClick(BookElement book);

    public void onDeleteClick(BookElement book);
}
