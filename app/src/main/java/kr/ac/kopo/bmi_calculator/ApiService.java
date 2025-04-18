package kr.ac.kopo.bmi_calculator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("openapi/tn_pubr_public_nutri_food_info_api")
    Call<ApiResponse> getNutritionInfo(
            @Query(value = "serviceKey", encoded = true) String serviceKey,
            @Query("type") String type,
            @Query("foodNm") String foodNm
    );
    @GET("openapi/tn_pubr_public_nutri_food_info_api")
    Call<ResponseBody> getRawNutritionInfo(
            @Query("serviceKey") String serviceKey,
            @Query("type") String type,
            @Query("foodNm") String foodNm
    );
}