package vn.iotstar.AppMobileEcommerce.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.api.CustomerAPI;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;
import vn.iotstar.AppMobileEcommerce.sharedpreferences.SharedPreferencesManager;
import com.google.gson.Gson;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {
    TextView title;
    ImageView iv_back;

    TextView tvUsername;

    EditText edtFullname;
    EditText edtPhone;
    EditText edtAddress;

    EditText edtProvince;
    EditText edtDistrict;
    EditText edtSub_district;

    ImageView imgAvatar;

    Button btnLoadImage;
    Button btnChangePassword;
    Button btnUpdateProfile;

    Button btnLogout;
    Context context;

    CustomerAPI customerAPI = RetrofitClient.getRetrofit().create(CustomerAPI.class);

    private CustomerModel customer;

    private ProgressDialog progressDialog;

    private String username;
    static SharedPreferences pres;
    SharedPreferencesManager SharedPreferences = SharedPreferencesManager.getInstance(pres);

    SharedPreferencesManager pres1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);
        //
        this.title = (TextView) findViewById(R.id.title);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        mapView();

        context = this;
        pres1 = SharedPreferencesManager
                .getInstance(getSharedPreferences("Username", MODE_PRIVATE));
        callAPIGetUserName();

        this.title.setText("My Profile");
        //Click doi passworrd
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, UpdatePasswordActivity.class));
            }
        });
        //Clicl doi image
        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, UploadImageActivity.class));
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeInfor();
            }
        });
        //CLick logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData();
                gotoLogin();
            }
        });
    }
    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void removeData() {
        SharedPreferences.removeJWT();
        SharedPreferences.removeEmail();
        //SharedPreferences.removeUsername();
    }
    private void callAPIGetUserName() {
        String email = SharedPreferences.getEmail();
        customerAPI.getUserName(email).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    username= response.body().getMessage();
                    pres1.saveUsername(username);
                    callAPI(username);
                    getAvatar(username);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Log.e("CALL API get Username","Fail");
            }
        });
    }


    private void mapView() {
        btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        btnChangePassword = (Button) findViewById(R.id.btnChangePwd);
        btnUpdateProfile = (Button) findViewById(R.id.btn_update_profile);
        tvUsername = findViewById(R.id.tv_username);
        edtFullname = findViewById(R.id.edt_fullname);
        edtAddress = findViewById(R.id.edt_address);
        edtPhone = findViewById(R.id.edt_sodienthoai);
        edtProvince = findViewById(R.id.edt_province);
        edtDistrict = findViewById(R.id.edt_district);
        edtSub_district = findViewById(R.id.edt_subdistrict);
        imgAvatar = findViewById(R.id.profileImage);
        btnLogout = findViewById(R.id.btn_logout_profile);
    }

    private void callAPI(String username) {
        customerAPI.getCustomerInfor(username).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getData());
                    customer = gson.fromJson(json, CustomerModel.class);
                    tvUsername.setText(customer.getUserName());
                    edtFullname.setText(customer.getFullname());
                    edtPhone.setText(customer.getPhonenumber());
                    edtAddress.setText(customer.getAddress());
                    edtProvince.setText(customer.getProvince());
                    edtDistrict.setText(customer.getDistrict());
                    edtSub_district.setText(customer.getSubdistrict());
                }
                else {
                    customer = new CustomerModel();
                    Toast.makeText(ProfileActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAvatar(String username) {
        customerAPI.getImage(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    ResponseBody body = response.body();
                    InputStream inputStream = body.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAvatar.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeInfor() {
        customer = new CustomerModel();
        customer.setAddress(edtAddress.getText().toString());
        customer.setUserName(tvUsername.getText().toString());
        customer.setFullname(edtFullname.getText().toString());
        customer.setPhonenumber(edtPhone.getText().toString());
        customer.setProvince(edtProvince.getText().toString());
        customer.setDistrict(edtDistrict.getText().toString());
        customer.setSubdistrict(edtSub_district.getText().toString());
        customerAPI.updateCustomer(customer).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.isSuccessful())
                {
                    System.out.println("B");
                    System.out.println("Cập nhật thông tin thành công");
                    Toast.makeText(ProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println("A");
                System.out.println("Cập nhật thất bại");
                Toast.makeText(ProfileActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}