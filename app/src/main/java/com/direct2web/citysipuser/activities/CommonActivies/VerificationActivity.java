package com.direct2web.citysipuser.activities.CommonActivies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.ActivityVerificationBinding;
import com.direct2web.citysipuser.model.RestaurentModels.OtpSend.ResponseOtpSend;
import com.direct2web.citysipuser.model.RestaurentModels.OtpSend.ResponseVerifyPassword;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.ConnectionToInternet;
import com.direct2web.citysipuser.utils.CustomTextChangeListener;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    ActivityVerificationBinding binding;
    SessionManager sessionManager;

    String strOtp, otp;
    String strMsg;
    String strUsername;
    ProgressDialog pd;

    public int counter = 30;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification);

        sessionManager = new SessionManager(this);

        strUsername = getIntent().getStringExtra("username");
        strMsg = getIntent().getStringExtra("msg");
        otp = getIntent().getStringExtra("otp");

        binding.edtNumber.setText(strUsername);

        resendotp();
        reverseTimer(counter,binding.txtTime,binding.btnResend);

        binding.edtOne.addTextChangedListener(new CustomTextChangeListener(VerificationActivity.this,binding.edtOne, binding.edtTwo, binding.edtOne,false,binding.btnVerify));
        binding.edtTwo.addTextChangedListener(new CustomTextChangeListener(VerificationActivity.this,binding.edtTwo, binding.edtThree, binding.edtOne,false,binding.btnVerify));
        binding.edtThree.addTextChangedListener(new CustomTextChangeListener(VerificationActivity.this,binding.edtThree, binding.edtFour, binding.edtTwo,false,binding.btnVerify));
        binding.edtFour.addTextChangedListener(new CustomTextChangeListener(VerificationActivity.this,binding.edtFour, binding.edtFour, binding.edtThree,true,binding.btnVerify));


        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ConnectionToInternet(VerificationActivity.this).isConnectingToInternet()) {
                    sendData();
                } else {
                    new ConnectionToInternet(VerificationActivity.this).ShowDilog(VerificationActivity.this);
                }
            }
        });

        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ConnectionToInternet(VerificationActivity.this).isConnectingToInternet()) {
                    reverseTimer(counter,binding.txtTime,binding.btnResend);
                    resendotp();
                } else {
                    new ConnectionToInternet(VerificationActivity.this).ShowDilog(VerificationActivity.this);
                }
            }
        });

    }

    public void reverseTimer(int Seconds, final TextView tv, final TextView tv2) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                tv.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                tv.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds )+ " resend OTP after");
            }

            public void onFinish() {
                tv.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void resendotp() {

        pd = new ProgressDialog(VerificationActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseOtpSend> call = api.sendOtp("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, strUsername, otp);
        call.enqueue(new Callback<ResponseOtpSend>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOtpSend> call, @NonNull Response<ResponseOtpSend> response) {

                Log.e("response_otpsend", new Gson().toJson(response.body()));

                pd.dismiss();
                if (response.body() != null) {

                    if (!response.body().getError()) {

                        Toast.makeText(VerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(VerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOtpSend> call, @NonNull Throwable t) {
                pd.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void sendData() {

        pd = new ProgressDialog(VerificationActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        if (flag == 0){

            strOtp = binding.edtOne.getText().toString() + binding.edtTwo.getText().toString() + binding.edtThree.getText().toString() + binding.edtFour.getText().toString();

            if (!strOtp.equals(otp)) {
                pd.dismiss();
                //binding.editTextCode.setError("Please Enter Valid OTP");
                Toast.makeText(VerificationActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();


            } else if (strOtp.equals(getIntent().getStringExtra("otp"))) {
                pd.dismiss();
                sendUserData(strUsername,"");

            }
        }
    }

    private void sendUserData(String strUsername,String strpassword) {


        pd = new ProgressDialog(VerificationActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseVerifyPassword> call = api.sendVerifyPassword("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, strUsername,strpassword);
        call.enqueue(new Callback<ResponseVerifyPassword>() {
            @Override
            public void onResponse(@NonNull Call<ResponseVerifyPassword> call, @NonNull Response<ResponseVerifyPassword> response) {

                Log.e("responseCreateAccount", new Gson().toJson(response.body()));
                pd.dismiss();

                if (response.body() != null && response.isSuccessful()) {

                    if (!response.body().getError()) {

                        sessionManager.setUserId(response.body().getUserId());
                        sessionManager.setUserContact(strUsername);
                        sessionManager.setLogin(true);

                        Intent i = new Intent(VerificationActivity.this, CategoryActivity.class);
                        finish();
                        startActivity(i);

                    } else {

                        Toast.makeText(VerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(VerificationActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseVerifyPassword> call, @NonNull Throwable t) {
                pd.dismiss();
                t.printStackTrace();
                Log.e("errorCreateAccount", t.getMessage());
            }
        });
    }

    private class GenericTextWatcher implements TextWatcher {
        private EditText currentView;
        private EditText nextView;

        private GenericTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            if (nextView != null && text.length() == 1) {
                nextView.requestFocus();

            }
            if (text.length() > 1) {
                currentView.setText(String.valueOf(text.charAt(text.length() - 1)));
                currentView.setSelection(1);
            }

            if (text.length() > 0) {
                currentView.setBackground(getResources().getDrawable(R.drawable.four_dp_corner_box_green));
            } else {
                currentView.setBackground(getResources().getDrawable(R.drawable.four_dp_corner_box_two_dp_thik));

            }

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    private static class GenericKeyEvent implements View.OnKeyListener {

        private EditText currentView;
        private EditText previousView;

        public GenericKeyEvent(EditText currentView, EditText previousView) {
            this.currentView = currentView;
            this.previousView = previousView;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DEL && currentView.getText().toString().isEmpty()) {
                if (previousView != null) {
                    previousView.requestFocus();
                }
                return true;
            }
            return false;
        }
    }

    private class GenericTextWatcher2 implements TextWatcher {

        private View view;
        EditText etNext, etPrev;
        EditText currentView;
        boolean flag= false;

        public GenericTextWatcher2(EditText currentView, EditText etNext, EditText etPrev,boolean flag) {
            this.etPrev = etPrev;
            this.etNext = etNext;
            this.currentView = currentView;
            this.flag=flag;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = s.toString();
            if (text.length() == 1) {
                etNext.requestFocus();
            } else {
                etPrev.requestFocus();
            }

            /*if (text.length() > 0) {
                currentView.setBackground(getResources().getDrawable(R.drawable.four_dp_corner_box_green));
            } else {
                currentView.setBackground(getResources().getDrawable(R.drawable.four_dp_corner_box_two_dp_thik));

            }*/
            if (flag){
                closeKeyboard();
                binding.btnVerify.setTextColor(getResources().getColor(R.color.clr_f8f8f8));
                binding.btnVerify.setBackground(getResources().getDrawable(R.drawable.button));

            }else {

                binding.btnVerify.setTextColor(getResources().getColor(R.color.cle_979592));
                binding.btnVerify.setBackground(getResources().getDrawable(R.drawable.button_disable));


            }



        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}