package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


public class AdminAddParkingLot extends AppCompatActivity {

    private ParkingLotProfileFirebaseHelper dbHelper;
    private EditText lotName, address, owner, ownerTel, ownerEmail;;
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
                args.putSerializable("ARRAYLIST3",(Serializable)adminlots );
                intent.putExtra("BUNDLE3",args);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                String name = lotName.getText().toString();
                String vAddress = address.getText().toString();
                adminlots.add(new SecondaryParkingLot(name,vAddress,0,"purple"));
                Intent intent = new Intent(AdminAddParkingLot.this, AdminHomeScreen.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST3",(Serializable)adminlots );
                intent.putExtra("BUNDLE3",args);
                startActivity(intent);

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
                if (dataSnapshot.exists()){

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


}