package com.example.coen390_app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.coen390_app.Controllers.ParkingLotProfileFirebaseHelper;
import com.example.coen390_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import org.w3c.dom.Text;

public class admin_settings extends AppCompatActivity {

    private ParkingLotProfileFirebaseHelper dbHelper;

    static boolean firstCall = true;

    static String name= "", address= "", email= "",telephone= "";

    TextView vname,vaddress,vemail,vtel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        dbHelper = new ParkingLotProfileFirebaseHelper();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if(firstCall == true){
            getFirebaseData();
            firstCall = false;
        }
        else{
            vname = findViewById(R.id.admin_settings_name);
            vaddress = findViewById(R.id.admin_settings_location);
            vemail = findViewById(R.id.admin_settings_email);
            vtel = findViewById(R.id.admin_settings_tel);

            vname.setText("Name: " + name);
            vaddress.setText("Address: " + address);
            vemail.setText("Email: " + email);
            vtel.setText("Contact telephone : +1 " + telephone);
        }



    }


    private void getFirebaseData(){
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com")
                .getReference().child("Test")
                .child("lots")
                .child("Lb_building");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    //set textView
                    name = (dataSnapshot.child("lot_owner").getValue(String.class)).toString();
                    telephone = (dataSnapshot.child("owner_tel").getValue(String.class));
                    email = (dataSnapshot.child("owner_email").getValue(String.class));
                    address =dataSnapshot.child("address").getValue(String.class);


                    vname = findViewById(R.id.admin_settings_name);
                    vaddress = findViewById(R.id.admin_settings_location);
                    vemail = findViewById(R.id.admin_settings_email);
                    vtel = findViewById(R.id.admin_settings_tel);

                    vname.setText("Name: " + name);
                    vaddress.setText("Address: " + address);
                    vemail.setText("Email: " + email);
                    vtel.setText("Contact telephone : +1 " + telephone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}