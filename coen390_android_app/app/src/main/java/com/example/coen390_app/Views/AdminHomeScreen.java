package com.example.coen390_app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coen390_app.R;

public class AdminHomeScreen extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        button = findViewById(R.id.to_form_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFormPage();
            }
        });

    }

    public void openFormPage() {
        Intent intent = new Intent(this, AdminForm.class );
        startActivity(intent);
    }
}