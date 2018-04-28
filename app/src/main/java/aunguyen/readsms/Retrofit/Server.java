package aunguyen.readsms.Retrofit;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    private RetrofitAPI mAPI;

    public Server(Context context, String url) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mAPI = retrofit.create(RetrofitAPI.class);
        }catch (IllegalArgumentException e){
            Toast.makeText(context, "URL is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    public void postSMS(String data, String phoneNumber, String time, Callback<String> callback) {

        JsonObject smsObject = new JsonObject();
        smsObject.addProperty("ThongTin", data);
        smsObject.addProperty("SDT", phoneNumber);
        smsObject.addProperty("ThoiGian", time);
        if (mAPI != null){
            mAPI.postSMS(smsObject).enqueue(callback);
        }
    }
}
