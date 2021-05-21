package com.example.groupassapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupassapplication.Activites.BookListActivity;
import com.example.groupassapplication.Activites.DetailesBookActivity;
import com.example.groupassapplication.Activites.EditBookActivity;
import com.example.groupassapplication.Models.Book;
import com.example.groupassapplication.Models.Category;
import com.example.groupassapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_BookList extends RecyclerView.Adapter<Adapter_BookList.ViewHolder> {

    private Context context;
    private List<Book> books;

    Button up_date_btn ;

    public Adapter_BookList(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    public Adapter_BookList() {}


    @NonNull
    @Override
    public Adapter_BookList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_BookList.ViewHolder holder, int position) {
        Book bookList = books.get(position);
        CardView cardView = holder.cardView;

        ImageView image = cardView.findViewById(R.id.imageView);
        Picasso.get().load(bookList.getBookPhoto()).into(image);

        TextView txt = cardView.findViewById(R.id.text);
        txt.setText(bookList.getBookName());

        up_date_btn=cardView.findViewById(R.id.update_btn);

        up_date_btn.setOnClickListener(new View.OnClickListener()
                                       {

                                           @Override
                                           public void onClick(View v) {
                                               Intent intent =new Intent(context, EditBookActivity.class);
                                               String ids = bookList.getId()+"";
                                               intent.putExtra("id",ids);
                                               intent.putExtra("bookName",bookList.getBookName());
                                               intent.putExtra("bookPhoto",bookList.getBookPhoto());
                                               intent.putExtra("bookCategory",bookList.getBookCategory());
                                               intent.putExtra("author",bookList.getAuthor());
                                               intent.putExtra("publisher",bookList.getPublisher());
                                               intent.putExtra("originalLanguage",bookList.getOriginalLanguage());
                                               intent.putExtra("releaseDate",bookList.getReleaseDate());
                                               context.startActivity(intent);
                                           }
                                       }
        );

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context, DetailesBookActivity.class);
                intent.putExtra("bookName",bookList.getBookName());
                intent.putExtra("bookPhoto",bookList.getBookPhoto());
                intent.putExtra("bookCategory",bookList.getBookCategory());
                intent.putExtra("author",bookList.getAuthor());
                intent.putExtra("publisher",bookList.getPublisher());
                intent.putExtra("originalLanguage",bookList.getOriginalLanguage());
                intent.putExtra("releaseDate",bookList.getReleaseDate());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filteredList(ArrayList<Book> filterList) {

        books = filterList;
        notifyDataSetChanged();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
