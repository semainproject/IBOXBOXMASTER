package com.example.kittaporn.iboxbox;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    FirebaseAuth mAuth;
// ...
    EditText emailTx,passwordTx;
    Button backBtn,signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        emailTx = (EditText) findViewById(R.id.email);
        passwordTx = (EditText) findViewById(R.id.password);
        backBtn = (Button) findViewById(R.id.backbutton);
        signupBtn = (Button) findViewById(R.id.signupbutton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,LoginActivity.class);
                startActivity(intent);

            }
        });
////
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTx.getText().toString();
                String password = passwordTx.getText().toString();
                if(!email.equals("")&&!password.equals("")){
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Signup.this, "ลงทะเบียนไม่สำรเ็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            finish();
                        }
                    }
                });
                }else{
                    if(email.equals("")){
                        emailTx.setError("Please fill your email");
                    }
                    if(email.equals("")){
                        passwordTx.setError("Please fill your password");
                    }
                }


            }
        });
    }
}
