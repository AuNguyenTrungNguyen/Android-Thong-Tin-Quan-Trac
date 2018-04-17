package aunguyen.readsms;

import android.Manifest;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences mPreferences;
    private int mState; // -1 default, 0 stat, 1 stop
    private static String NAME_OF_KEY = "STATE";

    private Button mBtnStartService;
    private Button mBtnStopService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();
        
        addControls();
        
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState(mState);
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    private void addControls() {
        mBtnStartService = findViewById(R.id.btn_start_service);
        mBtnStopService = findViewById(R.id.btn_stop_service);

        mPreferences = getPreferences(MODE_PRIVATE);
    }

    private void addEvents() {
        mBtnStartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);
    }

    private void checkState(){
        mState = mPreferences.getInt(NAME_OF_KEY, -1);

        if(mState == -1){
            stopService();
        }else if(mState == 0){
            startService();
            setEnableView(0);
        }else if(mState == 1){
            stopService();
            setEnableView(1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_service:
                startService();
                saveState(0);
                setEnableView(0);
                break;

            case R.id.btn_stop_service:
                stopService();
                saveState(1);
                setEnableView(1);
                break;
        }
    }

    private void startService(){
        PackageManager pm  = MainActivity.this.getPackageManager();
        ComponentName componentName = new ComponentName(MainActivity.this, ReceiveReadSMS.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Log.i("ANTN", "Receive ENABLED!");

    }

    private void stopService(){
        PackageManager pm  = MainActivity.this.getPackageManager();
        ComponentName componentName = new ComponentName(MainActivity.this, ReceiveReadSMS.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Log.i("ANTN", "Receive DISABLED!");
    }

    private void saveState(int state){
        SharedPreferences.Editor editor = mPreferences.edit();
        mState = state;
        editor.putInt(NAME_OF_KEY, state);
        editor.apply();
    }

    private void setEnableView(int state){

        if(state == 0){
            mBtnStartService.setEnabled(false);
            mBtnStopService.setEnabled(true);
        }else if (state == 1){
            mBtnStartService.setEnabled(true);
            mBtnStopService.setEnabled(false);
        }
    }
}
