package com.example.coen390_app.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coen390_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    protected DatabaseReference mDatabase;

    protected TextView textViewDistance;
    protected TextView textViewSpotStatus;

    protected String distance;

    protected String spotStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com/").getReference();

        textViewDistance = findViewById(R.id.textViewDistance);
        textViewSpotStatus = findViewById(R.id.textViewSpotStatus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("Test").child("Distance").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    distance = String.valueOf(task.getResult().getValue());
                    Log.d("firebase", "Value of Distance: " + distance);
                    textViewDistance.setText("Distance: " + distance);
                }
            }
        });
        mDatabase.child("Test").child("spotStatus").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    spotStatus = String.valueOf(task.getResult().getValue());
                    Log.d("firebase", "Value of spotStatus: " + spotStatus);
                    textViewSpotStatus.setText("Spot Status: " + spotStatus);
                }
            }
        });
    }
}