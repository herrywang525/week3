package com.example.herry_wang.week3_contacts_provider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Herry_Wang on 2015/1/23.
 */
public class ContactDetail extends Activity{

    private static final int INSERT_CONTACT = Menu.FIRST;
    private static final int EDIT_CONTACT = Menu.FIRST + 1;
    private static final int DELETE_CONTACT = Menu.FIRST + 2;
    public static long id;
    private String contactId;
    private TextView nameText,phoneNumberText,emailText;
    //private MainActivity aa = new MainActivity();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetail);
        setTitle("Contact Detail");
        Intent i = this.getIntent();
        id = i.getLongExtra("id",(long)0);
        //temp setting
         nameText = (TextView)findViewById(R.id.name);
        phoneNumberText = (TextView)findViewById(R.id.phoneNumber);
        emailText = (TextView)findViewById(R.id.email);

        populate();
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
        while(temp-->=0){
            ContactCursor.moveToNext();
        }
        Log.d("TAG",String.valueOf(id));
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


        nameText.setText(disPlayName);
        phoneNumberText.setText(phoneNumber);
        emailText.setText(email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, EDIT_CONTACT, 0, R.string.editContact);
        menu.add(0, DELETE_CONTACT, 0, R.string.deleteContact);

        return true;
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case EDIT_CONTACT:
                Intent i = new Intent();
                i.setClass(this,EditContact.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                i.putExtras(bundle);
                startActivity(i);
                setResult(RESULT_OK);
                finish();
                return true;

            case DELETE_CONTACT:
                getContentResolver().delete( Uri.parse("content://com.android.contacts/data"),"raw_contact_id="+contactId,null);
                getContentResolver().delete( Uri.parse("content://com.android.contacts/raw_contacts"),"_id="+contactId,null);
                getContentResolver().delete( Uri.parse("content://com.android.contacts/contacts"),"raw_contact_id="+contactId,null);
                MainActivity.myListData.remove((int)id);
                setResult(RESULT_OK);
                finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

}
