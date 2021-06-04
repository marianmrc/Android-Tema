package com.example.library.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.library.R;
import com.example.library.adapters.BookAdapter;
import com.example.library.data.BookRepository;
import com.example.library.helpers.UtilsValidators;
import com.example.library.interfaces.FragmentCommunication;
import com.example.library.interfaces.OnItemClickListener;
import com.example.library.models.BookElement;
import com.example.library.models.dbEntities.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditBooksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentCommunication fragmentCommunication;
    private ArrayList<BookElement> bookList = new ArrayList<>();
    private BookAdapter bookAdapter = new BookAdapter(bookList, new OnItemClickListener() {
        @Override
        public void onBookClick(BookElement bookElement) {
            fragmentCommunication.openSpecificBookFragment(bookElement);
        }

        @Override
        public void onDeleteClick(BookElement book) {

        }
    },
            new FragmentCommunication() {
                @Override
                public void openSpecificBookFragment(BookElement bookElement) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getView().getContext();
                    SpecificBookFragment specificBookFragment = new SpecificBookFragment(bookElement);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_edit_books, specificBookFragment).addToBackStack("null").commit();
                }
            });
    private BookRepository bookRepository = new BookRepository();
    private EditText authorEt;
    private EditText titleEt;
    private EditText descriptionEt;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditBooksFragment newInstance(String param1, String param2) {
        EditBooksFragment fragment = new EditBooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public void validateBookDetails() {
        if (getView() == null)
            return;

        String title = titleEt.getText().toString();
        String author = authorEt.getText().toString();
        String description = descriptionEt.getText().toString();

        if (!UtilsValidators.isValidTitle(title)) {
            titleEt.setError("Invalid title.");
            return;
        } else {
            titleEt.setError(null);
        }

        if (!UtilsValidators.isValidAuthor(author)) {
            authorEt.setError("Invalid author.");
            return;
        } else {
            authorEt.setError(null);
        }

        if (!UtilsValidators.isValidDescription(description)) {
            descriptionEt.setError("Invalid description.");
            return;
        } else {
            descriptionEt.setError(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_books, container, false);
        View view = inflater.inflate(R.layout.fragment_edit_books, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_books);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookAdapter);
        authorEt = view.findViewById(R.id.editable_author);
        titleEt = view.findViewById(R.id.editable_title);
        descriptionEt = view.findViewById(R.id.editable_description);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBooks();
        view.findViewById(R.id.btn_add_update).setOnClickListener(v -> {
            validateBookDetails();

            String title = titleEt.getText().toString();

            String author = authorEt.getText().toString();

            String description = descriptionEt.getText().toString();

  //          Book book = new Book(title, author, description);
            boolean flag=false;
            int index=-1;
            for (BookElement bookElement:bookList
            ) {
                if(bookElement.getTitle().equals(title)
                && bookElement.getAuthor().equals(author))
                {
                    flag=true;
                    index=bookList.indexOf(bookElement);
                }
            }
            if(flag)
            {
                Book book=new Book(bookList.get(index).getId(),bookList.get(index).getTitle(),bookList.get(index).getAuthor(),description);
                bookRepository.updateBook(book);
                bookList.get(index).setDescription(description);
                bookAdapter.notifyItemChanged(index);
            }
            else
            {
                Book book=new Book(title,author,description);
                bookRepository.insertBook(book);
                bookList.add(book.convert());
            }

            bookAdapter.notifyItemChanged(bookList.size() - 1);
            getBooks();
        });

    }

    public void getBooks() {
        bookRepository.getAllBooks(booksResult -> {
            bookList.clear();
            for (Book book : booksResult
            ) {
                bookList.add(book.convert());
            }
            bookAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FragmentCommunication) {
            fragmentCommunication = (FragmentCommunication) context;
        }


    }
}
