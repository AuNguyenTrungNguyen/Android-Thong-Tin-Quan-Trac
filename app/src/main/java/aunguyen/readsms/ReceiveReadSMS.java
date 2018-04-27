package aunguyen.readsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Au Nguyen on 4/8/2018.
 */

public class ReceiveReadSMS extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";

        if(intent != null){
            if (TextUtils.equals(intent.getAction(), ACTION_SMS)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[])bundle.get("pdus");

                    if(pdus != null){

                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        }

                        /*Log.i("ANTN", "Data: " + messages[0].getDisplayMessageBody());
                        Log.i("ANTN", "Phone number: " + messages[0].getOriginatingAddress());
                        Log.i("ANTN", "Time: " + String.valueOf(messages[0].getTimestampMillis()));*/

                        Intent intentBroad = new Intent(context, ServicePostSMS.class);
                        intentBroad.putExtra("Message", messages[0].getDisplayMessageBody());
                        intentBroad.putExtra("PhoneNumber", messages[0].getOriginatingAddress());
                        intentBroad.putExtra("Time", String.valueOf(messages[0].getTimestampMillis()));

                        context.startService(intentBroad);
                        Log.i("ANTN", "Send data to service success!");
                    }
                }
            }
        }
    }
}
