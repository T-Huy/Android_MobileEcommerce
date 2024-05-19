package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.adapter.AdminRecycleAdapterBrandsList;
import vn.iotstar.AppMobileEcommerce.adapter.AdminRecycleAdapterUserList;
import vn.iotstar.AppMobileEcommerce.api.BrandAPI;
import vn.iotstar.AppMobileEcommerce.api.CustomerAPI;
import vn.iotstar.AppMobileEcommerce.api.LoginAPI;
import vn.iotstar.AppMobileEcommerce.model.BrandsModel;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;

public class AdminPanelCustomerActivity extends AppCompatActivity {
    private List<UserModel> userModels;
    private AdminRecycleAdapterUserList mAdapter2;
    Button btnAdd;
    RecyclerView recyclerview;
    TextView title;
    LoginAPI userAPI;
    ImageView ivback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin_panel_customer);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerview = findViewById(R.id.recyclerViewUser);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        mAdapter2 = new AdminRecycleAdapterUserList(userModels, AdminPanelCustomerActivity.this);
//        recyclerview.setAdapter(mAdapter2);
        title = findViewById(R.id.title);
        this.title.setText("Quản lý người dùng");
        ivback = (ImageView) findViewById(R.id.iv_back);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelCustomerActivity.this, AdminPanelHomeActivity.class);
                startActivity(intent);
            }
        });

        userModels = new ArrayList<>();
        mAdapter2 = new AdminRecycleAdapterUserList(userModels, AdminPanelCustomerActivity.this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter2);

        GetUser();
    }

    private void GetUser(){
        userAPI= RetrofitClient.getRetrofit().create(LoginAPI.class);
        userAPI.getUser().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    userModels = response.body();
                    mAdapter2 = new AdminRecycleAdapterUserList(userModels, AdminPanelCustomerActivity.this);
                    recyclerview.setLayoutManager(new LinearLayoutManager(AdminPanelCustomerActivity.this));
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(mAdapter2);
                }else{
                    int statusCode = response.code();
                    Log.e("GetUser", "Response unsuccessful or body is null");
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.e("GetUser", "API call failed", t);
            }
        });
    }
}
