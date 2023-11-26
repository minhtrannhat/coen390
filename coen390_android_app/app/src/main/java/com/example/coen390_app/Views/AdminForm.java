package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdminForm extends AppCompatActivity {

    private EditText lotName, address, postalCode, city, province, country, owner, ownerTel, ownerEmail;

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
        province = findViewById(R.id.province);
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
                if(validatePreferences()){
                    toggleEditMode(false);
                    Toast.makeText(AdminForm.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AdminForm.this, "Invalid input. Please check.", Toast.LENGTH_SHORT).show();
                }

                //validate all fields have been filled
                //good > toggle display mode
                //fail > toast msg
            }
        });

        toggleEditMode(false); //display mode on default




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);

        //hide search, refresh and login button in menu


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.check_sensors && isEditMode == false){
            Intent intent = new Intent(this, Connected_devices.class );
            startActivity(intent);
        }

        if(id == R.id.form_edit){
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
        province.setEnabled(isEdit);
        country.setEnabled(isEdit);
        owner.setEnabled(isEdit);
        ownerTel.setEnabled(isEdit);
        ownerEmail.setEnabled(isEdit);

    }

    private void loadProfileInfo() {

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com")
                .getReference().child("Test")
                .child("lots")
                .child("Lb_building");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    // Retrieve values from firebase
                    String lotNameFB = dataSnapshot.child("name").getValue(String.class);
                    String addressFB = dataSnapshot.child("address").getValue(String.class);
                    String postalCodeFB = dataSnapshot.child("postal_code").getValue(String.class);
                    String cityFB = dataSnapshot.child("city").getValue(String.class);
                    String provinceFB = dataSnapshot.child("province").getValue(String.class);
                    String countryFB = dataSnapshot.child("country").getValue(String.class);
                    String ownerFB = dataSnapshot.child("lot_owner").getValue(String.class);
                    String ownerTelFB = dataSnapshot.child("owner_tel").getValue(String.class);
                    String ownerEmailFB = dataSnapshot.child("owner_email").getValue(String.class);

                    //set textView
                    lotName.setText(lotNameFB);
                    address.setText(addressFB);
                    postalCode.setText(postalCodeFB);
                    city.setText(cityFB);
                    province.setText(provinceFB);
                    country.setText(countryFB);
                    owner.setText(ownerFB);
                    ownerTel.setText(ownerTelFB);
                    ownerEmail.setText(ownerEmailFB);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean validatePreferences(){
        //lotName, address, postalCode, city, country, owner, ownerTel, ownerEmail;
        String stringLotName = lotName.getText().toString().trim();
        String stringAddress = address.getText().toString().trim();
        String stringPostalCode = postalCode.getText().toString().trim();
        String stringCity = city.getText().toString().trim();
        String stringProvince = province.getText().toString().trim();
        String stringCountry = country.getText().toString().trim();
        String stringOwner = owner.getText().toString().trim();
        String stringOwnerTel = ownerTel.getText().toString().trim();
        String stringOwnerEmail = ownerEmail.getText().toString().trim();


        //fields cannot be empty

        if(stringLotName.isEmpty() ||
                stringAddress.isEmpty() ||
                stringPostalCode.isEmpty() ||
                stringCity.isEmpty() ||
                stringProvince.isEmpty() ||
                stringCountry.isEmpty() ||
                stringOwner.isEmpty() ||
                stringOwnerTel.isEmpty() ||
                stringOwnerEmail.isEmpty()){
            showToast("Please fill all fields.");
            return false; //empty fields
        }

        //other validation criterias

        //string patterns
        String alphanumerics = "^[a-zA-Z0-9\\s.-]{1,30}$";
        String alphabet = "^[a-zA-Z\\s-]{1,35}$";
        String telNumber = "^[0-9\\s]{1,20}$";
        //String telNumberSanitized = "^[0-9\\s]{1,20}$";
        String postalCode = "^[a-zA-Z0-9\\s]{1,7}$";
        String email = "^[a-zA-Z0-9\\s@._-]{1,35}$";

        //data validation
        if( !stringLotName.matches(alphanumerics)){
            showToast("Invalid Parking lot name.");
            return false;
        }
        if( !stringAddress.matches(alphanumerics)){
            showToast("Invalid address.");
            return false;
        }
        if( !stringPostalCode.matches(postalCode)){
            showToast("Invalid Postal Code.");
            return false;
        }
        if( !stringCity.matches(alphabet)){
            showToast("Invalid city name.");
            return false;
        }
        if( !stringProvince.matches(alphabet)){
            showToast("Invalid province name.");
            return false;
        }
        if( !stringCountry.matches(alphabet)){
            showToast("Invalid country name.");
            return false;
        }
        if( !stringOwner.matches(alphabet)){
            showToast("Invalid owner name.");
            return false;
        }
        if( !stringOwnerTel.matches(telNumber)){
            showToast("Invalid telephone number.");
            return false;
        }
        if( !stringOwnerEmail.matches(email)){
            showToast("Invalid email.");
            return false;
        }






        //function to send to firebase

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com")
                .getReference().child("Test")
                .child("lots")
                .child("Lb_building");

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", stringLotName);
        updates.put("address", stringAddress);
        updates.put("postal_code", stringPostalCode);
        updates.put("city", stringCity);
        updates.put("province", stringProvince);
        updates.put("country", stringCountry);
        updates.put("lot_owner", stringOwner);
        updates.put("owner_tel", stringOwnerTel);
        updates.put("owner_email", stringOwnerEmail);

        // Update the values in Firebase
        databaseReference.updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle successful update
                        Log.d("FirebaseUpdate", "Values updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failed update
                        Log.e("FirebaseUpdate", "Error updating values: " + e.getMessage());
                    }
                });


        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}