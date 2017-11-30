package com.example.kittaporn.iboxbox;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressbar = (ProgressBar) findViewById(R.id.progressBar3);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    if (user != null) {
                        //progressbar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(StartActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        //progressbar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        welcomeThread.start();
    }
}
