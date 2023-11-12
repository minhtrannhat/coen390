package com.example.coen390_app.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.coen390_app.Models.ParkingLotProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotProfileFirebaseHelper {

    protected DatabaseReference firebaseDatabase;
    public ParkingLotProfileFirebaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com").getReference().child("Test").child("lots");
    }
    public void getParkingLotProfiles(final OnDataLoadedListener listener) {
        final List<ParkingLotProfile> parkingLotProfiles = new ArrayList<>();

        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot lotSnapshot : dataSnapshot.getChildren()) {
                    ParkingLotProfile parkingLotProfile = lotSnapshot.getValue(ParkingLotProfile.class);

                    if (parkingLotProfile != null) {
                        parkingLotProfiles.add(parkingLotProfile);
                    }
                }

                // Notify the listener that data loading is complete
                if (listener != null) {
                    listener.onDataLoaded(parkingLotProfiles);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                if (listener != null) {
                    listener.onDataError(databaseError.getMessage());
                }
            }
        });
    }

    public DatabaseReference getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public void setFirebaseDatabase(DatabaseReference firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    // Define an interface to handle the callback
    public interface OnDataLoadedListener {
        void onDataLoaded(List<ParkingLotProfile> parkingLotProfiles);
        void onDataError(String errorMessage);
    }

    public void test(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(); // Reference to the root node

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String jsonData = dataSnapshot.getValue().toString();

                if (dataSnapshot.getValue() != null) {
                    try {
                    // Prettify JSON using Gson
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String prettifiedJson = gson.toJson(gson.fromJson(jsonData, Object.class));

                    Log.d("ParkingLotProfileFirebaseHelper", "onDataChange: " + prettifiedJson);
                } catch (com.google.gson.JsonSyntaxException error){
                        Log.d("ParkingLotProfileFirebaseHelper", "onDataChange: " + jsonData);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ParkingLotProfileFirebaseHelper", "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}
