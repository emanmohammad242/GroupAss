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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.groupassapplication.Adapters.Adapter_BookList;
import com.example.groupassapplication.Adapters.Adapter_CategoryList;
import com.example.groupassapplication.Models.Category;
import com.example.groupassapplication.MySingleton;
import com.example.groupassapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();
    public RecyclerView recyclerView;
    public EditText searchView;
    String url = "http://192.168.1.114:84/Mayar/getCategoryList.php";
    Adapter_CategoryList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        setupViews();
        loadData();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              filter(s.toString());
            }
        });



    }

    private void filter(String text) {
        ArrayList<Category> filterList = new ArrayList<>();
        for (Category item : categories)
        {
            if (item.getCategoryName().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }

    private void setupViews(){
        recyclerView = findViewById(R.id.category);
        searchView = findViewById(R.id.searchView);
    }

    private void loadData() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String categoryName = jsonObject.getString("categoryName");
                                String categoryPhoto = jsonObject.getString("categoryPhoto");

                                categories.add(new Category(id, categoryName, categoryPhoto));

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(CategoryListActivity.this));
                            adapter = new Adapter_CategoryList(CategoryListActivity.this, categories);
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

    public void insert_btn_OnClick(View view)
    {
        Intent intent = new Intent(this,InsertCategoryActivity.class);
        startActivity(intent);
    }
}