package com.example.herry_wang.week3_contacts_provider;

import android.app.Activity;
import android.content.Intent;
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
    @Override
    public void onResume() {
        super.onResume();
        populate();
    }
    public void populate(){

        Log.d("TAg",Integer.toString((int)id));
        Log.d("TAG",MainActivity.people[(int)id].contactNAME);
        Log.d("TAG",MainActivity.people[(int)id].contactPHONENUMBER);
        Log.d("TAG",MainActivity.people[(int)id].contactEMAIL);
        nameText.setText(MainActivity.people[(int)id].contactNAME);
        phoneNumberText.setText(MainActivity.people[(int)id].contactPHONENUMBER);
        emailText.setText(MainActivity.people[(int)id].contactEMAIL);

        HashMap<String,String> item = new HashMap<String,String>();

        item.put("contactNameList",MainActivity.people[(int)id].contactNAME);
        item.put("contactPhoneList",MainActivity.people[(int)id].contactPHONENUMBER);

        MainActivity.myListData.set((int)id,item);
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
                populate();
                setResult(RESULT_OK);
                finish();
                return true;

            case DELETE_CONTACT:
                getContentResolver().delete( Uri.parse("content://com.android.contacts/data"),"raw_contact_id="+MainActivity.people[(int)id].contactID,null);
                getContentResolver().delete( Uri.parse("content://com.android.contacts/raw_contacts"),"_id="+MainActivity.people[(int)id].contactID,null);
                getContentResolver().delete( Uri.parse("content://com.android.contacts/contacts"),"raw_contact_id="+MainActivity.people[(int)id].contactID,null);
                MainActivity.myListData.remove((int)id);
                setResult(RESULT_OK);
                finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

}
