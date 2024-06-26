package vn.iotstar.AppMobileEcommerce.api;

import vn.iotstar.AppMobileEcommerce.model.Status;
import vn.iotstar.AppMobileEcommerce.model.dto.RequestOrderDTO;
import vn.iotstar.AppMobileEcommerce.model.dto.OrderResponseDTO;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseOrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderAPI {
    @POST("api/order")
    Call<OrderResponseDTO> order(@Body RequestOrderDTO orderDTO);

    @GET("api/order/{username}")
    Call<List<ResponseOrderDTO>> getOrderByUsername(@Path("username") String username);

    @GET("api/order/getOrder/{status}")
    Call<List<ResponseOrderDTO>> getOrderByStatus(@Path("status") Status status);

    @GET("api/order/getOrderByID/{orderId}")
    Call<ResponseOrderDTO> getOrderByID(@Path("orderId") Integer orderId);

    @GET("api/order/update-status/{orderId}/{status}")
    Call<OrderResponseDTO> updateStatus(@Path("orderId") Integer orderId,
                                        @Path("status") Status status);
}
