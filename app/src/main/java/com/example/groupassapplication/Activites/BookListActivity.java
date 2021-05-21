package com.example.groupassapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.groupassapplication.Adapters.Adapter_BookList;
import com.example.groupassapplication.Models.Book;
import com.example.groupassapplication.MySingleton;
import com.example.groupassapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    private List<Book> books = new ArrayList<>();
    String category = "";
    public EditText searchView;
    Adapter_BookList adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        setupViews();
        loadData();
//
//        searchView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });


    }

    private void filter(String text) {

        ArrayList<Book> filterList = new ArrayList<>();
        for (Book item : books)
        {
            if (item.getBookName().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }

    private void setupViews(){
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
    }

    private void loadData() {

        category = getIntent().getStringExtra("Category");
        String url = "http://192.168.1.114:84/Mayar/getBookList.php?cat="+category;
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String bookName = jsonObject.getString("bookName");
                                String bookPhoto = jsonObject.getString("bookPhoto");
                                String bookCategory = jsonObject.getString("bookCategory");
                                String author = jsonObject.getString("author");
                                String publisher = jsonObject.getString("publisher");
                                String originalLanguage = jsonObject.getString("originalLanguage");
                                String releaseDate = jsonObject.getString("releaseDate");

                                books.add(new Book(id, bookName, bookPhoto ,bookCategory,author,publisher,originalLanguage,releaseDate));

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(BookListActivity.this));
                            adapter = new Adapter_BookList(BookListActivity.this, books);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jor);
    }

    public void insert_btn_OnClick(View view){
        Intent intent = new Intent(this,InsertBookActivity.class);
        intent.putExtra("Category",category);
        startActivity(intent);
    }
}