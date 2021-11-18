package com.example.passwordwallet.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passwordwallet.Activities.DashboardActivity;
import com.example.passwordwallet.Activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    // instanciamos las librerias de firebase
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // firebase auth
        mauth = FirebaseAuth.getInstance();

        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); */
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(this, MainActivity.class ));
        } else {
            startActivity(new Intent(this, DashboardActivity.class ));
        }
    }
}