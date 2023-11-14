package com.example.coen390_app.Views;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.view.View;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserHomescreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ParkingLotAdapter parkingLotAdapter;

    private ParkingLotProfileFirebaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homescreen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        dbHelper = new ParkingLotProfileFirebaseHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();

        buildRecyclerView();
    }

    private void Open_UserMapInterface() {
        Intent intent = new Intent(this, UserMapInterface.class );
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            // Handle the "Settings" menu item click
            // You can perform an action here
            return true;
        }

        if (id == R.id.action_adminLogin) {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_search) {
            // Handle the search action
            //performSearch();
            return true;
        }

        if (id == R.id.refresh_homescreen){
            buildRecyclerView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerView(){
        recyclerView =findViewById(R.id.parking_lot_profile_list);
        dbHelper.getParkingLotProfiles(new ParkingLotProfileFirebaseHelper.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<ParkingLotProfile> parkingLotProfiles) {
                // Handle the loaded data
                // This block of code is executed after the data is loaded
                Map<String, Map<String, Boolean>> occupancyMap = parkingLotProfiles.get(0).occupancy;
                List<String> occupiedSpots = new ArrayList<>();

                for (Map.Entry<String, Map<String, Boolean>> floorEntry : occupancyMap.entrySet()) {
                    String floor = floorEntry.getKey();
                    Map<String, Boolean> spotStatusMap = floorEntry.getValue();

                    // Now, you can iterate through the spotStatusMap for each floor
                    for (Map.Entry<String, Boolean> spotEntry : spotStatusMap.entrySet()) {
                        String spot = spotEntry.getKey();
                        boolean status = spotEntry.getValue();

                        if (status){
                            occupiedSpots.add(spot);
                        }

                        Log.d("UserHomeScreen", "onDataChange: " + "Floor: " + floor + ", Spot: " + spot + ", Status: " + status);
                    }
                }

                dbHelper.setCurrentOccupancy(occupiedSpots.size());

                parkingLotAdapter = new ParkingLotAdapter(getApplicationContext(), parkingLotProfiles);

                // Set an OnClickListener on the RecyclerView items
                parkingLotAdapter.setOnItemClickListener(new ParkingLotAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick() {
                        // Handle item click, e.g., launch UserMapInterface activity
                        Open_UserMapInterface();
                    }
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(parkingLotAdapter);


                Log.d("Userhomescreen", "onStart: Current parkingLotList size " + parkingLotProfiles.size());
            }

            @Override
            public void onDataError(String errorMessage) {
                // Handle the error
                Log.e("UserHomeScreen", "onDataError: " + errorMessage);
            }
        });
    }
}







