package com.example.herry_wang.week3_contacts_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by Herry_Wang on 2015/1/26.
 */
public class EditContact extends Activity{

    public static long id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        setTitle("Edit Contact");
        Intent i = this.getIntent();

        id = i.getLongExtra("id",(long)0);
        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        final EditText editName = (EditText) findViewById(R.id.name);
        final EditText editPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        final EditText editEmail = (EditText) findViewById(R.id.email);


        editName.setText(MainActivity.people[(int)id].contactNAME);
        editPhoneNumber.setText(MainActivity.people[(int)id].contactPHONENUMBER);
        editEmail.setText(MainActivity.people[(int)id].contactEMAIL);

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();
                // content://com.android.contacts/raw_contacts/2
                long contactid = Long.parseLong(MainActivity.people[(int)id].contactID);

                // 編輯姓名
                uri = Uri.parse("content://com.android.contacts/data");

                values.put("data2", editName.getText().toString());
                resolver.update(uri, values,"(raw_contact_id="+contactid+") and (mimetype_id"+"="+"8)",null);

                // 編輯電話
                values.clear();

                values.put("data1", editPhoneNumber.getText().toString());
                resolver.update(uri, values, "(raw_contact_id="+contactid+") and (mimetype_id"+"="+"6)", null);

                // 編輯Email
                values.clear();

                values.put("data1", editEmail.getText().toString());
                resolver.update(uri, values, "(raw_contact_id="+contactid+") and (mimetype_id"+"="+"2)", null);

                MainActivity.people[(int)id].contactNAME = editName.getText().toString();
                MainActivity.people[(int)id].contactPHONENUMBER = editPhoneNumber.getText().toString();
                MainActivity.people[(int)id].contactEMAIL = editEmail.getText().toString();

                HashMap<String,String> item = new HashMap<String,String>();

                item.put("contactNameList",MainActivity.people[(int)id].contactNAME);
                item.put("contactPhoneList",MainActivity.people[(int)id].contactPHONENUMBER);

                MainActivity.myListData.set((int)id,item);

                setResult(RESULT_OK);
                finish();
            }

        });
    }

}
