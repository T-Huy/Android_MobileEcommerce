package vn.iotstar.AppMobileEcommerce.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.adapter.AdminRecycleAdapterProductGrid;
import vn.iotstar.AppMobileEcommerce.adapter.RecycleAdapterProductGrid;
import vn.iotstar.AppMobileEcommerce.api.ProductAPI;
import vn.iotstar.AppMobileEcommerce.model.ProductGridModel;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;

public class AdminProductGridActivity extends AppCompatActivity {
    private List<ProductGridModel> productGridModelList;
    private AdminRecycleAdapterProductGrid mAdapter2;
    private RecyclerView recyclerview;
    ImageView iv_back;
    TextView title;
    ProductAPI productAPI;
    static int id;
    EditText inputSearch;
    Button btn_add;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_admin);
        anhXa();
        this.title.setText("Product Grid");
        Intent intent = getIntent();
        id = intent.getIntExtra("idBrand", 0);
        AdminProductGridActivity();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProductGridActivity.this.getBaseContext(), AddProductDialogActivity.class);
                intent.putExtra("idBrand", id);
                finish();
                startActivity(intent);
            }
        });

        //Search products by name
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = s.toString();
                // Gọi phương thức tìm kiếm sản phẩm với searchQuery
                searchProduct(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProductGridActivity.this, AdminPanelHomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void searchProduct(String searchQuery) {
        // Tìm kiếm sản phẩm theo tên
        List<ProductGridModel> productList = getProductListByName(searchQuery);

        // Cập nhật danh sách sản phẩm trong RecyclerView
        mAdapter2.setProductList(productList);
    }
    private List<ProductGridModel> getProductListByName(String searchQuery) {
        List<ProductGridModel> productList = new ArrayList<>();
        for (ProductGridModel product : productGridModelList) {
            if (product.getProductName().toLowerCase().contains(searchQuery.toLowerCase())) {
                productList.add(product);
            }
        }
        return productList;
    }
    @SuppressLint("NotConstructor")
    public void AdminProductGridActivity() {
        productAPI= RetrofitClient.getRetrofit().create(ProductAPI.class);
        productAPI.getProductByBrand(id).enqueue(new Callback<List<ProductGridModel>>() {
            @Override
            public void onResponse(Call<List<ProductGridModel>> call, Response<List<ProductGridModel>> response) {
                if(response.isSuccessful()){
                    productGridModelList = response.body();
                    mAdapter2 = new AdminRecycleAdapterProductGrid(productGridModelList, AdminProductGridActivity.this);
                    recyclerview.setLayoutManager(new GridLayoutManager(AdminProductGridActivity.this, 2));
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(mAdapter2);
                }else{
                    int statusCode = response.code();
                }
            }
            @Override
            public void onFailure(Call<List<ProductGridModel>> call, Throwable t) {
            }
        });
    }
    void anhXa(){
        recyclerview = findViewById(R.id.recyclerview_product_grid);
        title = findViewById(R.id.title);
        iv_back = findViewById(R.id.iv_back);
        inputSearch = findViewById(R.id.search_editext);
        btn_add = findViewById((R.id.btn_add));
    }
}
