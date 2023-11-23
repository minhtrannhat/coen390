package com.example.coen390_app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coen390_app.Controllers.AdminFirebaseHelper;
import com.example.coen390_app.Models.Admin;
import com.example.coen390_app.R;

import java.util.List;

public class AdminLogin extends AppCompatActivity {

    Button loginButton;

    protected AdminFirebaseHelper dbHelper;
    private String correctUsername;
    private String correctPassword;
    private EditText usernamePrompt;
    private EditText passwordPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize the button
        loginButton = findViewById(R.id.login_button);
        // Set a click listener for the button
        
        usernamePrompt = findViewById(R.id.username);
        passwordPrompt = findViewById(R.id.password);

        dbHelper = new AdminFirebaseHelper();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
            return true;
        }

        if (id == R.id.action_settings) {

            return true;
        }

        if (id == R.id.action_adminLogout) {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();

        dbHelper.getAdmins(new AdminFirebaseHelper.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Admin> AdminList) {
                correctUsername = AdminList.get(0).getUsername();
                correctPassword = AdminList.get(0).getPassword();

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((usernamePrompt.getText().toString().equals(correctUsername)) &&
                                (passwordPrompt.getText().toString().equals(correctPassword))){
                            Log.d("AdminLogin", "onClick: correct_username is " + correctUsername + " correct_password is " + correctPassword + " but username entered is " + usernamePrompt.getText().toString() + " and password entered is  " + passwordPrompt.getText().toString());
                            Intent intent = new Intent(AdminLogin.this, AdminHomeScreen.class);
                            startActivity(intent);
                        }
                        else{
                            Log.d("AdminLogin", "onClick: correct_username is " + correctUsername + " correct_password is " + correctPassword + " but username entered is " + usernamePrompt.getText().toString() + " and password entered is  " + passwordPrompt.getText().toString());
                            Toast.makeText(AdminLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onDataError(String errorMessage) {
                Log.e("AdminLoginScreen", "onDataError: " + errorMessage);
            }
        });
    }
}