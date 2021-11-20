package com.example.passwordwallet.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.passwordwallet.Activities.DashboardActivity;
import com.example.passwordwallet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    TextView forgotpass;
    EditText email, password;
    Button btnlogin;
    float v = 0;
    private FirebaseAuth mauth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        // firebase auth
        mauth = FirebaseAuth.getInstance();

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        btnlogin = view.findViewById(R.id.btnlogin);
        forgotpass = view.findViewById(R.id.forgotpass);

        email.setTranslationX(800);
        password.setTranslationX(800);
        btnlogin.setTranslationX(800);
        forgotpass.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        btnlogin.setAlpha(v);
        forgotpass.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnlogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        // onclick button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        return  view;
    }

    public void userLogin(){
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        // validamos los campos que no esten vacios
        if(TextUtils.isEmpty(mail)){
            email.setError("Este campo es obligatorio");
            email.requestFocus();
        } else if(TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        } else{
            mauth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "Bienvenido", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                    } else{
                        Toast.makeText(getActivity(), "Usuario no registro ó esta mal escrito los datos", Toast.LENGTH_SHORT).show();
                        Log.w("RESPUES", "ERROR: ", task.getException());
                    }
                }
            });
        }
    }

}
