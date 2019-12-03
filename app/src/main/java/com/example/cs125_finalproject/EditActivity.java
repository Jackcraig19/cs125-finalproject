package com.example.cs125_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Contact Options");
        final EditActivity context = this;
        Intent intent = getIntent();

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));

                finish();
            }
        });
        fillList();
    }

    private void fillList() {
        LinearLayout parent = findViewById(R.id.contactLayout);
        parent.removeAllViews();
        ArrayList<Contact> contacts = Handler.contacts;
        for (Contact c : contacts) {
            Log.d("EditActivityDebug","New Contact");
            View testChunk = getLayoutInflater().inflate(R.layout.chunk_contact, parent, false);
            TextView nameView = testChunk.findViewById(R.id.name);
            nameView.setText(c.getName());
            TextView numberView = testChunk.findViewById(R.id.number);
            numberView.setText(String.valueOf(c.getNumber()));
            Switch toggleSwitch = testChunk.findViewById(R.id.toggle);
            toggleSwitch.setChecked(c.getState());
            parent.addView(testChunk);
        }

    }
}
