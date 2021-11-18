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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupTabFragment extends Fragment {

    EditText nombre, email, pass, confirpass;
    Button btnsignup;

    private String userID;
    private FirebaseAuth mauth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.signup_tab_fragment, container, false);

        // firebase auth ES IMPORTANTE PONERLO
        mauth = FirebaseAuth.getInstance();
        // instancia para base de datos
        db = FirebaseFirestore.getInstance();

        // atributtos del fragment
        nombre = view.findViewById(R.id.nombre);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        confirpass = view.findViewById(R.id.confirmpass);
        btnsignup = view.findViewById(R.id.btnsignup);

        // accion del botons
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createuser();
            }
        });
        return  view;
    }

    public void createuser(){
        String name = nombre.getText().toString();
        String mail = email.getText().toString();
        String pas = pass.getText().toString();
        String conpas = confirpass.getText().toString();
        try{
            if(TextUtils.isEmpty(name)){
                nombre.setError("Este campo es obligatorio");
                nombre.requestFocus();
            } else if(TextUtils.isEmpty(mail)){
                email.setError("Este campo es obligatorio");
                email.requestFocus();
            }  else if(TextUtils.isEmpty(pas)){
                pass.setError("Ingrese una contraseña");
                pass.requestFocus();
            } else if(TextUtils.isEmpty(conpas)){
                confirpass.setError("Ingrese una contraseña para confirmar");
                confirpass.requestFocus();
            } else if(pas == conpas){
                pass.setError("Las contraseñas no coinciden");
                pass.requestFocus();
            } else if(pas.length() <= 8){
                pass.setError("La contraseña debe ser mayor a 8 caracteres.");
                pass.requestFocus();
            } else{
                mauth.createUserWithEmailAndPassword(mail, pas)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    userID = mauth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("users").document(userID);

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("email", mail);
                                    user.put("password", pas);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            System.out.println("Usuario registrado: "+ userID);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Usuario no registrado "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
