package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class AdminAddParkingLot extends AppCompatActivity {

    private ParkingLotProfileFirebaseHelper dbHelper;
    private EditText lotName, address, owner, ownerTel, ownerEmail, postalCode, city, province, country;
    private Button cancelButton, saveButton;

    private ArrayList<SecondaryParkingLot> adminlots = new ArrayList<SecondaryParkingLot>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE3");
        adminlots = (ArrayList<SecondaryParkingLot>) args.getSerializable("ARRAYLIST3");


        dbHelper = new ParkingLotProfileFirebaseHelper();

        lotName = findViewById(R.id.lotName);
        address = findViewById(R.id.address);
        owner = findViewById(R.id.owner);
        ownerTel = findViewById(R.id.ownerTel);
        ownerEmail = findViewById(R.id.ownerEmail);
        postalCode = findViewById(R.id.postalCode);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        country = findViewById(R.id.country);

        loadProfileInfo();

        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);

        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddParkingLot.this, AdminHomeScreen.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST3", (Serializable) adminlots);
                intent.putExtra("BUNDLE3", args);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePreferences()) {
                    String name = lotName.getText().toString();
                    String vAddress = address.getText().toString();
                    adminlots.add(new SecondaryParkingLot(name, vAddress, 0, "purple"));
                    Intent intent = new Intent(AdminAddParkingLot.this, AdminHomeScreen.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST3", (Serializable) adminlots);
                    intent.putExtra("BUNDLE3", args);
                    startActivity(intent);

                    Toast.makeText(AdminAddParkingLot.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminAddParkingLot.this, "Invalid input. Please check.", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                if (dataSnapshot.exists()) {

                    //set textView
                    owner.setText(dataSnapshot.child("lot_owner").getValue(String.class));
                    ownerTel.setText(dataSnapshot.child("owner_tel").getValue(String.class));
                    ownerEmail.setText(dataSnapshot.child("owner_email").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean validatePreferences() {
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
        if (stringLotName.isEmpty() ||
                stringAddress.isEmpty() ||
                stringPostalCode.isEmpty() ||
                stringCity.isEmpty() ||
                stringProvince.isEmpty() ||
                stringCountry.isEmpty() ||
                stringOwner.isEmpty() ||
                stringOwnerTel.isEmpty() ||
                stringOwnerEmail.isEmpty()) {
            showToast("Please fill all fields.");
            return false; //empty fields
        }

        //other validation criteria
        //string patterns
        String alphanumerics = "^[a-zA-Z0-9\\s.-]{1,30}$";
        String alphabet = "^[a-zA-Z\\s-]{1,35}$";
        String telNumber = "^[0-9\\s]{1,20}$";
        String postalCode = "^[a-zA-Z0-9\\s]{1,7}$";

        //data validation
        if (!stringLotName.matches(alphanumerics)) {
            showToast("Invalid Parking lot name.");
            return false;
        }
        if (!stringAddress.matches(alphanumerics)) {
            showToast("Invalid address.");
            return false;
        }
        if (!stringPostalCode.matches(postalCode)) {
            showToast("Invalid Postal Code.");
            return false;
        }
        if (!stringCity.matches(alphabet)) {
            showToast("Invalid city name.");
            return false;
        }
        if (!stringProvince.matches(alphabet)) {
            showToast("Invalid province name.");
            return false;
        }
        if (!stringCountry.matches(alphabet)) {
            showToast("Invalid country name.");
            return false;
        }
        if (!stringOwner.matches(alphabet)) {
            showToast("Invalid owner name.");
            return false;
        }
        if (!stringOwnerTel.matches(telNumber)) {
            showToast("Invalid telephone number.");
            return false;
        }
        if (!Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(stringOwnerEmail).matches()
        ) {
            showToast("Invalid email.");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}