package com.example.coen390_app.Controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminFirebaseHelper {
    protected DatabaseReference firebaseDatabase;

    public AdminFirebaseHelper(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://test-hw-project-86ca6-default-rtdb.firebaseio.com/").getReference();
    }
}
