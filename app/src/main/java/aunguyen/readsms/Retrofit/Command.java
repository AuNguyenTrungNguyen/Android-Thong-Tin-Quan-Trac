package aunguyen.readsms.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;

public class Command {
    private Command() {

    }

    private static Command mCommand = null;

    public static Command getInstance() {
        if (mCommand == null) {
            mCommand = new Command();
        }
        return mCommand;
    }

    public void postSMS(String data, String phoneNumber, String time, Callback<String> callback) {
        RetrofitAPI retrofitAPI = RetrofitConfig.createLinkBuilder(RetrofitAPI.class);
        JsonObject smsObject = new JsonObject();
        smsObject.addProperty("ThongTin", data);
        smsObject.addProperty("SDT", phoneNumber);
        smsObject.addProperty("ThoiGian", time);
        Call<String> call = retrofitAPI.postSMS(smsObject);
        call.enqueue(callback);
    }

}
