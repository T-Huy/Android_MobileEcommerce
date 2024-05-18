package vn.iotstar.AppMobileEcommerce.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.activity.EditBrandDialogActivity;
import vn.iotstar.AppMobileEcommerce.model.BrandsModel;

import java.util.List;

public class AdminRecycleAdapterBrandsList extends RecyclerView.Adapter<AdminRecycleAdapterBrandsList.MyViewHolder> {
    private List<BrandsModel> brandsList;
    private Context context;

    public AdminRecycleAdapterBrandsList(List<BrandsModel> brandsList, Context context) {
        this.brandsList = brandsList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdminRecycleAdapterBrandsList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_list,
                parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdminRecycleAdapterBrandsList.MyViewHolder holder, int position) {
        BrandsModel brand = brandsList.get(position);
        holder.title.setText(brand.getName());
        Glide.with(context)
                .load(brand.getLogo())
                .into(holder.images);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), EditBrandDialogActivity.class);
                intent.putExtra("brand", brand);
                ((Activity) context).finish();
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(brandsList!= null){
            return brandsList.size();
        }
        return 0;
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
}
