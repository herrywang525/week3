package com.example.herry_wang.week3_contacts_provider;


import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MainActivity extends ListActivity {

    static ArrayList<HashMap<String,String>> myListData = new ArrayList<HashMap<String,String>>();

    private static final int INSERT_CONTACT = Menu.FIRST;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //getContentResolver().delete( Uri.parse("content://com.android.contacts/data"),null,null);
        //getContentResolver().delete( Uri.parse("content://com.android.contacts/raw_contacts"),null,null);
       // getContentResolver().delete( Uri.parse("content://com.android.contacts/contacts"),null,null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMenu();
        registerForContextMenu(getListView());
    }

     void getMenu(){
           myListData = new ArrayList<HashMap<String,String>>();
        Cursor ContactCursor =  getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.NAME_RAW_CONTACT_ID
                },
                null,
                null,
                "_id");

        // // put all data to the arrays

        while(ContactCursor.moveToNext()){
            int idColumn = ContactCursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID);
            int displayNameColumn = ContactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            String contactId = ContactCursor.getString(idColumn);
            String disPlayName = ContactCursor.getString(displayNameColumn);
            Log.d("TAG",contactId);
            // get Phone
            String phoneNumber = "null";
            String email = "null";


            Cursor phonesCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID + " = " + contactId, null,null);
            if (phonesCursor.moveToFirst()) {
                phoneNumber = phonesCursor
                        .getString(phonesCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
            }


            Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID + " = " + contactId, null, null);
            if (emailCursor.moveToFirst()) {
                email = emailCursor
                        .getString(emailCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));
            }

            Log.d("TAG","phone:"+phoneNumber);

            HashMap<String,String> item = new HashMap<String,String>();
            item.put("contactNameList",disPlayName);
            item.put("contactPhoneList",phoneNumber);
            myListData.add(item);


        }


        fillData();

    }
    @Override
    public void onResume(){
        super.onResume();
        fillData();
    }


    public void fillData(){
        SimpleAdapter userAdapter = new SimpleAdapter(this,
                myListData,
                R.layout.row,
                new String[] { "contactNameList", "contactPhoneList" },
                new int[] { R.id.TextView1, R.id.TextView2 });


        setListAdapter(userAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, INSERT_CONTACT, 0, R.string.addContact).setIcon(android.R.drawable.ic_menu_add);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_CONTACT:
                Intent i = new Intent();
                i.setClass(this,AddContact.class);
                startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent();
        i.setClass(this,ContactDetail.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        i.putExtras(bundle);

        startActivity(i);
    }



}