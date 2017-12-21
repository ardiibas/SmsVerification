package com.example.presentasi.smsverification;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText editTextPhoneNumber;

    int SMS_PERMISSION_CODE = 1001;

    private BroadcastReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        //1
        requestReadAndSendSmsPermission();
    }

    public void onClickButtonLoginListener(View button){
        String phoneNumber = editTextPhoneNumber.getText().toString();
        if (phoneNumber.isEmpty()){
            Toast.makeText(this, "phone number can not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent nextPage = new Intent(this, VerificationActivity.class);
        nextPage.putExtra("phone_number",phoneNumber);
        startActivity(nextPage);
    }

    //2
    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }

    //3
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: ");
                }
            }
        }
    }
}
