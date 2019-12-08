package com.example.cs125_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        final NewContactActivity context = this;

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });
        Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Handler.contacts.size() == 9) {
                    Toast.makeText(context, "Contacts Full", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    EditText nameBox = findViewById(R.id.name_input);
                    String name = nameBox.getText().toString();
                    EditText numberBox = findViewById(R.id.phoneNumber);
                    String number = numberBox.getText().toString();
                    Long.parseLong(number);
                    if (number.length() != 10) {
                        throw new Exception();
                    }
                    if (name.equals("")) {
                        name = "No Name";
                    };
                    Handler.contacts.add(new Contact(name, number));
                    nameBox.setText("");
                    numberBox.setText("");
                } catch (Exception e) {
                    Log.e("Error", "Error", e);
                }
            }
        });
    }
}
