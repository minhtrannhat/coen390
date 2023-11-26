package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.ParkingLotDevice;
import com.example.coen390_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connected_devices extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<ParkingLotDevice> parkingLotDevices = new ArrayList<ParkingLotDevice>();

    private DeviceAdapter deviceAdapter;

    private ParkingLotProfileFirebaseHelper dbHelper;
    protected DatabaseReference occupancyListDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);

        dbHelper = new ParkingLotProfileFirebaseHelper();
        occupancyListDbRef = dbHelper.getParkingLotProfilesDbRef()
                .getDatabase()
                .getReference()
                .child("Test")
                .child("lots")
                .child("Lb_building")
                .child("occupancy");
        buildRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        buildRecycleView();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
    public void buildRecycleView(){
        recyclerView =findViewById(R.id.device_view_list);

        occupancyListDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Assuming you have retrieved the data from Firebase and stored it in occupancyMap
                HashMap<String, HashMap<String, Boolean>> occupancyMap = (HashMap<String, HashMap<String, Boolean>>) dataSnapshot.getValue();


                for (Map.Entry<String, HashMap<String, Boolean>> floorEntry : occupancyMap.entrySet()) {
                    String floor = floorEntry.getKey();
                    HashMap<String, Boolean> spotStatusMap = floorEntry.getValue();

                    // Now, you can iterate through the spotStatusMap for each floor
                    for (Map.Entry<String, Boolean> spotEntry : spotStatusMap.entrySet()) {
                        String spot = spotEntry.getKey();
                        boolean status = spotEntry.getValue();

                        if(parkingLotDevices.size() < 2) {
                            parkingLotDevices.add(new ParkingLotDevice(spot, status));
                        }

                    }
                }




                deviceAdapter = new DeviceAdapter(getApplicationContext(),parkingLotDevices);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(deviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserMapInterface", "onCancelled: " + databaseError.getMessage());
            }
        });
    }


}