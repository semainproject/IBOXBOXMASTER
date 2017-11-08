package com.example.kittaporn.iboxbox;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailTx,passTx;
    Button signInBtn,signUpBtn;
    FirebaseAuth mAuth;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener mAuthListener;
        emailTx = (EditText) findViewById(R.id.emailTx);
        passTx = (EditText) findViewById(R.id.passTx);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        signUpBtn = (Button) findViewById(R.id.signUpbtn);
        spinner = (ProgressBar) findViewById(R.id.progressBar2);
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent toMain = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(toMain);
            finish();
        }
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,Signup.class);
                startActivity(i);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                if(!TextUtils.isEmpty(emailTx.getText()) && !TextUtils.isEmpty(passTx.getText()) ) {
                    spinner.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(emailTx.getText().toString(), passTx.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                spinner.setVisibility(View.INVISIBLE);
                            } else {
                                Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(toMain);
                                finish();
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }else{
                    if(TextUtils.isEmpty(emailTx.getText())){
                        emailTx.setError("Please fill your email");
                    }
                    if(TextUtils.isEmpty(passTx.getText())){
                        passTx.setError("Please fill your password");
                    }
                }
//////////////////////////////////////////////
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
