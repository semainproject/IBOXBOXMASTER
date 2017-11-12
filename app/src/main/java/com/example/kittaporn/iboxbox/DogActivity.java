package com.example.kittaporn.iboxbox;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DogActivity extends AppCompatActivity {
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    DatabaseReference db,dbuser;
    FirebaseUser user;
    String uid;
    Button dateBtn;
    TextView dateTx,tx13,playTX,restTX,play,rest;
    //ImageView img,restIMG,playIMG;
    StackedBarChart mStackedBarChart;
    ConstraintLayout constraintLayout;
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        dateTx = (TextView) findViewById(R.id.dateTX);
        //img = (ImageView) findViewById(R.id.imageView6);
        tx13 = (TextView) findViewById(R.id.textView13);
        playTX = (TextView) findViewById(R.id.playTX);
        restTX = (TextView) findViewById(R.id.restTX);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mCalendar = Calendar.getInstance();
        mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
        db = FirebaseDatabase.getInstance().getReference("DOGID");
        dbuser = FirebaseDatabase.getInstance().getReference("USER").child(uid).child("device");
        mDatePicker = DatePickerDialog.newInstance(onDateSetListener,
                mCalendar.get(Calendar.YEAR),       // ปี
                mCalendar.get(Calendar.MONTH),      // เดือน
                mCalendar.get(Calendar.DAY_OF_MONTH),// วัน (1-31)
                false);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker.setYearRange(2017, 2020);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
                mStackedBarChart.clearChart();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, final int day) {

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
                    final String textDate = dateFormat.format(date);
//                    dateTx.setText(textDate);
                    final String dateInsting = String.valueOf(day) +"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                   // Toast.makeText(DogActivity.this,dateInsting,Toast.LENGTH_LONG).show();
                    dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String device = dataSnapshot.getValue(String.class);
                            try {
                                db.child(device).child("DATA").child(dateInsting).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            PlayRestGetter data = dataSnapshot.getValue(PlayRestGetter.class);
                                            if (dataSnapshot != null) {
                                                //img.setVisibility(View.INVISIBLE);
                                                playTX.setText(String.valueOf(data.getPlay())+" Hours");
                                                restTX.setText(String.valueOf(data.getRest())+" Hours");
                                                dateTx.setText(textDate);
                                                tx13.setVisibility(View.INVISIBLE);
                                                constraintLayout.setVisibility(View.VISIBLE);
                                                mStackedBarChart.setVisibility(View.VISIBLE);
                                                StackedBarModel bar = new StackedBarModel(String.valueOf(textDate));
                                                bar.addBar(new BarModel(data.getPlay(), 0xFF63CBB0));
                                                bar.addBar(new BarModel(data.getRest(), 0xFF56B7F1));
                                                mStackedBarChart.addBar(bar);
                                                mStackedBarChart.startAnimation();
                                            }
                                        } catch(Exception e) {
                                            tx13.setVisibility(View.VISIBLE);
                                            dateTx.setText("DATE");
                                            constraintLayout.setVisibility(View.INVISIBLE);
                                            Toast.makeText(DogActivity.this,"Please select other date!!",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } catch(Exception e) {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            };

}
