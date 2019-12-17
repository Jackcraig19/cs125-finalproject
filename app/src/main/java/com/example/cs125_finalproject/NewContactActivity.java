package com.example.cs125_finalproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class NewContactActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private List<String> nameList = new ArrayList<>();
    private List<String> numberList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        final NewContactActivity CONTEXT = this;

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    EditText nameBox = findViewById(R.id.name_input);
                    String name = nameBox.getText().toString();
                    EditText numberBox = findViewById(R.id.phoneNumber);
                    String number = numberBox.getText().toString();
                    number = format(number);
                    if (number.length() != 10) {
                        throw new Exception();
                    }
                    if (name.equals("")) {
                        name = "No Name";
                    }
                    Contact c = new Contact(name, number);
                    if (Handler.contacts.contains(c)) {
                        Toast.makeText(CONTEXT, "Contact already exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Handler.contacts.add(c);
                    MessageBuilder.setupContacts(CONTEXT);
                    nameBox.setText("");
                    numberBox.setText("");
                } catch (Exception e) {
                    Toast.makeText(CONTEXT, "Invalid Number", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button autoFill = findViewById(R.id.autoFillButton);
        autoFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CONTEXT, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CONTEXT, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    Toast.makeText(CONTEXT, "Contacts could not be filled", Toast.LENGTH_LONG).show();
                } else {
                    getAllContacts();
                    for (int i = 0; i < numberList.size() && i < nameList.size(); i++) {
                        String name = nameList.get(i);
                        String number = numberList.get(i);
                        number = format(number);
                        //Log.d("number format", number + "\t\t\t" + num + "\t\t\t" + name);
                        if (number.length() == 10) {
                            Contact toAdd = new Contact(name, number);
                            if (!Handler.contacts.contains(toAdd)) {
                                toAdd.setState(false);
                                Handler.contacts.add(toAdd);
                            }
                        }
                    }
                    MessageBuilder.setupContacts(CONTEXT);
                    Toast.makeText(CONTEXT, "Contacts Filled", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private String format(String number) {
        String num = "";
        for (int j = 0; j < number.length(); j++) {
            try {
                String digit = number.substring(j, j + 1);
                Integer.parseInt(digit);
                num = num.concat(digit);
            } catch (Exception e) {
                //illegal character, do nothing
            }
        }
        if (num.length() == 11) {
            num = num.substring(1);
        }
        return num;
    }
    private void getAllContacts() {
        nameList = new ArrayList<>();
        numberList = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    String phoneNo = "";
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    numberList.add(phoneNo);
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }
}
