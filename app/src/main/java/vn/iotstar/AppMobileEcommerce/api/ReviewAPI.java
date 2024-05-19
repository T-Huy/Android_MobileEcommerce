package vn.iotstar.AppMobileEcommerce.api;

import vn.iotstar.AppMobileEcommerce.model.ReviewModel;
import vn.iotstar.AppMobileEcommerce.model.dto.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewAPI {
    @GET("api/review/{productId}/{username}")
    Call<List<ReviewModel>> getReviewsByProductId(@Path("productId") int productId, @Path("username") String username);

    @POST("api/review")
    Call<ReviewModel> insertReview(@Body ReviewModel reviewModel);

    @DELETE("api/review/{id}")
    Call<ResponseObject> deleteReview(@Path("id") Integer reviewId);

    @PUT("api/review")
    Call<ReviewModel> updateReview(@Query("id") Integer reviewId,
                                   @Query("rate") Integer rate,
                                   @Query("content") String content);
}
