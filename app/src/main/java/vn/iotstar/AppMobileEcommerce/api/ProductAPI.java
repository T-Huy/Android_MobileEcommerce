package vn.iotstar.AppMobileEcommerce.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import vn.iotstar.AppMobileEcommerce.model.HomeViewModelClass;
import vn.iotstar.AppMobileEcommerce.model.ProductGridModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("api/product/lasted-product")
    Call<List<HomeViewModelClass>> LastedProduct();

    @GET("api/product/popular-product")
    Call<List<HomeViewModelClass>> PopularProduct();

    @GET("api/product/{brandId}")
    Call<List<ProductGridModel>> getProductByBrand(@Path("brandId") Integer brandId);
    @GET("api/product")
    Call<List<ProductGridModel>> getAllProduct();

    @GET("api/product/filter")
    Call<List<ProductGridModel>> filterProduct(@Query("start-price") double startPrice,
                                               @Query("end-price") double endPrice,
                                               @Query("start-battery") int startBattery,
                                               @Query("end-battery") int endBattery,
                                               @Query("start-screen") double startScreen,
                                               @Query("end-screen") double endScreen);

    @GET("api/product/search")
    Call<ResponseObject> searchProduct(@Query("keyword") String keyword);

    @GET("api/product/get")
    Call<ProductGridModel> getProduct(@Query("id") Integer id);

    @Multipart
    @POST("api/product")
    Call<ResponseObject> insert(@Part("name") RequestBody name,
                                @Part("description") RequestBody description,
                                @Part("brandId") RequestBody brandId,
                                @Part MultipartBody.Part images);
}
