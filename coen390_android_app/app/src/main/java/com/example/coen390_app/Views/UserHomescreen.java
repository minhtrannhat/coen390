package com.example.coen390_app.Views;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.Toast;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.firebase.messaging.FirebaseMessaging;

public class UserHomescreen extends AppCompatActivity {
    private RecyclerView recyclerView;

    static private List<SecondaryParkingLot> parkingLotList = new ArrayList<SecondaryParkingLot>();

    static private List<SecondaryParkingLot> unusedParkingLotList = new ArrayList<SecondaryParkingLot>();

    private List<SecondaryParkingLot> oldParkingLotList = new ArrayList<SecondaryParkingLot>();
    private List<SecondaryParkingLot> oldUnusedList = new ArrayList<SecondaryParkingLot>();
    private ParkingLotAdapter parkingLotAdapter;

    private ParkingLotProfileFirebaseHelper dbHelper;

    private Button btnCancel, btnSave;
    private boolean isEditMode = false;

    static private boolean firstLogIn = true;
    static private boolean fromAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homescreen);


        // extra array
        if(firstLogIn){

            String addrTemp = "Lorem ipsum dolor sit amet";
            for(int i =0; i <5;i++){
                int occ = 2*i+ i;
                if(occ > 10){occ = 5;}
                if(i == 3){
                    parkingLotList.add(new SecondaryParkingLot("Building " + (i+1), addrTemp.substring(0,(10+i*3)),occ,"green"));
                }else{
                    unusedParkingLotList.add(new SecondaryParkingLot("Building " + (i+1), addrTemp.substring(0,(10+i*3)),occ,"green"));
                }

            }
            firstLogIn = false;
        }else{
            if(fromAdmin == false){
                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("BUNDLE1");
                parkingLotList = (ArrayList<SecondaryParkingLot>) args.getSerializable("ARRAYLIST");
                args = intent.getBundleExtra("BUNDLE2");
                unusedParkingLotList = (ArrayList<SecondaryParkingLot>) args.getSerializable("UNUSEDARRAYLIST");
            }
            fromAdmin = false;
        }

        NotificationsUtils.checkAndRequestNotificationPermission(this);

        oldParkingLotList = new ArrayList<SecondaryParkingLot>(parkingLotList);
        oldUnusedList = new ArrayList<SecondaryParkingLot>(unusedParkingLotList);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dbHelper = new ParkingLotProfileFirebaseHelper();

        btnCancel = findViewById(R.id.cancel_button);
        btnSave = findViewById(R.id.save_button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditMode = !isEditMode;
                oldParkingLotList = new ArrayList<SecondaryParkingLot>(parkingLotList);
                oldUnusedList = new ArrayList<SecondaryParkingLot>(unusedParkingLotList);
                toggleEditMode(isEditMode);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditMode = !isEditMode;
                parkingLotList = new ArrayList<SecondaryParkingLot>(oldParkingLotList);
                unusedParkingLotList = new ArrayList<SecondaryParkingLot>(oldUnusedList);
                toggleEditMode(isEditMode);
                buildRecyclerView();
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d("UserHomeScreen", "FCM Token: " + token);
                    } else {
                        Log.e("UserHomeScreen", "Failed to obtain FCM Token", task.getException());
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("occupancy_alert")
                .addOnCompleteListener(task -> {
                    String message = "Subscribed to occupancy_alert topic!";
                    if (!task.isSuccessful()) {
                        message = "Subscription to occupancy_alert topic failed";
                    }
                    Log.d("UserHomeScreen", message);
                });
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
            isEditMode = !isEditMode;
           toggleEditMode(isEditMode);
            return true;
        }

        if (id == R.id.action_adminLogin) {
            fromAdmin = true;
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_search) {
            // need to clear array before switching to avoid element copies
            Intent intent = new Intent(this, AddLocation.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)parkingLotList );
            intent.putExtra("BUNDLE1",args);
            args.putSerializable("UNUSEDARRAYLIST",(Serializable)unusedParkingLotList);
            intent.putExtra("BUNDLE2",args);
            startActivity(intent);

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

    private void toggleEditMode(boolean isEdit) {
        btnSave.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        btnCancel.setVisibility(isEdit ? View.VISIBLE : View.GONE);
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


                parkingLotAdapter = new ParkingLotAdapter(getApplicationContext(), parkingLotProfiles,parkingLotList,true);

                // Set an OnClickListener on the RecyclerView items
                parkingLotAdapter.setOnItemClickListener(new ParkingLotAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Handle item click, e.g., launch UserMapInterface activity
                        if(isEditMode){
                            if(position == 0){
                                Toast.makeText(UserHomescreen.this,"Unable to complete action! Try again later",Toast.LENGTH_SHORT).show();
                            }else{
                                unusedParkingLotList.add(parkingLotList.get(position-1));
                                parkingLotList.remove(position-1);
                                buildRecyclerView();
                            }
                        }
                        else if(position == 0){
                            Open_UserMapInterface();
                        }else{
                            Toast.makeText(UserHomescreen.this,"Parking information is currently down",Toast.LENGTH_SHORT).show();
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
}







