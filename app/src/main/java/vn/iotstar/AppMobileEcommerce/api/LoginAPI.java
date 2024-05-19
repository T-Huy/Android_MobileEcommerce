package vn.iotstar.AppMobileEcommerce.api;

import java.util.List;

import retrofit2.http.GET;
import vn.iotstar.AppMobileEcommerce.model.CustomerModel;
import vn.iotstar.AppMobileEcommerce.model.UserModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {

    @POST("api/user/login")
    Call<ResponseDTO> Login(@Body UserModel userModel);

    @GET("api/user/users")
    Call<List<UserModel>> getUser();
}
