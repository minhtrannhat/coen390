package com.example.coen390_app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.coen390_app.R;
import com.ortiz.touchview.TouchImageView;

public class UserMapInterface extends AppCompatActivity {

    protected FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map_interface);

//        frameLayout = findViewById(R.id.frameLayout);

        // Example usage
//        displayImage(R.drawable.image1);
//        displayImage(R.drawable.image2);
//        displayImage(R.drawable.image3);
//        displayImage(R.drawable.image4);
    }

    private void displayImage(int imageResId) {
        TouchImageView photoView = new TouchImageView(this);
        photoView.setImageResource(imageResId);
        frameLayout.addView(photoView);
    }
}

