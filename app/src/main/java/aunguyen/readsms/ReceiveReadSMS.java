package aunguyen.readsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Au Nguyen on 4/8/2018.
 */

public class ReceiveReadSMS extends BroadcastReceiver{

    private static String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SMS)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                Log.i("ANTN", "Data: " + messages[0].getDisplayMessageBody());
                Log.i("ANTN", "Phone number: " + messages[0].getOriginatingAddress());

                Intent intentBroad = new Intent(context, ServicePostSMS.class);
                intentBroad.putExtra("Message", messages[0].getDisplayMessageBody());
                intentBroad.putExtra("PhoneNumber", messages[0].getOriginatingAddress());
                context.startService(intentBroad);
                Log.i("ANTN", "Send data to service!");

            }
        }
    }
}
