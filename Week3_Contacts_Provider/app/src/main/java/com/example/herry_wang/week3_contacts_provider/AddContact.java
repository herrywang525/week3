package com.example.herry_wang.week3_contacts_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;

/**
 * Created by Herry_Wang on 2015/1/23.
 */
public class AddContact extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button) findViewById(R.id.cancel);

        final EditText editName = (EditText) findViewById(R.id.name);
        final EditText editPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        final EditText editEmail = (EditText) findViewById(R.id.email);

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
                long contactid = ContentUris.parseId(resolver.insert(uri, values));

                // 添加姓名
                uri = Uri.parse("content://com.android.contacts/data");
                values.put("raw_contact_id", contactid);
                values.put("mimetype", "vnd.android.cursor.item/name");
                values.put("data2", editName.getText().toString());
                resolver.insert(uri, values);

                // 添加電話
                values.clear();
                values.put("raw_contact_id", contactid);
                values.put("mimetype", "vnd.android.cursor.item/phone_v2");
                values.put("data2", "2");
                values.put("data1", editPhoneNumber.getText().toString());
                resolver.insert(uri, values);

                // 添加Email
                values.clear();
                values.put("raw_contact_id", contactid);
                values.put("mimetype", "vnd.android.cursor.item/email_v2");
                values.put("data2", "2");
                values.put("data1", editEmail.getText().toString());
                resolver.insert(uri, values);
                Log.d("TAG","putDataFinish");


                Log.d("TAG","userrrr"+MainActivity.userCount);
                Log.d("TAG","userrrr"+String.valueOf(contactid));
                MainActivity.people[MainActivity.userCount] = new MainActivity.person();
                MainActivity.people[MainActivity.userCount].contactID = String.valueOf(contactid);
                MainActivity.people[MainActivity.userCount].contactNAME = editName.getText().toString();
                MainActivity.people[MainActivity.userCount].contactPHONENUMBER = editPhoneNumber.getText().toString();
                MainActivity.people[MainActivity.userCount].contactEMAIL = editEmail.getText().toString();

                Log.d("TAG",MainActivity.people[MainActivity.userCount].contactID);
                Log.d("TAG",MainActivity.people[MainActivity.userCount].contactNAME);
                Log.d("TAG",MainActivity.people[MainActivity.userCount].contactPHONENUMBER);
                Log.d("TAG",MainActivity.people[MainActivity.userCount].contactEMAIL);
                MainActivity.userCount++;
                HashMap<String,String> item = new HashMap<String,String>();
                item.put("contactNameList",editName.getText().toString());
                item.put("contactPhoneList",editPhoneNumber.getText().toString());
                MainActivity.myListData.add(item);

                setResult(RESULT_OK);
                finish();
            }

        });
    }

}
