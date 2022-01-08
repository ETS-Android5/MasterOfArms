package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtTxtUsername, edtTxtEmail, edtTxtPass, edtTxtConfirmPass,edtTxtPhone;
    public static TextView txtVwDate;
    public RadioGroup radGrp;
    public RadioButton radBtn;

    public static int age;
    int day,month,year;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtVwDate = (TextView) findViewById(R.id.txtVwDate_Reg);
        setTitle("Registration Page");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

    }


    public void dateClick(View view) {
        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mMonth = c.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            c.set(Calendar.MONTH, month);
            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            age = calculateAge(c.getTimeInMillis());

            if (age < 12) {
                txtVwDate.setError("Age must be more than 12");
            } else {
                txtVwDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }
    };

    int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public void Register(View view) {
        edtTxtUsername = (EditText) findViewById(R.id.edtTxtUsername_Reg);
        edtTxtEmail = (EditText) findViewById(R.id.edtTxtEmail_Reg);
        edtTxtPass = (EditText) findViewById(R.id.edtTxtPassword_Reg);
        edtTxtConfirmPass = (EditText) findViewById(R.id.edtTxtConfirmPassword_Reg);
        edtTxtPhone=(EditText)findViewById(R.id.edtTxtPhone_Reg);

        radGrp=(RadioGroup)findViewById(R.id.radGrpGender);

        // get selected radio button from radioGroup
        int selectedId = radGrp.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radBtn = (RadioButton) findViewById(selectedId);

        String name = edtTxtUsername.getText().toString();
        String email = edtTxtEmail.getText().toString();
        String pass = edtTxtPass.getText().toString();
        String confirmPass = edtTxtConfirmPass.getText().toString();
        String dob=txtVwDate.getText().toString();
        String gender=radBtn.getText().toString();
        String phone=edtTxtPhone.getText().toString();

        if (!name.isEmpty()) {
            if (name.length() >= 3 && name.length() <= 30) {
                if (!email.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (email.length() >= 5 && email.length() <= 45) {

                            Boolean chkemail = db.chkemail(email);

                            if (chkemail == true) {
                                if (!pass.isEmpty()) {
                                    if (pass.length() >= 5 && pass.length() <= 15) {
                                        if (!confirmPass.isEmpty()) {
                                            if (confirmPass.equals(pass)) {
                                                if (!txtVwDate.getText().toString().equals("Birthday")) {
                                                    if(!phone.isEmpty()) {
                                                        if (phone.length() == 10) {

                                                            boolean insert = db.insertData(name, email, pass, dob,phone,gender);
                                                                if (insert == true) {
                                                                    Toast.makeText(getApplicationContext(), "Registered Successully!!!", Toast.LENGTH_LONG).show();

                                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                    startActivity(intent);

                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Registration Failed!!!", Toast.LENGTH_LONG).show(); }
                                                        } else {
                                                            edtTxtPhone.setError("Invalid phone number");}
                                                    }else {
                                                       edtTxtPhone.setError("Enter phone number"); }
                                                } else {
                                                    txtVwDate.setError("Select Birthday"); }
                                            } else {
                                                edtTxtConfirmPass.setError("Password and Confirm Password does not match"); }
                                        } else {
                                            edtTxtConfirmPass.setError("Enter Confirm Password"); }
                                    } else {
                                        edtTxtPass.setError("Password range is 5-15"); }

                                } else {
                                    edtTxtPass.setError("Enter Password"); }
                            } else {
                                edtTxtEmail.setError("Email already exists");}
                        }else {
                            edtTxtEmail.setError("Email Address range is 5-45"); }
                    }else {
                        edtTxtEmail.setError("Invalid Email Address");}
                } else {
                    edtTxtEmail.setError("Enter Email Address"); }
            } else {
                edtTxtUsername.setError("Username range is 3-30"); }
        } else {
            edtTxtUsername.setError("Enter Uername"); }
    }

}
