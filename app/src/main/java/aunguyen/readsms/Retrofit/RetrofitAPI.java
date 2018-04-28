package aunguyen.readsms.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("api/THONG_TIN_QUAN_TRAC")
    Call<String> postSMS(
            @Body JsonObject smsObject);

}
