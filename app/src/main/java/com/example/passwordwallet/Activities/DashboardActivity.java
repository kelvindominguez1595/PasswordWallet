package com.example.passwordwallet.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.passwordwallet.Fragments.BusquedaFragment;
import com.example.passwordwallet.Fragments.HomeFragment;
import com.example.passwordwallet.Fragments.ProfileFragment;
import com.example.passwordwallet.R;
import com.example.passwordwallet.interfaces.IcomunicaFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements IcomunicaFragments {
    private FirebaseAuth mAuth;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // firebase auth
        mAuth = FirebaseAuth.getInstance();
        // parameters

        navigationView = findViewById(R.id.bottonNavView);
        //carge el home por defecto
        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerFragment,new HomeFragment()).commit();

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment= null;
                String Nameheader="";
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        Nameheader = "Home";
                        break;
                    case R.id.wallet:
                        fragment= new BusquedaFragment();
                        Nameheader = "Busqueda";
                        break;
                    case R.id.perfil:
                        fragment = new ProfileFragment();
                        Nameheader ="Perfil";
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.ContainerFragment,fragment).commit();

                return true;
            }
        });

    }

    @Override
    public void usuarios() {
        Toast.makeText(this, "Usuarios desde activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void iconos() {
        Toast.makeText(this, "Iconos ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    @Override
    public void subcuentas() {
        Toast.makeText(this, "Subcuentas", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ScrollingActivity2.class));
    }

    @Override
    public void Salir() {
        mAuth.signOut();
        Toast.makeText(this, "Cerrando Sesión", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}