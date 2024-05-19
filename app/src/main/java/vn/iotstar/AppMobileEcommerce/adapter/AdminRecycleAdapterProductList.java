package vn.iotstar.AppMobileEcommerce.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.activity.AdminProductGridActivity;
import vn.iotstar.AppMobileEcommerce.activity.EditBrandDialogActivity;
import vn.iotstar.AppMobileEcommerce.activity.ProductDetailActivity;
import vn.iotstar.AppMobileEcommerce.activity.ProductGridActivity;
import vn.iotstar.AppMobileEcommerce.api.ProductAPI;
import vn.iotstar.AppMobileEcommerce.model.BrandsModel;
import vn.iotstar.AppMobileEcommerce.model.HomeViewModelClass;
import vn.iotstar.AppMobileEcommerce.model.ProductGridModel;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;

public class AdminRecycleAdapterProductList extends RecyclerView.Adapter<AdminRecycleAdapterProductList.MyViewHolder>{
    private List<BrandsModel> brandsList;
    private Context context;


    public AdminRecycleAdapterProductList(List<BrandsModel> brandsList, Context context) {
        this.brandsList = brandsList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdminRecycleAdapterProductList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_list,
                parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AdminRecycleAdapterProductList.MyViewHolder holder, int position) {
        BrandsModel brand = brandsList.get(position);
        holder.title.setText(brand.getName());
        Glide.with(context)
                .load(brand.getLogo())
                .into(holder.images);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý show sản phẩm
                Intent intent = new Intent(context, AdminProductGridActivity.class);
                // Truyền thông tin brand vào Intent
                intent.putExtra("idBrand", brand.getBrandId());

                // Khởi động Activity
                context.startActivity(intent);
            }
        });
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        private TextView title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.image_brand_products);
            title=itemView.findViewById(R.id.title_brand_products);
        }
    }


    @Override
    public int getItemCount() {
        if(brandsList!= null){
            return brandsList.size();
        }
        return 0;
    }
}
