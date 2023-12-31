package com.example.coen390_app.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapInterface extends AppCompatActivity {

    protected FrameLayout frameLayout;

    protected ParkingLotProfileFirebaseHelper dbHelper;

    protected DatabaseReference occupancyListDbRef;

    protected DatabaseReference currentOccupancyDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map_interface);

        dbHelper = new ParkingLotProfileFirebaseHelper();

        occupancyListDbRef = dbHelper.getParkingLotProfilesDbRef()
                .getDatabase()
                .getReference()
                .child("Test")
                .child("lots")
                .child("Lb_building")
                .child("occupancy");

        occupancyListDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Assuming you have retrieved the data from Firebase and stored it in occupancyMap
                HashMap<String, HashMap<String, Boolean>> occupancyMap = (HashMap<String, HashMap<String, Boolean>>) dataSnapshot.getValue();
                List<String> occupiedSpots = new ArrayList<>();

                for (Map.Entry<String, HashMap<String, Boolean>> floorEntry : occupancyMap.entrySet()) {
                    String floor = floorEntry.getKey();
                    HashMap<String, Boolean> spotStatusMap = floorEntry.getValue();

                    // Now, you can iterate through the spotStatusMap for each floor
                    for (Map.Entry<String, Boolean> spotEntry : spotStatusMap.entrySet()) {
                        String spot = spotEntry.getKey();
                        boolean status = spotEntry.getValue();

                        if (status){
                            occupiedSpots.add(spot);
                        }

                        Log.d("UserMapInterface", "onDataChange: " + "Floor: " + floor + ", Spot: " + spot + ", Status: " + status);
                    }
                }

                Log.d("UserMapInterface", "displayImage: occupiedSpots = " + occupiedSpots);
                displayImage(occupiedSpots);
                dbHelper.setCurrentOccupancy(occupiedSpots.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserMapInterface", "onCancelled: " + databaseError.getMessage());
            }
        });

        frameLayout = findViewById(R.id.frameLayout);
    }

    private void displayImage(List<String> occupiedSpots) {
        int imageResId = R.drawable.a1_and_a2_empty;
        Log.d("UserMapInterface", "displayImage: occupiedSpots = " + occupiedSpots);

        if (occupiedSpots.size() == 2) {
            imageResId = R.drawable.a1_and_a2_occupied;
        } else if (occupiedSpots.size() == 1) {
            if (occupiedSpots.get(0).equals("A1")) {
                imageResId = R.drawable.a1_occupied;
            } else {
                imageResId = R.drawable.a2_occupied;
            }
        }

        TouchImageView photoView = new TouchImageView(this);
        photoView.setImageResource(imageResId);
        frameLayout.addView(photoView);
    }
}

