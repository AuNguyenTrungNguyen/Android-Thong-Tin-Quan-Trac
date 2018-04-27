package aunguyen.readsms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import aunguyen.readsms.Retrofit.Command;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePostSMS extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            final String data = intent.getStringExtra("Message");
            final String phoneNumber = intent.getStringExtra("PhoneNumber");
            final String time = intent.getStringExtra("Time");

            //Chuyen ve dinh dang 0
            String phone = phoneNumber;
            if(phoneNumber.substring(0,3).equals("+84")){
                phone = "0" + phoneNumber.substring(3);
            }

            //Xu ly thoi gian
            long millisecond = Long.parseLong(time);
            String finalTime = DateFormat.format("yyyy/MM/dd HH:mm:ss", new Date(millisecond)).toString();

            Command command = Command.getInstance();
            final String finalPhone = phone;

            Log.i("ANTN", "Data: " + data);
            Log.i("ANTN", "Phone number: " + finalPhone);
            Log.i("ANTN", "Time: " + finalTime);

            command.postSMS(data, finalPhone, finalTime, new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()){
                        Log.i("ANTN", "Post success!!!");
                        Toast.makeText(ServicePostSMS.this, response.body(), Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("ANTN", "Not success!");
                        try {
                            Log.i("ANTN", response.errorBody().string());
                        } catch (IOException e) {
                        }
                        Toast.makeText(ServicePostSMS.this, "Post fail!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("ANTN", "Post fail!!!");
                    Toast.makeText(ServicePostSMS.this, "Post fail!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Log.i("ANTN", "Intent in service is null!");

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
