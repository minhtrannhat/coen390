package com.example.coen390_app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.coen390_app.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPass extends AppCompatActivity {
    String correctEmail = "admin@concordia.ca";

    Button returnEmailBtn;
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        returnEmailBtn = findViewById(R.id.emailBtn);
        inputEmail = findViewById(R.id.returnEmail);

        returnEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctEmail.equals(inputEmail.getText().toString())){
                    Toast.makeText(ForgotPass.this,"Email sent", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPass.this ,"Incorrect email", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}