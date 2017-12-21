package com.example.presentasi.smsverification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerificationActivity extends AppCompatActivity {
    EditText editTextSecretCode;
    TextView textViewInfo;
    String info = "An sms has been sent to %s. Please enter the secret code below.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        editTextSecretCode = (EditText) findViewById(R.id.editTextSecretCode);

        //getting the data from intent
        if(getIntent() != null){
            String phoneNumber = getIntent().getStringExtra("phone_number");
            textViewInfo.setText(String.format(info,phoneNumber));
        }
    }

    public class SmsReceiver extends BroadcastReceiver {
        private static final String TAG = "SmsReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieves a map of extended data from the intent.
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            final SmsManager sms = SmsManager.getDefault();
            final Bundle bundle = intent.getExtras();

            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                        String smsContent = "PT Hepicar Indonesia.\n" +
                                "Nomor anda baru saja login. Demi keamanan, jangan berikan kode rahasia ini kepada siapapun. Kode rahasia anda 6772";
                        String splitedSmsContent[] = smsContent.split(" ");
                        String phoeNumber = splitedSmsContent[splitedSmsContent.length-1];
                        editTextSecretCode.setText(phoeNumber);
                    }
                }
            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" +e);
            }
        }
    }
}
