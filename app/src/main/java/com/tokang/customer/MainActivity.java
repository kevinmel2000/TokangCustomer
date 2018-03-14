package com.tokang.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.tokang.customer.auth.OTPActivity;
import com.tokang.customer.constants.AppConstants;
import com.tokang.customer.utils.StringUtils;

public class MainActivity extends AppCompatActivity {
    private EditText inputPhone;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null && StringUtils.hasValue(mAuth.getCurrentUser().getPhoneNumber(), true)){
            Intent home = new Intent(MainActivity.this, HomeActivity.class);
            home.putExtra(AppConstants.PHONE_NUMBER,mAuth.getCurrentUser().getPhoneNumber());
            startActivity(home);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            setViewById();
            inputPhone.addTextChangedListener(inputPhoneTextWatcher());
            btnLogin.setOnClickListener(loginBtnListener());
        }
    }

    private void setViewById(){
        inputPhone = findViewById(R.id.input_phone);
        btnLogin = findViewById(R.id.btn_login);
    }

    private View.OnClickListener loginBtnListener() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = inputPhone.getText().toString();
                if(StringUtils.hasValue(phoneNumber, true)){
                    Intent otp = new Intent(MainActivity.this, OTPActivity.class);
                    otp.putExtra(AppConstants.PHONE_NUMBER,phoneNumber.trim());
                    startActivity(otp);
                }
            }
        };

        return v;
    }

    private TextWatcher inputPhoneTextWatcher(){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(StringUtils.hasValue(inputPhone.getText().toString(), true)){
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return tw;
    }
}
