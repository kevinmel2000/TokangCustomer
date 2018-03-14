package com.tokang.customer.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tokang.customer.HomeActivity;
import com.tokang.customer.R;
import com.tokang.customer.constants.AppConstants;
import com.tokang.customer.utils.StringUtils;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    private EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;
    private TextView lblPhoneNumber, lblResendCodeTimer;
    private Button btnSend;
    private String phoneNumber;
    private static final String FORMAT = "%02d:%02d";

    // Firebase Phone Auth
    private static final int STATE_VERIFY_FAILED = 1;
    private static final int STATE_VERIFY_SUCCESS = 2;
    private static final int STATE_SIGN_IN_FAILED = 3;
    private static final int STATE_SIGN_IN_SUCCESS = 4;

    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setFindViewById();

        btnSend.setOnClickListener(btnSendListener());
        inputOtp1.addTextChangedListener(otpTextWatcher());
        inputOtp2.addTextChangedListener(otpTextWatcher());
        inputOtp3.addTextChangedListener(otpTextWatcher());
        inputOtp4.addTextChangedListener(otpTextWatcher());
        inputOtp5.addTextChangedListener(otpTextWatcher());
        inputOtp6.addTextChangedListener(otpTextWatcher());

        if(getIntent() != null){
            phoneNumber = getIntent().getStringExtra(AppConstants.PHONE_NUMBER);
        }

        lblPhoneNumber.setText(phoneNumber);

        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                updateUI(STATE_VERIFY_SUCCESS, phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(OTPActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else if(e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTPActivity.this, "Quota exceeded", Toast.LENGTH_SHORT).show();
                }

                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
            }
        };

        setCountDownTimer();

        startPhoneNumberVerification(phoneNumber);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();

                            updateUI(STATE_SIGN_IN_SUCCESS, user);
                        } else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                            }

                            updateUI(STATE_SIGN_IN_FAILED);
                        }
                    }
                });
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_VERIFY_FAILED:
                Toast.makeText(this, "STATE_VERIFY_FAILED", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case STATE_VERIFY_SUCCESS:
                if(cred != null) {
                    if(cred.getSmsCode() !=  null) {
                        String smsCode = cred.getSmsCode();
                        inputOtp1.setText(smsCode.substring(0));
                        inputOtp2.setText(smsCode.substring(1));
                        inputOtp3.setText(smsCode.substring(2));
                        inputOtp4.setText(smsCode.substring(3));
                        inputOtp5.setText(smsCode.substring(4));
                        inputOtp6.setText(smsCode.substring(5));
                    }
                }
                break;
            case STATE_SIGN_IN_FAILED:
                Toast.makeText(this, "STATE_SIGN_IN_FAILED", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case STATE_SIGN_IN_SUCCESS:
                Intent home = new Intent(OTPActivity.this, HomeActivity.class);
                home.putExtra("uId", user.getUid());
                startActivity(home);
                finishAffinity();
                break;
        }
    }

    private void setCountDownTimer(){
        new CountDownTimer(90000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                long time_left = millisUntilFinished/1000;
                lblResendCodeTimer.setText(secondsToMinutes(time_left));
            }

            @Override
            public void onFinish() {
                lblResendCodeTimer.setText(secondsToMinutes(0l));
                resendVerificationCode(phoneNumber, mResendToken);
            }
        }.start();
    }

    private String secondsToMinutes(long time){
        int mins = (int) time / 60;
        int remainder = (int) time - mins * 60;
        int secs = remainder;

        return String.format(FORMAT, mins, secs);
    }

    private void setFindViewById(){
        inputOtp1 = findViewById(R.id.input_otp_1);
        inputOtp2 = findViewById(R.id.input_otp_2);
        inputOtp3 = findViewById(R.id.input_otp_3);
        inputOtp4 = findViewById(R.id.input_otp_4);
        inputOtp5 = findViewById(R.id.input_otp_5);
        inputOtp6 = findViewById(R.id.input_otp_6);
        btnSend = findViewById(R.id.btn_send);
        lblPhoneNumber = findViewById(R.id.phone_number_label);
        lblResendCodeTimer = findViewById(R.id.resend_code_timer);
    }

    private TextWatcher otpTextWatcher() {
       TextWatcher tw = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               String otp_1 = inputOtp1.getText().toString();
               String otp_2 = inputOtp2.getText().toString();
               String otp_3 = inputOtp3.getText().toString();
               String otp_4 = inputOtp4.getText().toString();
               String otp_5 = inputOtp5.getText().toString();
               String otp_6 = inputOtp6.getText().toString();

               if(StringUtils.hasValue(otp_1,true) && inputOtp1.hasFocus()){
                   inputOtp2.requestFocus();
               } else if(StringUtils.hasValue(otp_2,true) && inputOtp2.hasFocus()){
                   inputOtp3.requestFocus();
               } else if(StringUtils.hasValue(otp_3,true) && inputOtp3.hasFocus()){
                   inputOtp4.requestFocus();
               } else if(StringUtils.hasValue(otp_4,true) && inputOtp4.hasFocus()){
                   inputOtp5.requestFocus();
               } else if(StringUtils.hasValue(otp_5,true) && inputOtp5.hasFocus()){
                   inputOtp6.requestFocus();
               }
               if(StringUtils.hasValue(otp_1,true) && StringUtils.hasValue(otp_2,true)
                       && StringUtils.hasValue(otp_3,true) && StringUtils.hasValue(otp_4,true)
                       && StringUtils.hasValue(otp_5, true) && StringUtils.hasValue(otp_6, true)){
                   btnSend.setEnabled(true);
               }else{
                   btnSend.setEnabled(false);
               }
           }

           @Override
           public void afterTextChanged(Editable editable) {
           }
       };

       return tw;
    }

    private View.OnClickListener btnSendListener(){

        View.OnClickListener vo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp1 = inputOtp1.getText().toString();
                String otp2 = inputOtp2.getText().toString();
                String otp3 = inputOtp3.getText().toString();
                String otp4 = inputOtp4.getText().toString();
                String otp5 = inputOtp5.getText().toString();
                String otp6 = inputOtp6.getText().toString();

                String code = otp1+otp2+otp3+otp4+otp5+otp6;

                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        };

        return vo;
    }

}
