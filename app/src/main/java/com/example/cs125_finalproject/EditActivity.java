package com.example.cs125_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Contact Options");
        final EditActivity context = this;

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });
        LinearLayout parent = findViewById(R.id.contactLayout);
        View testChunk = getLayoutInflater().inflate(R.layout.chunk_contact, parent, false);
        TextView nameView = testChunk.findViewById(R.id.name);
        nameView.setText("Test Name");
        TextView numberView = testChunk.findViewById(R.id.number);
        numberView.setText("(934)-934-0189");
        parent.addView(testChunk);
    }
}
