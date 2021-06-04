package com.example.library.interfaces;

import com.example.library.models.BookElement;
import com.example.library.models.dbEntities.Book;

public interface FragmentCommunication {
    public void openSpecificBookFragment(BookElement bookElement);
}
