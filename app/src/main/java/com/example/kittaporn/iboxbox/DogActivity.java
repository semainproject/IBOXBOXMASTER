package com.example.kittaporn.iboxbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DogActivity extends AppCompatActivity {
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    Button dateBtn;
    TextView dateTx;
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        dateTx = (TextView) findViewById(R.id.dateTX);
        mCalendar = Calendar.getInstance();
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
            }
        });
    }
    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
                    String textDate = dateFormat.format(date);

                    dateTx.setText(textDate);
                }
            };
}
