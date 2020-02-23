package com.choewang.kmetrify;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLDisplay;

public class AddDayActivity extends Activity {
    private Date date;
    private int meterReading;
    private String startPlace;
    private List<String> listOfPlaces;
    private String finalPlace;
    private int dailyKm;
    private EditText dateInput;

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener datePickerDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_day);
        dateInput = (EditText) findViewById(R.id.editTextDate);
    }

    public void onDateInputClick(View view) {
        new DatePickerDialog(view.getContext(), datePickerDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        dateInput.setText(sdf.format(myCalendar.getTime()));
    }

    public void onSaveClick(View view) throws ParseException {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        this.date = sdf.parse(dateInput.getText().toString());
        EditText meter = (EditText) findViewById(R.id.editTextMeter);
        this.meterReading = Integer.parseInt(meter.getText().toString());
        EditText start = (EditText) findViewById(R.id.editTextStart);
        this.startPlace = start.getText().toString();
        EditText finale = (EditText) findViewById(R.id.editTextFinal);
        this.finalPlace = finale.getText().toString();

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra("Date", this.date);
        intent.putExtra("Meter_Reading", this.meterReading);
        intent.putExtra("Start", this.startPlace);
        intent.putExtra("Final", this.finalPlace);
        startActivity(intent);
    }
}
