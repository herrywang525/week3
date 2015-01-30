package com.example.herry_wang.week3_contacts_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Herry_Wang on 2015/1/26.
 */
public class EditContact extends Activity{

    public static long id;
    private EditText editName,editPhoneNumber,editEmail;
    private String contactId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        setTitle("Edit Contact");
        Intent i = this.getIntent();
        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        id = i.getLongExtra("id",(long)0);
        editName = (EditText) findViewById(R.id.name);
        editPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        editEmail = (EditText) findViewById(R.id.email);


        populate();

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if("".compareTo(editName.getText().toString().trim())!=0){
                    Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                    ContentResolver resolver = getContentResolver();
                    ContentValues values = new ContentValues();
                    long contactid = Long.parseLong(contactId);

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

                    HashMap<String,String> item = new HashMap<String,String>();

                    item.put("contactNameList", editName.getText().toString());
                    item.put("contactPhoneList",editPhoneNumber.getText().toString());
                    MainActivity.myListData.set((int)id,item);

                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Toast.makeText(EditContact.this, "請填入姓名", Toast.LENGTH_LONG).show();

                }
            }

        });


    }
    public void populate(){

        Cursor ContactCursor =  getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.NAME_RAW_CONTACT_ID
                },
                null,
                null,
                "_id");

        int temp=(int)id;
        ContactCursor.moveToFirst();
        while(temp-->0){
            ContactCursor.moveToNext();
        }

        int idColumn = ContactCursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID);
        contactId = ContactCursor.getString(idColumn);
        Log.d("TAG",contactId);
        int displayNameColumn = ContactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String disPlayName = ContactCursor.getString(displayNameColumn);

        Cursor phonesCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID + " = " + contactId, null, null);
        phonesCursor.moveToFirst();
        String phoneNumber = phonesCursor.getString(phonesCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));

        Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Email.NAME_RAW_CONTACT_ID+"= " + contactId, null, null);
        emailCursor.moveToFirst();
        String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));


        editName.setText(disPlayName);
        editPhoneNumber.setText(phoneNumber);
        editEmail.setText(email);
    }

}
