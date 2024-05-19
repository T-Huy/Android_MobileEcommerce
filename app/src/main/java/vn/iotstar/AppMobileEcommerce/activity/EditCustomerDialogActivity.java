package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.api.BrandAPI;
import vn.iotstar.AppMobileEcommerce.api.CustomerAPI;
import vn.iotstar.AppMobileEcommerce.model.BrandsModel;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;
import vn.iotstar.AppMobileEcommerce.service.RealPathUtil;

public class EditCustomerDialogActivity extends AppCompatActivity {
    private CustomerModel customerModel;
    private TextView title_;
    private Button btnSelectImage, btnSaveCustomer, btnCancel;
    private ImageView imgView;
    private EditText edtusername;
    private EditText edtfullname;
    private EditText edtaddress;
    private EditText edtphone;
    private Uri imgUrl;
    private Uri mUri;
    private String user_name;
    private String email;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 9;
    private static final int PICK_IMAGE_REQUEST = 10;
    private CustomerAPI customerAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_admin_update_customer);

        customerAPI = RetrofitClient.getRetrofit().create(CustomerAPI.class);

        imgView = findViewById(R.id.selected_image);

        btnSelectImage = findViewById(R.id.btnSelect_);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        title_ = findViewById(R.id.dialog_title);
        edtusername = findViewById(R.id.username);
        edtfullname = findViewById(R.id.fullname);
        edtaddress = findViewById(R.id.address);
        edtphone = findViewById(R.id.phonenumber);

        btnSaveCustomer = findViewById(R.id.btnSave_);
        btnCancel = findViewById(R.id.btnCancel_);
        title_.setText("Thay đổi thông tin");

        // Lấy thông tin của Customer cần sửa từ Intent và hiển thị lên giao diện

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        email = intent.getStringExtra("email");
        customerAPI = RetrofitClient.getRetrofit().create(CustomerAPI.class);
        customerAPI.getCustomerInfor(user_name).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()) {
                    ResponseObject responseObject = response.body();
                    if (responseObject != null && responseObject.getStatus().equals("Success")) {
                        CustomerModel customer = (CustomerModel) responseObject.getData();
                        // Gán thông tin của khách hàng vào các EditText

                        edtusername.setText(customer.getUserName());
                        edtfullname.setText(customer.getFullname());
                        edtaddress.setText(customer.getAddress());
                        edtphone.setText(customer.getPhonenumber());
                        if (customer.getAvatar() != null && !customer.getAvatar().isEmpty()) {
                            Picasso.get().load(customer.getAvatar()).into(imgView);
                        }
                    } else {
                        // Xử lý khi không thành công
                        Toast.makeText(EditCustomerDialogActivity.this, responseObject.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi không thành công
                    Toast.makeText(EditCustomerDialogActivity.this, "Không thành công: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối
                Toast.makeText(EditCustomerDialogActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xử lý update
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();
            mUri = imgUrl;
            String realPath = RealPathUtil.getRealPath(this, mUri);
            if (realPath != null) {
                Picasso.get().load(imgUrl).into(imgView);
            } else {
                Toast.makeText(this, "Cannot get real path of image", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void updateBrand() {
//        if (username.getText().toString().trim().isEmpty()) {
//            Toast.makeText(EditCustomerDialogActivity.this, "Please enter user name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (fullname.getText().toString().trim().isEmpty()) {
//            Toast.makeText(EditCustomerDialogActivity.this, "Please enter full name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (address.getText().toString().trim().isEmpty()) {
//            Toast.makeText(EditCustomerDialogActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (phone.getText().toString().trim().isEmpty()) {
//            Toast.makeText(EditCustomerDialogActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        MultipartBody.Part image = null;
//        if (imgUrl != null) {
//            File file = new File(RealPathUtil.getRealPath(this, imgUrl));
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            image = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
//            Log.e("images", image.toString());
//        }
//
//        //Call<ResponseObject> call = customerAPI.update(oldName, brandName, image);
////        call.enqueue(new Callback<ResponseObject>() {
////            @Override
////            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
////                if (response.isSuccessful()) {
////                    ResponseObject result = response.body();
////                    if (result != null && result.getStatus().equals("Success")) {
////                        Toast.makeText(EditBrandDialogActivity.this, "Brand updated successfully", Toast.LENGTH_SHORT).show();
////                    } else {
////                        Toast.makeText(EditBrandDialogActivity.this, "Failed to update brand: " + result.getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                } else {
////                    Toast.makeText(EditBrandDialogActivity.this, "Failed to update brand", Toast.LENGTH_SHORT).show();
////                }
////                startActivity(new Intent(EditBrandDialogActivity.this, AdminPanelBrandActivity.class));
////                finish();
////            }
////
////            @Override
////            public void onFailure(Call<ResponseObject> call, Throwable t) {
////                Toast.makeText(EditBrandDialogActivity.this, "Failed to call API" + t.getMessage(), Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(EditBrandDialogActivity.this, AdminPanelBrandActivity.class));
////                finish();
////            }
////        });
//    }

}
