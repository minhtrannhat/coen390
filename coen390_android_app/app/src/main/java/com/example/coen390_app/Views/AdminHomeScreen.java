package com.example.coen390_app.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminHomeScreen extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected ParkingLotProfileFirebaseHelper dbHelper;
    private Button button;
    private ParkingLotAdapter parkingLotAdapter;

    static private ArrayList<SecondaryParkingLot> adminlots = new ArrayList<SecondaryParkingLot>();

    static boolean fromAddfunc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dbHelper = new ParkingLotProfileFirebaseHelper();

        if(fromAddfunc){
            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra("BUNDLE3");
            adminlots = (ArrayList<SecondaryParkingLot>) args.getSerializable("ARRAYLIST3");
            fromAddfunc = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
            return true;
        }
        if(id == R.id.admin_add){
            fromAddfunc = true;
            Intent intent = new Intent(this, AdminAddParkingLot.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST3",(Serializable)adminlots );
            intent.putExtra("BUNDLE3",args);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, admin_settings.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_adminLogout) {
            Intent intent = new Intent(this, UserHomescreen.class);
            startActivity(intent);
            Toast.makeText(this ,"Logged out", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.parking_lot_profile_list);
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

                        if (status) {
                            occupiedSpots.add(spot);
                        }

                        Log.d("UserHomeScreen", "onDataChange: " + "Floor: " + floor + ", Spot: " + spot + ", Status: " + status);
                    }
                }

                dbHelper.setCurrentOccupancy(occupiedSpots.size());


                parkingLotAdapter = new ParkingLotAdapter(getApplicationContext(), parkingLotProfiles,adminlots,true);

                // Set an OnClickListener on the RecyclerView items
                parkingLotAdapter.setOnItemClickListener(new ParkingLotAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Handle item click, e.g., launch UserMapInterface activity
                        if(position == 0){
                            openFormPage();
                        }else{
                            Toast.makeText(AdminHomeScreen.this,"Parking lot not connected to database",Toast.LENGTH_SHORT).show();

                        }

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

    public void openFormPage() {
        Intent intent = new Intent(this, AdminForm.class);
        startActivity(intent);
    }
}