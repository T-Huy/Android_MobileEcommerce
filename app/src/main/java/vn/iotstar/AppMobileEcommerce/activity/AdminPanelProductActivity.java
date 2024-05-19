package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import vn.iotstar.AppMobileEcommerce.adapter.AdminRecycleAdapterProductList;
import vn.iotstar.AppMobileEcommerce.api.BrandAPI;
import vn.iotstar.AppMobileEcommerce.model.BrandsModel;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;

public class AdminPanelProductActivity extends AppCompatActivity {

    private List<BrandsModel> brandsModels;
    private AdminRecycleAdapterProductList mAdapter2;
    Button btnAdd;
    RecyclerView recyclerview;
    TextView title;
    BrandAPI brandAPI;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin_panel_brand);


        title = (TextView) findViewById(R.id.title);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerViewUser);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setVisibility(View.GONE);
        this.title.setText("Quản lý sản phẩm");
        brandsModels = new ArrayList<>();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelProductActivity.this, AdminPanelHomeActivity.class);
                startActivity(intent);
            }
        });
        GetBrands();

    }
    private void GetBrands(){
        brandAPI= RetrofitClient.getRetrofit().create(BrandAPI.class);
        brandAPI.getBrands().enqueue(new Callback<List<BrandsModel>>() {
            @Override
            public void onResponse(Call<List<BrandsModel>> call, Response<List<BrandsModel>> response) {
                if(response.isSuccessful()){
                    brandsModels = response.body();
                    mAdapter2 = new AdminRecycleAdapterProductList(brandsModels, AdminPanelProductActivity.this);
                    recyclerview.setLayoutManager(new LinearLayoutManager(AdminPanelProductActivity.this));
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(mAdapter2);
                }else{
                    int statusCode = response.code();
                }
            }
            @Override
            public void onFailure(Call<List<BrandsModel>> call, Throwable t) {

            }
        });
    }


}
