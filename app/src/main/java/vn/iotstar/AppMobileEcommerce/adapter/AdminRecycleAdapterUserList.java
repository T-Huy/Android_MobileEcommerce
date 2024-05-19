package vn.iotstar.AppMobileEcommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.activity.EditCustomerDialogActivity;
import vn.iotstar.AppMobileEcommerce.model.UserModel;

public class AdminRecycleAdapterUserList extends RecyclerView.Adapter<AdminRecycleAdapterUserList.MyViewHolder> {
    private List<UserModel> users;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userEmail;

        public MyViewHolder(@NonNull View v) {
            super(v);
            userName = v.findViewById(R.id.userName);
            userEmail = v.findViewById(R.id.userEmail);
        }
    }

    public AdminRecycleAdapterUserList(List<UserModel> users, Context context) {
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public AdminRecycleAdapterUserList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserModel user = users.get(position);
        holder.userName.setText(user.getUserName());
        holder.userEmail.setText(user.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, EditCustomerDialogActivity.class);
                intent.putExtra("user_name", user.getUserName());
                intent.putExtra("email", user.getEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}