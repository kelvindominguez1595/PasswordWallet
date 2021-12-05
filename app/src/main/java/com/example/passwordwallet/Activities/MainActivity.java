package com.example.passwordwallet.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.passwordwallet.Adapters.LoginAdapter;
import com.example.passwordwallet.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    LoginAdapter adapter;
    FloatingActionButton google;
    float v = 0;

    private GoogleSignInClient mGoogleSignInClient;
    // private final static int RC_SIGN_IN = 123; // este ya no se utiliza
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // quito el header de la pagina principal
        getSupportActionBar().hide();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(R.id.btnlogingoogle);

        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        googleSigIn();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultLauncher.launch(new Intent(googleSigIn().getSignInIntent()));
            }
        });
        tabopt(); // controlar el tab opcion
        animationComponents(); // tenemos las animaciones
    }

    public void tabopt(){
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        // fragment login
        FragmentManager fm = getSupportFragmentManager();
        // instanciamos el fragme adapter
        adapter = new LoginAdapter(fm, getLifecycle());
        // conectamos el adaptador al viewpager2
        viewPager.setAdapter(adapter);
        // listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    public void animationComponents(){
        google.setTranslationY(300);
        tabLayout.setTranslationY(300);

        google.setAlpha(v);
        tabLayout.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    public GoogleSignInClient googleSigIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        return mGoogleSignInClient;
    }

    // forma antigua
   /* private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/

    ActivityResultLauncher resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account != null;
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("ERROR: ", "Google sign in failed", e);
                }
            }else{
                Log.w("NO", "RESULTADO: "+result);
            }
        }
    });
 /* ESTA ES LA FORMA SEGUN GOOGLE AUTHEN QUEDA OBSOLETA
   */
  /* @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
       if (requestCode == RC_SIGN_IN) {
           Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try {
               // Google Sign In was successful, authenticate with Firebase
               GoogleSignInAccount account = task.getResult(ApiException.class);
               Log.d("SUCCESS: ", "firebaseAuthWithGoogle:" + account.getId());
               firebaseAuthWithGoogle(account.getIdToken());
           } catch (ApiException e) {
               // Google Sign In failed, update UI appropriately
               Log.w("ERROR: ", "Google sign in failed", e);
           }
       }
   }*/

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}