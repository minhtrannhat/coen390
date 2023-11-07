package com.example.coen390_app;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class UserHomescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homescreen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true); //back button
        //actionBar.setIcon(R.drawable.parkngo_logo); //test icon
        //actionBar.setDisplayHomeAsUpEnabled(true);

/*
     final Button modeToggleButton = findViewById(R.id.modeToggleButton);
     final Button saveButton = findViewById(R.id.saveButton);
     final Button cancelButton = findViewById(R.id.cancelButton);
*/






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            // Handle the "Settings" menu item click
            // You can perform an action here
            return true;
        }

        if (id == R.id.action_adminLogin) {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_search) {
            // Handle the search action
            //performSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}







