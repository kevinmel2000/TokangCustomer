package com.tokang.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tokang.customer.auth.OTPActivity;
import com.tokang.customer.constants.AppConstants;
import com.tokang.customer.utils.StringUtils;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputName, inputPhone, inputEmail;
    private Button btnRegister;
    private boolean isNameValid, isPhoneValid, isEmailValid = false;
    private FirebaseDatabase database;
    private DatabaseReference user_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setFindViewById();

        inputName.addTextChangedListener(inputNameTextWatcher());
        inputPhone.addTextChangedListener(inputPhoneTextWatcher());
        inputEmail.addTextChangedListener(inputEmailTextWatcher());
        btnRegister.setOnClickListener(btnRegisterListener());

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        user_table = database.getReference(AppConstants.USER_TABLE);
    }

    private void setFindViewById(){
        inputName = findViewById(R.id.input_name);
        inputPhone = findViewById(R.id.input_phone);
        inputEmail = findViewById(R.id.input_email);
        btnRegister = findViewById(R.id.btn_register);
    }

    private View.OnClickListener btnRegisterListener(){
        View.OnClickListener vo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage(getString(R.string.please_wait));
                mDialog.show();

                user_table.addListenerForSingleValueEvent(userTableEventListener(mDialog));
            }
        };

        return vo;
    }

    private TextWatcher inputNameTextWatcher(){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isNameValid = StringUtils.hasValue(inputName.getText().toString(), true);
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        return tw;
    }

    private TextWatcher inputEmailTextWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                isEmailValid = StringUtils.hasValue(inputEmail.getText().toString(), true) &&
                        Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches();
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        return tw;
    }

    private TextWatcher inputPhoneTextWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone_number="";
                if(StringUtils.hasValue(inputPhone.getText().toString(), true)){
                    phone_number = inputPhone.getText().toString();
                    if(phone_number.length()>=1 && phone_number.substring(0,1).equals("0")){
                        phone_number = phone_number.replace(phone_number.substring(0,1),"");
                        inputPhone.setText(phone_number);
                        isPhoneValid = false;
                    } else {
                        isPhoneValid = true;
                    }
                } else {
                    isPhoneValid = false;
                }

                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return tw;
    }

    private void checkInput(){
        if(isNameValid && isPhoneValid && isEmailValid) {
            btnRegister.setEnabled(true);
        } else {
            btnRegister.setEnabled(false);
        }
    }

    private ValueEventListener userTableEventListener(final ProgressDialog mDialog){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(inputPhone.getText().toString()).exists()){
                    mDialog.dismiss();
                    inputPhone.setError(getString(R.string.user_exists));
                } else {
                    mDialog.dismiss();
                    Intent otp = new Intent(RegisterActivity.this, OTPActivity.class);
                    otp.putExtra(AppConstants.FROM_STATE, AppConstants.REGISTER_STATE);
                    otp.putExtra(AppConstants.PHONE_NUMBER, inputPhone.getText().toString().trim());
                    otp.putExtra(AppConstants.NAME, inputName.getText().toString().trim());
                    otp.putExtra(AppConstants.EMAIL, inputEmail.getText().toString().trim());
                    startActivity(otp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return ve;
    }
}
