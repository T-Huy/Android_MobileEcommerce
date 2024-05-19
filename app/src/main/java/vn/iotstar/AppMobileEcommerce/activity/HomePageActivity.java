package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.adapter.NavigationRecycleAdapter;
import vn.iotstar.AppMobileEcommerce.api.CustomerAPI;
import vn.iotstar.AppMobileEcommerce.api.LoginAPI;
import vn.iotstar.AppMobileEcommerce.fragment.HomeFragment;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.EShoppingModelClass;

import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;
import vn.iotstar.AppMobileEcommerce.service.JwtService;
import vn.iotstar.AppMobileEcommerce.sharedpreferences.SharedPreferencesManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawer;
    private ArrayList<EShoppingModelClass> eShoppingModelClasses;
    LinearLayout fregmentlayout;
    private NavigationRecycleAdapter mAdapter;
    NavigationView navigationView;
    private RecyclerView recyclerView2;

    static android.content.SharedPreferences pres_a;
    SharedPreferencesManager SharedPreferences = SharedPreferencesManager.getInstance(pres_a);

    private CustomerModel customer;

    private String username;
    private String email;
    TextView edtfullname;
    TextView edtemail;
    ImageView imgAvatar;

    CustomerAPI customerAPI = RetrofitClient.getRetrofit().create(CustomerAPI.class);

    private String[] title2 = {"Trang Chủ", "Giỏ Hàng", "Đơn Hàng", "Thương Hiệu", "Ưu Đãi", "Thông Tin"};

    private int[] image2 = {R.drawable.home_, R.drawable.cart_, R.drawable.shoppingbag, R.drawable.category, R.drawable.offer, R.drawable.profile_};
    private Toolbar toolbar;
    SharedPreferencesManager pres;
    ImageView ecart;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        pres = SharedPreferencesManager.getInstance(getSharedPreferences("jwt", MODE_PRIVATE));
//        checkJWT();
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_page);

        getWindow().setSoftInputMode(3);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setToolbar();

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        this.eShoppingModelClasses = new ArrayList<>();
        //Hien thi image, avatar, email tren navi
        edtfullname = findViewById(R.id.profile_fullname);
        edtemail = findViewById(R.id.profile_email);
        imgAvatar = findViewById(R.id.profile_image);
        pres = SharedPreferencesManager.getInstance(getSharedPreferences("Username", MODE_PRIVATE));
        callAPIGetUserName();
        //Hien thi icon va ten cua thanh ben
        for (int i = 0; i < this.title2.length; i++) {
            this.eShoppingModelClasses.add(new EShoppingModelClass(this.title2[i], this.image2[i]));
        }
        this.mAdapter = new NavigationRecycleAdapter(this, this.eShoppingModelClasses);
        this.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView2.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView2.setAdapter(this.mAdapter);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, this.toolbar, R.string.openDrawer, R.string.closeDrawer) { // from class: com.ecommerce.template.activity.HomePageActivity.1
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
            }
        };
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.drawer.setDrawerListener(actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        invalidateOptionsMenu();
        this.fregmentlayout = (LinearLayout) findViewById(R.id.fregmentlayout);
        this.ecart = findViewById(R.id.iv_ecart);
        this.ecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCart();
            }
        });

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(R.id.fregmentlayout, homeFragment, "Home Fragment");
        beginTransaction.commit();
    }

    private void gotoCart() {
        Intent intent = new Intent(this, MyCartActivity.class);
        startActivity(intent);
    }

    private void checkJWT() {
        try {
            String token = pres.getJWT();
            if (token != null) {
                if (!JwtService.validateTokenLogin(token)) {
                    gotoLogin();
                }
            }else{
                gotoLogin();
            }
        }catch (Exception e){
            Log.e("Lỗi",e.getMessage());
        }

    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }
        supportActionBar.setTitle("");
        this.toolbar.findViewById(R.id.navigation_menu).setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (HomePageActivity.this.drawer.isDrawerOpen(HomePageActivity.this.navigationView)) {
                    HomePageActivity.this.drawer.closeDrawer(HomePageActivity.this.navigationView);
                } else {
                    HomePageActivity.this.drawer.openDrawer(HomePageActivity.this.navigationView);
                }
            }
        });
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.actionBarDrawerToggle.syncState();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.actionBarDrawerToggle.onConfigurationChanged(configuration);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }

    @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }
    private void callAPIGetUserName() {
        String email = SharedPreferences.getEmail();
        customerAPI.getUserName(email).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    username= response.body().getMessage();
                    pres.saveUsername(username);
                    edtemail.setText(email);
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
    private void callAPI(String username) {
        customerAPI.getCustomerInfor(username).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getData());
                    customer = gson.fromJson(json, CustomerModel.class);
                    edtfullname.setText(customer.getFullname());
                }
                else {
                    customer = new CustomerModel();
                    Toast.makeText(HomePageActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomePageActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
