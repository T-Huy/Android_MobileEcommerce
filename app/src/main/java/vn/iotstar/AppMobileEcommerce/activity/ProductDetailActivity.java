package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.adapter.RecycleAdapterOptionList;
import vn.iotstar.AppMobileEcommerce.adapter.ReviewsRecycleAdapter;
import vn.iotstar.AppMobileEcommerce.adapter.ViewPagerAdapter;
import vn.iotstar.AppMobileEcommerce.api.ReviewAPI;
import vn.iotstar.AppMobileEcommerce.model.OptionModel;
import vn.iotstar.AppMobileEcommerce.model.ProductGridModel;
import vn.iotstar.AppMobileEcommerce.model.ReviewModel;
import vn.iotstar.AppMobileEcommerce.model.cartRoomDatabase.ItemDatabase;
import vn.iotstar.AppMobileEcommerce.model.cartRoomDatabase.entity.Item;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;
import vn.iotstar.AppMobileEcommerce.sharedpreferences.SharedPreferencesManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes.dex */
public class ProductDetailActivity extends AppCompatActivity {
    ImageView iv_back;
    RatingBar ratingBar;
    TextView title,tvName, tvPrice, tvDescription;
    Button addToCart;
    ViewPager viewPager;
    ProductGridModel product;
    RecyclerView rc_view;
    RecycleAdapterOptionList recycleAdapterOptionList;
    private int oId=0;
    RecyclerView rcvReview;
    private ReviewsRecycleAdapter reviewsRecycleAdapter;
    ReviewAPI reviewAPI = RetrofitClient.getRetrofit().create(ReviewAPI.class);
    List<ReviewModel> listReview;
    ViewPagerAdapter mViewPagerAdapter;
    private String username;
    ImageView favorite;
    LinearLayout rating_layout;

    SharedPreferencesManager pres;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_detail);
        anhXa();
        //get username
        pres = SharedPreferencesManager
                .getInstance(getSharedPreferences("Username", MODE_PRIVATE));
        username = pres.getUsername();
        Intent intent = getIntent();
        product = (ProductGridModel) intent.getSerializableExtra("product");
        loadProductDetail();
        // Recycle Review Set Up
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rcvReview.setLayoutManager(linearLayoutManager1);
        rcvReview.setItemAnimator(new DefaultItemAnimator());
        AddReviews();
        //view Pager
        mViewPagerAdapter = new ViewPagerAdapter(ProductDetailActivity.this, product, LayoutInflater.from(this));
        viewPager.setAdapter(mViewPagerAdapter);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(this.viewPager);
        recycleAdapterOptionList = new RecycleAdapterOptionList(this, product.getOptions(), new RecycleAdapterOptionList.ItemClickListener() {
            @Override
            public void onClick(int id) {
                oId= id;
                //Toast.makeText(ProductDetailActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                rc_view.post(new Runnable() {
                    @Override
                    public void run() {
                        recycleAdapterOptionList.notifyDataSetChanged();
                    }
                });
            }
        });
        rc_view.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setAdapter(recycleAdapterOptionList);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, MyCartActivity.class);
                //intent.putExtra("product", product);
                Log.e("ID khi add cart","là " + product.getProductId());
                addItem(oId);
                startActivity(intent);
            }
        });

//        btnAddReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openReviewDialog();
//            }
//        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

//    private void openReviewDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_review_product);
//        Window window = dialog.getWindow();
//        if(window == null) {
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = Gravity.CENTER;
//        window.setAttributes(windowAttributes);
//
//        Button btnNoThank = dialog.findViewById(R.id.btn_no_thank);
//        SeekBar sbRate = dialog.findViewById(R.id.sb_rate_review);
//        EditText edtContent = dialog.findViewById(R.id.edt_content_review);
//        Button btnSendReview = dialog.findViewById(R.id.btn_send_review);
//
//        btnNoThank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        btnSendReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ReviewModel reviewModel = new ReviewModel();
//                reviewModel.setContent(edtContent.getText().toString());
//                reviewModel.setRate(sbRate.getProgress() + 1);
//                reviewModel.setProduct(new ProductGridModel(product.getProductId()));
//                reviewModel.setCustomer(new CustomerModel(username));
//                reviewAPI.insertReview(reviewModel).enqueue(new Callback<ReviewModel>() {
//                    @Override
//                    public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
//                        listReview.add(0, response.body());
//                        reviewsRecycleAdapter.notifyItemInserted(0);
//                        Log.d("OKE", "OKE");
//                    }
//                    @Override
//                    public void onFailure(Call<ReviewModel> call, Throwable t) {
//
//                    }
//                });
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }


    private void AddReviews() {
        reviewAPI.getReviewsByProductId(product.getProductId(), username).enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                listReview = response.body();
                float rate=0;
                for(int i=0; i<listReview.size();i++){
                    rate += listReview.get(i).getRate();
                }
                ratingBar.setRating((float) rate/(float) listReview.size());
                reviewsRecycleAdapter = new ReviewsRecycleAdapter(listReview, ProductDetailActivity.this);
                rcvReview.setAdapter(reviewsRecycleAdapter);
            }

            @Override
            public void onFailure(Call<List<ReviewModel>> call, Throwable t) {

            }
        });
    }

    private void addItem(int optionId){
        if(isCheckExist(product.getProductId(), optionId)!=null){
            Item uitem = isCheckExist(product.getProductId(), optionId);
            uitem.setQuantity(uitem.getQuantity()+1);
            ItemDatabase.getInstance(this).itemDao().updateItem(uitem);
            return;
        }
        String pName = product.getProductName();
        int pQuantity = 1;
        OptionModel optionModel = product.getOptions().get(oId);
        double pPrice = optionModel.getPrice();
        String image = optionModel.getImages().get(0).getPath();
        Item items = new Item(product.getProductId(), optionId,pName,image,pQuantity,
                optionModel.getRam(),optionModel.getRom(),pPrice);
        ItemDatabase.getInstance(this).itemDao().insertAll(items);
    }

    private Item isCheckExist(@NotNull int productId,@NotNull int optionId){
        Item list = ItemDatabase.getInstance(this).itemDao().checkItem(productId, optionId);
        if(list!=null){
            return list;
        }
        return null;
    }
    void loadProductDetail(){
        tvName.setText(product.getProductName());
        tvPrice.setText(String.valueOf(product.getPrice())+" $");
        tvDescription.setText(product.getDescription());
    }
    void anhXa(){
        title = findViewById(R.id.title);
        title.setText("Product Detail");
        iv_back = findViewById(R.id.iv_back);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        addToCart = findViewById(R.id.btn_addtocart);
        viewPager = findViewById(R.id.viewPager);
        rc_view = findViewById(R.id.rc_view_option);
        rcvReview = findViewById(R.id.recyclerview_review);
//        btnAddReview = findViewById(R.id.btn_add_review);
        ratingBar = findViewById(R.id.ratingbar);
        rating_layout = (LinearLayout) findViewById(R.id.rating_layout);
        favorite = (ImageView) findViewById(R.id.favorite);
//        addToCart.setVisibility(View.VISIBLE);
//        rating_layout.setVisibility(View.VISIBLE);
//        favorite.setVisibility(View.VISIBLE);
    }
}
