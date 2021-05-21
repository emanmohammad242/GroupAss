package com.example.groupassapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.groupassapplication.R;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void goBtnAction(View view) {

        Intent intent = new Intent(HomePageActivity.this, CategoryListActivity.class);
        startActivity(intent);
        finish();
    }
}