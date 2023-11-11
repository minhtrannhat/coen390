package com.example.coen390_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.acl.Owner;


public class AdminForm extends AppCompatActivity {

    private EditText lotName, address, postalCode, city, country, owner, ownerTel, ownerEmail;

    private Button cancelButton, saveButton;

    private boolean isEditMode = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        //initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        toggleEditMode(true); //display mode on default

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
        //pull data from firebase
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