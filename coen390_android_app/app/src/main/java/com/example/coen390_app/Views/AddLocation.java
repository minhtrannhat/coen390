package com.example.coen390_app.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AddLocation extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<SecondaryParkingLot> parkingLotList = new ArrayList<SecondaryParkingLot>();
    private List<SecondaryParkingLot> unusedParkingLotList = new ArrayList<SecondaryParkingLot>();
    private ParkingLotAdapter parkingLotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE1");
        parkingLotList = (ArrayList<SecondaryParkingLot>) args.getSerializable("ARRAYLIST");
        args = intent.getBundleExtra("BUNDLE2");
        unusedParkingLotList = (ArrayList<SecondaryParkingLot>) args.getSerializable("UNUSEDARRAYLIST");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_search) {
            // need to clear array before switching to avoid element copies
            return true;
        }

        if (id == R.id.refresh_homescreen2){
            buildRecyclerView();
            return true;
        }

        if (id == R.id.go_back){
            Intent intent = new Intent(this, UserHomescreen.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)parkingLotList );
            intent.putExtra("BUNDLE1",args);
            args.putSerializable("UNUSEDARRAYLIST",(Serializable)unusedParkingLotList);
            intent.putExtra("BUNDLE2",args);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void buildRecyclerView(){
        recyclerView =findViewById(R.id.parking_lot_profile_list_add);


        List<ParkingLotProfile> parkingLotProfiles = new ArrayList<ParkingLotProfile>();
        parkingLotAdapter = new ParkingLotAdapter(getApplicationContext(), parkingLotProfiles,unusedParkingLotList,false);

        parkingLotAdapter.setOnItemClickListener(new ParkingLotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                parkingLotList.add(unusedParkingLotList.get(position));
                unusedParkingLotList.remove(position);
                buildRecyclerView();


            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(parkingLotAdapter);
    }
}