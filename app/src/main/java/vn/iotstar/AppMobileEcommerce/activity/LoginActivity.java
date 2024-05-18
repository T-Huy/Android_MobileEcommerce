package vn.iotstar.AppMobileEcommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.api.CustomerAPI;
import vn.iotstar.AppMobileEcommerce.api.LoginAPI;
import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitForLogin;
import vn.iotstar.AppMobileEcommerce.service.JwtService;
import vn.iotstar.AppMobileEcommerce.sharedpreferences.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferencesManager SharedPreferencesSaveToken;
    SharedPreferencesManager SharedPreferencesSaveEmail;

    SharedPreferencesManager SharedPreferencesSaveUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView signuptxt = (TextView) findViewById(R.id.signup);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        SharedPreferencesSaveToken = SharedPreferencesManager
                .getInstance(getSharedPreferences("jwt", MODE_PRIVATE));
        SharedPreferencesSaveEmail = SharedPreferencesManager
                .getInstance(getSharedPreferences("email", MODE_PRIVATE));
        SharedPreferencesSaveUsername = SharedPreferencesManager
                .getInstance(getSharedPreferences("Username", MODE_PRIVATE));
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallLoginAPI(username.getText().toString().trim(), password.getText().toString().trim());
            }
        });

        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CallLoginAPI(String username, String password) {

        // Get data
        UserModel user = new UserModel(username, password);
        LoginAPI loginAPI = RetrofitForLogin.getRetrofitForLogin().create(LoginAPI.class);
        loginAPI.Login(user).enqueue(new Callback<ResponseDTO>() {

            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    ResponseDTO responseDTO = response.body();
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    saveJWT(responseDTO.getMessage());
                    saveEmail(username);
                    callAPIGetUsername(username);
                    gotoHome(responseDTO.getMessage());
                } else{
                    Toast.makeText(LoginActivity.this, "Wrong When You Login!!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Log.e("error when login",t.getMessage());
            }
        });

    }

    private void callAPIGetUsername(String email) {
        CustomerAPI customerAPI = RetrofitForLogin.getRetrofitForLogin().create(CustomerAPI.class);
        customerAPI.getUserName(email).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    Log.e("USERNAME CALL LOGIN", response.body().getMessage());
                    saveUsername(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Log.e("CALL API get Username","Fail");
            }
        });
    }

    private void gotoHome(String token) {
        String role = JwtService.getRoleFromToken(token);
        Log.e("ROLE", "là "+ role);
        Intent intent;
        if(role.equals("ROLE_ADMIN")){
            intent = new Intent(this, AdminPanelHomeActivity.class);
        }else if(role.equals("ROLE_MANAGER")){
            intent = new Intent(this, ManageOrderActivity.class);
        }else if(role.equals("ROLE_SHIPPER")){
            intent = new Intent(this,ShipOrderActivity.class);
        }else {
            intent = new Intent(this, HomePageActivity.class);
        }
        startActivity(intent);
    }
    private void saveJWT(String token) {
        SharedPreferencesSaveToken.saveJWT(token);
    }
    private void saveEmail(String email) {
        SharedPreferencesSaveEmail.saveEmail(email);
    }
    private void saveUsername(String username) {
        SharedPreferencesSaveUsername.saveUsername(username);
        Log.e("username là:", username);
    }
}