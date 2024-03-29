package com.example.cs125_finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String restriction = "";
    private final EditActivity CONTEXT = this;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditActivity CONTEXT = this;
        setTitle("Contact Options");
        ((TextView) findViewById(R.id.contactLength)).setText("You Have " + Handler.contacts.size() + "Contacts");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText search = findViewById(R.id.searchLabel);
                restriction = search.getText().toString();
                fillList();
            }
        });

        Button allToggle = findViewById(R.id.allToggle);
        allToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allOff = true;
                for (Contact con : Handler.contacts) {
                    if (con.getState()) {
                        allOff = false;
                        break;
                    }
                }
                for (Contact con : Handler.contacts) {
                    con.setState(allOff);
                }
                fillList();
            }
        });

        fillList();
    }

    private void fillList() {
        LinearLayout parent = findViewById(R.id.contactLayout);
        parent.removeAllViews();
        ArrayList<Contact> contacts = Handler.contacts;
        ((TextView) findViewById(R.id.contactLength)).setText("You Have " + contacts.size() + " Contacts");
        for (final Contact c : Handler.contacts) {
            String re = restriction.toLowerCase();
            String na = c.getName().toLowerCase();
            String no = c.getNumber().toLowerCase();
            if (na.contains(re) || no.contains(re)) {
                View testChunk = getLayoutInflater().inflate(R.layout.chunk_contact, parent, false);
                TextView nameView = testChunk.findViewById(R.id.name);
                nameView.setText(c.getName());
                TextView numberView = testChunk.findViewById(R.id.number);
                String number = c.getNumber();
                number = number.substring(0, 3) + "-" + number.substring(3, 6) + "-" + number.substring(6);
                numberView.setText(number);

                final Switch toggleSwitch = testChunk.findViewById(R.id.toggle);
                toggleSwitch.setChecked(c.getState());
                toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {
                            if (ContextCompat.checkSelfPermission(CONTEXT, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CONTEXT, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                                c.setState(false);
                                toggleSwitch.setChecked(false);
                            } else {
                                c.setState(true);
                            }
                        } else {
                            c.setState(false);
                        }
                    }
                });

                Button options = testChunk.findViewById(R.id.optionsButton);
                options.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText optionView = new EditText(CONTEXT);
                        optionView.setText(c.getName());
                        new AlertDialog.Builder(CONTEXT).setView(optionView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String setName = optionView.getText().toString();
                                if (setName.equals("")) {
                                    setName = "No Name";
                                }
                                c.setName(setName);
                                fillList();
                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Handler.contacts.remove(c);
                                fillList();
                            }
                        }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Dismisses dialog
                            }
                        }).create().show();
                    }
                });
                parent.addView(testChunk);
                Space space = new Space(this);
                space.setMinimumHeight(6);
                parent.addView(space);
            }
        }
        MessageBuilder.setupContacts(CONTEXT);
    }
}
