package vn.iotstar.AppMobileEcommerce.api;

import java.util.List;

import vn.iotstar.AppMobileEcommerce.model.BrandsModel;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerAPI {
    String prefixURL = "api/customer";

    @GET(prefixURL + "/{name}")
    Call<ResponseObject> getCustomerInfor(@Path("name") String name);

    @GET(prefixURL + "/image/{id}")
    Call<ResponseBody> getImage(@Path("id") String name);

    @POST(prefixURL)
    Call<ResponseObject> updateCustomer(@Body CustomerModel customerDTO);

    @Multipart
    @POST(prefixURL + "/change-avatar")
    Call<ResponseObject> updateAvatar(@Part("name") RequestBody username, @Part MultipartBody.Part avatar);

    @GET("api/user/change-password")
    Call<UserModel> updatePassword(@Query("username") String username, @Query("oldPassword") String oldPassword,
                                   @Query("newPassword") String newPassword);

    @GET("api/user/getUserName")
    Call<ResponseDTO> getUserName(@Query("email") String email);

    @GET("api/customer/customers")
    Call<List<CustomerModel>> getCustomer();



}
