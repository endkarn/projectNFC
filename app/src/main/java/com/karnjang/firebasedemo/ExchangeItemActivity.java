package com.karnjang.firebasedemo;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcF;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.models.Item;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

public class ExchangeItemActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");

    private TextView textNfc;

    private PendingIntent nfcPendingIntent;
    private IntentFilter[] nfcIntentFilters;
    private String[][] nfcTechLists;
    String textString = "";
    String waitingTag = "";
    Item itemEx;
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_item);

        Intent iIntent = getIntent();
        storeId = iIntent.getExtras().getString("StoreID");
        String ItemId = iIntent.getExtras().getString("ItemID");
        SharedPreferences userPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userName = userPref.getString("SH_USERNAME","");

        ImageView imageItem = findViewById(R.id.imageItem);
        textNfc = findViewById(R.id.textNfc);
        waitingTag = "KOOLPONG_FROMSTORE_"+storeId;
        Log.i("Ex Info","Wait Tag"+waitingTag);

        final TextView textItemName = findViewById(R.id.textItemName);
        final TextView textItemPrice = findViewById(R.id.textItemPrice);
        final TextView textItemStore = findViewById(R.id.textItemStore);
        final ToggleButton buttonTimeOut = findViewById(R.id.buttonTimeOut);

        dbStoreRef.child(storeId).child("ITEMS").child(ItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemEx = dataSnapshot.getValue(Item.class);

                Log.i("Ex Info", String.valueOf(dataSnapshot));

                textItemName.setText("Item : "+itemEx.getItemName());
                textItemPrice.setText("Price : "+itemEx.getItemPrice());
                textItemStore.setText("Ref Store : "+storeId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null){

        }else {

        }

        // create an intent with tage data
        nfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // create intent filter for MIME data
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntent.addDataType("*/*");
            nfcIntentFilters = new IntentFilter[] { ndefIntent };
        } catch (Exception e) {
            Log.e("TagDispatch", e.toString());
        }

        nfcTechLists = new String[][] { new String[] { NfcF.class.getName() } };




        new CountDownTimer(60000,1000) {

            public void onTick(long millisUntilFinished) {
                buttonTimeOut.setText(Long.toString(millisUntilFinished/1000));
            }

            public void onFinish() {
                buttonTimeOut.setText("Time Out!");

            }
        }.start();







    }

    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        // get the data from the tage
        Tag nfc_tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        // string that hold val of tag data
        String s = action + "\n\n" + nfc_tag.toString();

        // create the parceable object to parse through all nfc data, but only get the text
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // parse the data
        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord [] nfc_records = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < nfc_records.length; j++) {
                        if (nfc_records[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                Arrays.equals(nfc_records[j].getType(), NdefRecord.RTD_TEXT)) {
                            byte[] nfc_payload = nfc_records[j].getPayload();
                            String textEncoding = ((nfc_payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = nfc_payload[0] & 0077;
                            // concat data to end of string
                            s += ("\n\nNdefMessage[" + i + "], NdefRecord[" + j + "]:\n\"" +
                                    new String(nfc_payload, langCodeLen + 1, nfc_payload.length - langCodeLen - 1,
                                            textEncoding) + "\"");
                            textString = new String(nfc_payload, langCodeLen + 1, nfc_payload.length - langCodeLen - 1,
                                    textEncoding);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }
        }
        // set the textview to the NFC text data
//        textNfc.setText(s);
        textNfc.setText(textString);

        if(waitingTag.equals(textString)){
            final Dialog dialog = new Dialog(ExchangeItemActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_exchangeitem);
            dialog.setCancelable(true);

            Button buttonDiCom = dialog.findViewById(R.id.buttonDialog);
            buttonDiCom.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext()
                            , "Close dialog", Toast.LENGTH_SHORT);
                    dialog.cancel();
                }
            });

            TextView textDiItemName = (TextView)dialog.findViewById(R.id.textDiItemName);
            TextView textDiItemStore = (TextView)dialog.findViewById(R.id.textDiItemStore);

            textDiItemName.setText(itemEx.getItemName());
            textDiItemStore.setText(storeId);

            dialog.show();
        }else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, nfcIntentFilters, nfcTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }



}
