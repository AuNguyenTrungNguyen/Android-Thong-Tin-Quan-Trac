package aunguyen.readsms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
        Log.i("ANTN", "Service onCreate!");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            Log.i("ANTN", "Message in service: " + intent.getStringExtra("Message"));
            Log.i("ANTN", "PhoneNumber in service: " + intent.getStringExtra("PhoneNumber"));

        }else{
            Log.i("ANTN", "Intent in service is null!");

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("ANTN", "Service onDestroy!");
    }
}
