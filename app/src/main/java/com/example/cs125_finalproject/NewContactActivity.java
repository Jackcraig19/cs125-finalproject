package com.example.cs125_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        final NewContactActivity context = this;
        final Intent intent = getIntent();

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
                EditText nameBox = findViewById(R.id.name_input);
                String name = nameBox.getText().toString();
                EditText numberBox = findViewById(R.id.phoneNumber);
                int number = Integer.parseInt(numberBox.getText().toString());
                Handler.contacts.add(new Contact(name, number));
                nameBox.setText("");
                numberBox.setText("");
            }
        });
    }
}
