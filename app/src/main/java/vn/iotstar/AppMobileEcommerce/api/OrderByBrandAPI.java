package vn.iotstar.AppMobileEcommerce.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderByBrandAPI {
    @GET("api/admin/dashboard/get-total-price-of-brand")
    Call<HashMap<String, Float>> getTotalPriceOfBrand();
}
