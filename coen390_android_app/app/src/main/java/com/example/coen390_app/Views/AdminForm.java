package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.R;

import java.security.acl.Owner;


public class AdminForm extends AppCompatActivity {

    private EditText lotName, address, postalCode, city, country, owner, ownerTel, ownerEmail;

    private Button cancelButton, saveButton, editButton;

    private boolean isEditMode = false;

    private ParkingLotProfileFirebaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        //initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dbHelper = new ParkingLotProfileFirebaseHelper();

        lotName = findViewById(R.id.lotName);
        address = findViewById(R.id.address);
        postalCode = findViewById(R.id.postalCode);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        owner = findViewById(R.id.owner);
        ownerTel = findViewById(R.id.ownerTel);
        ownerEmail = findViewById(R.id.ownerEmail);

        loadProfileInfo(); //to implement

        //initialize buttons
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode(false); //toggles to display mode
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate all fields have been filled
                //good > toggle display mode
                //fail > toast msg
            }
        });

        toggleEditMode(false); //display mode on default




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        //hide search, refresh and login button in menu
        MenuItem searchButton = menu.findItem(R.id.action_search);
        MenuItem refreshButton = menu.findItem(R.id.refresh_homescreen);
        MenuItem loginButton = menu.findItem(R.id.action_adminLogin);

        searchButton.setEnabled(false);
        refreshButton.setEnabled(false);
        loginButton.setEnabled(false);

        searchButton.setVisible(false);
        refreshButton.setVisible(false);
        loginButton.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_edit){
            isEditMode = !isEditMode;
            toggleEditMode(isEditMode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleEditMode(boolean isEdit) {
        //toggle mode function (display/edit)
        //if true > edit
        //if false > true

        isEditMode = isEdit;
        saveButton.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        cancelButton.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        //enable editing of text fields
        lotName.setEnabled(isEdit);
        address.setEnabled(isEdit);
        postalCode.setEnabled(isEdit);
        city.setEnabled(isEdit);
        country.setEnabled(isEdit);
        owner.setEnabled(isEdit);
        ownerTel.setEnabled(isEdit);
        ownerEmail.setEnabled(isEdit);

    }

    private void loadProfileInfo() {
        // Retrieve values from firebase




        // Populate EditText fields with saved values


    }

    private boolean validatePrefences(){
        //lotName, address, postalCode, city, country, owner, ownerTel, ownerEmail;
        String stringLotName = lotName.getText().toString().trim();
        String stringAddress = address.getText().toString().trim();
        String stringPostalCode = postalCode.getText().toString().trim();
        String stringCity = city.getText().toString().trim();
        String stringCountry = country.getText().toString().trim();
        String stringOwner = owner.getText().toString().trim();
        String stringOwnerTel = ownerTel.getText().toString().trim();
        String stringOwnerEmail = ownerEmail.getText().toString().trim();


        if(stringLotName.isEmpty() || stringAddress.isEmpty() || stringPostalCode.isEmpty() || stringCity.isEmpty() || stringCountry.isEmpty() || stringOwner.isEmpty() || stringOwnerTel.isEmpty() || stringOwnerEmail.isEmpty()){
            showToast("Button names cannot be empty.");
            return false; //empty fields
        }

        //other validation criterias


        //function to send to firebase

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}