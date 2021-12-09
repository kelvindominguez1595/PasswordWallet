package com.example.passwordwallet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.passwordwallet.Activities.DashboardActivity;
import com.example.passwordwallet.Activities.MainActivity;
import com.example.passwordwallet.Activities.ScrollingActivity;
import com.example.passwordwallet.R;
import com.example.passwordwallet.databinding.FragmentBusquedaBinding;
import com.example.passwordwallet.interfaces.IcomunicaFragments;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;

    View vista;
    Activity actividad;

    LinearLayout usuarios;
    LinearLayout iconos;
    LinearLayout subcuentas;
    LinearLayout salir;
    IcomunicaFragments interfaceComunicaFragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // firebase auth
        mAuth = FirebaseAuth.getInstance();
        vista = inflater.inflate(R.layout.fragment_home, container, false);
        usuarios = vista.findViewById(R.id.usuarios);
        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               interfaceComunicaFragments.usuarios();
            }
        });
        iconos = vista.findViewById(R.id.iconos);
        iconos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.iconos();
            }
        });
        subcuentas = vista.findViewById(R.id.subcuentas);
        subcuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.subcuentas();
            }
        });
        salir = vista.findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.Salir();
            }
        });
        return vista;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            actividad = (Activity) context;
            interfaceComunicaFragments = (IcomunicaFragments) actividad;
        }
    }
}
