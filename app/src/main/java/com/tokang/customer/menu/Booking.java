package com.tokang.customer.menu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tokang.customer.R;

import java.util.Calendar;

public class Booking extends AppCompatActivity {
    private String serviceKey;

    private Calendar calendar;
    private int year, month, day, hour, minute;

    private TextView mOrderTime, mOrderDate;
    private Button mEditDateTime;
    private RelativeLayout lEditTimeLayout;

    private Button mEditLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setActionBarTitle();
        setFindViewById();

        setDefaultDateTime();

        mEditDateTime.setOnClickListener(getEditTimeListener());
        lEditTimeLayout.setOnClickListener(getEditTimeListener());

        mEditLocation.setOnClickListener(getEditLocationListener());
    }

    private void setFindViewById(){
        mOrderTime = findViewById(R.id.order_time);
        mOrderDate = findViewById(R.id.order_date);
        mEditDateTime = findViewById(R.id.edit_time);
        lEditTimeLayout = findViewById(R.id.edit_time_layout);

        mEditLocation = findViewById(R.id.edit_location);
    }

    private View.OnClickListener getEditLocationListener() {
        View.OnClickListener editLocationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(maps);
            }
        };

        return editLocationListener;
    }

    private View.OnClickListener getEditTimeListener(){
        View.OnClickListener editTimeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        };

        return editTimeListener;
    }

    private void setDefaultDateTime(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        showTime(hour, minute);
        showDate(year, month+1, day);
    }

    private void datePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearDp, int monthDp, int dayDp) {
                        year = yearDp;
                        month = monthDp;
                        day = dayDp;

                        showDate(year, month+1, day);
                        timePicker();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void timePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourTp, int minuteTp) {
                hour = hourTp;
                minute = minuteTp;

                showTime(hour, minute);
            }
        }, hour, minute,true);
        timePickerDialog.show();
    }

    private void showDate(int year, int month, int day){
        mOrderDate.setText(new StringBuilder()
                .append(day)
                .append("/")
                .append(month)
                .append("/")
                .append(year)
        );
    }

    private void showTime(int hour, int minute){
        mOrderTime.setText(new StringBuilder()
                .append(hour)
                .append(":")
                .append(minute)
        );
    }

    private void setActionBarTitle(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.booking_confirmation));
        actionBar.setElevation(0);

        if(getIntent()!=null){
            serviceKey = getIntent().getStringExtra("ServiceKey");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
