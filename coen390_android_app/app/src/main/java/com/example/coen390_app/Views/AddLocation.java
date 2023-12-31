package com.example.coen390_app.Views;


import androidx.appcompat.widget.SearchView;
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
    private List<SecondaryParkingLot> searchedArray = new ArrayList<SecondaryParkingLot>();
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

        searchedArray = new ArrayList<SecondaryParkingLot>(unusedParkingLotList);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search2);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Parking lot name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    searchedArray = new ArrayList<SecondaryParkingLot>(unusedParkingLotList);
                }
                else{
                    searchedArray = new ArrayList<SecondaryParkingLot>();
                    for(int i=0;i <unusedParkingLotList.size();i++){
                        if(unusedParkingLotList.get(i).getName().toString().toUpperCase().contains(newText.toUpperCase()) ||
                                unusedParkingLotList.get(i).getAddress().toString().toUpperCase().contains(newText.toUpperCase())){
                            searchedArray.add(unusedParkingLotList.get(i));
                        }
                    }
                }
                buildRecyclerView();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_search2) {
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
        parkingLotAdapter = new ParkingLotAdapter(getApplicationContext(), parkingLotProfiles,searchedArray,false);

        parkingLotAdapter.setOnItemClickListener(new ParkingLotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                parkingLotList.add(searchedArray.get(position));
                unusedParkingLotList.remove(searchedArray.get(position));
                searchedArray.remove(position);
                buildRecyclerView();


            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(parkingLotAdapter);
    }
}