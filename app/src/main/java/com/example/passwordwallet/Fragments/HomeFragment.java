package com.example.passwordwallet.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.passwordwallet.Activities.MainActivity;
import com.example.passwordwallet.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private Button btnlogout;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // firebase auth
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    public void logout(){
        mAuth.signOut();
        Toast.makeText(getActivity(), "Cerrando Sesión", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

}
