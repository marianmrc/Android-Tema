package com.example.library.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.R;
import com.example.library.data.BookRepository;
import com.example.library.fragments.EditBooksFragment;
import com.example.library.interfaces.FragmentCommunication;
import com.example.library.interfaces.OnItemClickListener;
import com.example.library.models.BookElement;
import com.example.library.models.dbEntities.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    ArrayList<BookElement> bookList;
    OnItemClickListener onItemClickListener;
    FragmentCommunication fragmentCommunication;
    public BookAdapter(ArrayList<BookElement>bookList,OnItemClickListener onItemClickListener,FragmentCommunication fragmentCommunication)
    {
        this.bookList=bookList;
        this.onItemClickListener=onItemClickListener;
        this.fragmentCommunication=fragmentCommunication;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(view);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        BookElement bookElement = bookList.get(position);
        holder.bind(bookElement);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView description;
        private View view;
        private Button deleteBtn;

        public BookViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            author=view.findViewById(R.id.tv_author);
            description = view.findViewById(R.id.tv_description);
            deleteBtn=view.findViewById(R.id.btn_delete);
            this.view=view;
        }

        public void bind(BookElement bookElement) {
            title.setText(bookElement.getTitle());
            author.setText(bookElement.getAuthor());
            description.setText((bookElement.getDescription()));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onBookClick(bookElement);
                    notifyItemChanged(getAdapterPosition());
                }
            });

            deleteBtn.setOnClickListener(v -> {
                BookRepository bookRepository=new BookRepository();
                Book book=new Book(bookElement.getId(),bookElement.getTitle(),bookElement.getAuthor(),bookElement.getDescription());
                bookRepository.deleteBook(book);
                onItemClickListener.onDeleteClick(bookElement);
                bookList.remove(getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            });
        }

    }
}
