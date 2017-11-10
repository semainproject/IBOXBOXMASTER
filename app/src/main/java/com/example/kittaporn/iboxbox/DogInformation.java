package com.example.kittaporn.iboxbox;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DogInformation extends AppCompatActivity {
    public int code;
    DatabaseReference db,db2;
    EditText name,device;
    CheckBox male,female;
    FloatingActionButton fab,fab2;
    FirebaseUser user;
    String uid;
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_information);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("USER").child(uid);
        db2 = FirebaseDatabase.getInstance().getReference("DOGID");
        name = (EditText) findViewById(R.id.nameTX);
        device = (EditText) findViewById(R.id.deviceTX);
        male = (CheckBox) findViewById(R.id.checkBox);
        female = (CheckBox) findViewById(R.id.checkBox2);
        fab = (FloatingActionButton) findViewById(R.id.submit);
        fab2 = (FloatingActionButton) findViewById(R.id.cancel);
        Bundle bundle = getIntent().getExtras();
        mCalendar = Calendar.getInstance();
        if (bundle != null) {
            int pageCode = bundle.getInt("pagenumber");
            code = pageCode;
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            finish();
        }
        /////////////////////////////////////////////////////////
        if(code == 1){ /////// from loginactivity ///////////////////
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String dogname = name.getText().toString();
                    final String deviceID = device.getText().toString();
                    DogInformationGetter infomation = new DogInformationGetter(dogname,deviceID);
                    db.child("device").setValue(deviceID);
                    db2.child(deviceID).child("information").setValue(infomation);
                    finish();
                }
            });

        }else if(code == 2){ ////// from another avivity //////////////////
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String dogname = name.getText().toString();
                    final String deviceID = device.getText().toString();
                    DogInformationGetter infomation = new DogInformationGetter(dogname,deviceID);
                    db.child("device").setValue(deviceID);
                    db2.child(deviceID).child("information").setValue(infomation);
                    finish();
                }
            });
        }
        ///////////////////////////////////////////////////////
    }
}
