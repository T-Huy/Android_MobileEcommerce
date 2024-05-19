package vn.iotstar.AppMobileEcommerce.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.api.ProductAPI;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;
import vn.iotstar.AppMobileEcommerce.retrofit.RetrofitClient;
import vn.iotstar.AppMobileEcommerce.service.RealPathUtil;

public class AddProductDialogActivity extends AppCompatActivity {

    private Button btnSelectImage, btnAddProduct, btnCancel;
    private ImageView imgView;
    private EditText edtProductName;
    private EditText edtDescri;
    private EditText edtBranId;
    private Uri imgUrl;
    private Uri mUri;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 9;
    private static final int PICK_IMAGE_REQUEST = 10;
    private ProductAPI productAPI;
    static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestReadExternalStoragePermission();

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_product);

        productAPI = RetrofitClient.getRetrofit().create(ProductAPI.class);

        imgView = findViewById(R.id.selected_image);

        btnSelectImage = findViewById(R.id.btnSelect_);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        edtProductName = findViewById(R.id.fullname);
        edtDescri = findViewById(R.id.edtDescri);
        edtBranId = findViewById(R.id.edtBrandId);

        btnCancel = findViewById(R.id.btnCancel_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddProduct = findViewById(R.id.btnSave_);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("idBrand", 0);
        edtBranId.setText(String.valueOf(id));
        edtBranId.setEnabled(false);
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
    private void addProduct() {
        if (edtProductName.getText().toString().trim().isEmpty()) {
            Toast.makeText(AddProductDialogActivity.this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (imgUrl == null) {
//            Toast.makeText(AddProductDialogActivity.this, "Please select product image", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (edtBranId == null) {
            Toast.makeText(AddProductDialogActivity.this, "Please enter brand of product", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody productName = RequestBody.create(MediaType.parse("multipart/form-data"), edtProductName.getText().toString().trim());
        //Thêm mô tả
        RequestBody productDescri = RequestBody.create(MediaType.parse("multipart/form-data"), edtDescri.getText().toString().trim());
        //Thêm brand
        RequestBody brandId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id));

        File file = new File(RealPathUtil.getRealPath(this, imgUrl));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), requestFile);

        Call<ResponseObject> call = productAPI.insert(productName, productDescri, brandId ,image);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()) {
                    ResponseObject result = response.body();
                    if (result != null && result.getStatus().equals("Success")) {
                        Toast.makeText(AddProductDialogActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddProductDialogActivity.this, "Failed to add product: " + result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddProductDialogActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(AddProductDialogActivity.this, AdminPanelBrandActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(AddProductDialogActivity.this, "Failed to call API"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddProductDialogActivity.this, AdminPanelBrandActivity.class));
                finish();
            }
        });
    }
    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            // Quyền đã được cấp, thực hiện các hành động tương ứng
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền được cấp, thực hiện các hành động tương ứng
                } else {
                    // Quyền không được cấp, thông báo cho người dùng biết
                    Toast.makeText(this, "Read external storage permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
