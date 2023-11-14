package com.example.coen390_app.Controllers;

import androidx.annotation.NonNull;

import com.example.coen390_app.Models.Admin;
import com.example.coen390_app.Models.ParkingLotProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFirebaseHelper {
    protected DatabaseReference firebaseDatabase;

    public AdminFirebaseHelper(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com/").getReference().child("Test").child("admins");
    }

    public void getAdmins(final AdminFirebaseHelper.OnDataLoadedListener listener) {
        final List<Admin> adminList = new ArrayList<>();

        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot lotSnapshot : dataSnapshot.getChildren()) {
                    Admin adminProfile = lotSnapshot.getValue(Admin.class);

                    if (adminProfile != null) {
                        adminList.add(adminProfile);
                    }
                }

                // Notify the listener that data loading is complete
                if (listener != null) {
                    listener.onDataLoaded(adminList);
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

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Admin> AdminList);

        void onDataError(String errorMessage);
    }
}
