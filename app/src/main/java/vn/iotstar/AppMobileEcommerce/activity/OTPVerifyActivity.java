package vn.iotstar.AppMobileEcommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.api.SignUpAPI;
import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_otpverify);

        //Component
        EditText inputOTP1 = (EditText) findViewById(R.id.inputotp1);
        EditText inputOTP2 = (EditText) findViewById(R.id.inputotp2);
        EditText inputOTP3 = (EditText) findViewById(R.id.inputotp3);
        EditText inputOTP4 = (EditText) findViewById(R.id.inputotp4);
        EditText inputOTP5 = (EditText) findViewById(R.id.inputotp5);
        EditText inputOTP6 = (EditText) findViewById(R.id.inputotp6);

        inputOTP1.addTextChangedListener(new OtpTextWatcher(inputOTP1, inputOTP2));
        inputOTP2.addTextChangedListener(new OtpTextWatcher(inputOTP2, inputOTP3));
        inputOTP3.addTextChangedListener(new OtpTextWatcher(inputOTP3, inputOTP4));
        inputOTP4.addTextChangedListener(new OtpTextWatcher(inputOTP4, inputOTP5));
        inputOTP5.addTextChangedListener(new OtpTextWatcher(inputOTP5, inputOTP6));
        inputOTP6.addTextChangedListener(new OtpTextWatcher(inputOTP6, null));

        Button verifyOTP = (Button) findViewById(R.id.btnsubmit);
        TextView re_SendOTP = (TextView) findViewById(R.id.sendotp_again);

        //Create OTP
        String OTP = inputOTP1.getText().toString()
                + inputOTP2.getText().toString()
                + inputOTP3.getText().toString()
                + inputOTP4.getText().toString()
                + inputOTP5.getText().toString()
                + inputOTP6.getText().toString();

        //Get User Form Register Activity
        UserModel user = (UserModel) getIntent().getSerializableExtra("user");

        //Event Vefify
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOTP(inputOTP1,
                        inputOTP2,
                        inputOTP3,
                        inputOTP4,
                        inputOTP5,
                        inputOTP6)){

                    //Create OTP
                    String OTP = inputOTP1.getText().toString()
                            + inputOTP2.getText().toString()
                            + inputOTP3.getText().toString()
                            + inputOTP4.getText().toString()
                            + inputOTP5.getText().toString()
                            + inputOTP6.getText().toString();

                    CallAPIVerifyOTP(Integer.parseInt(OTP), user.getEmail());
                } else {
                    Toast.makeText(OTPVerifyActivity.this, "Please enter all 6 digits", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // re-send OTP
        re_SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity registerActivity = new RegisterActivity();
                registerActivity.CallSignUpAPI(user);
            }
        });
    }

    private void CallAPIVerifyOTP(Integer OTP, String email) {
        SignUpAPI signUpAPI = RetrofitClient.getRetrofit().create(SignUpAPI.class);

        signUpAPI.Verify(OTP, email).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    String message = response.body().getMessage();
                    Toast.makeText(OTPVerifyActivity.this, message , Toast.LENGTH_LONG).show();
                    gotoLoginForm();
                } else{
                    Toast.makeText(OTPVerifyActivity.this, "Fail To Verify OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(OTPVerifyActivity.this, "Error When Verify OTP", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void gotoLoginForm() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

    private boolean checkOTP(EditText opt1,
                             EditText opt2,
                             EditText opt3,
                             EditText opt4,
                             EditText opt5,
                             EditText opt6) {
        if(!opt1.getText().toString().isEmpty()
                && !opt2.getText().toString().isEmpty()
                && !opt3.getText().toString().isEmpty()
                && !opt4.getText().toString().isEmpty()
                && !opt5.getText().toString().isEmpty()
                && !opt6.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
    private class OtpTextWatcher implements TextWatcher {
        private final EditText currentView;
        private final EditText nextView;

        public OtpTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}