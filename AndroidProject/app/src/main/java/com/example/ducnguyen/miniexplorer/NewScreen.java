package com.example.ducnguyen.miniexplorer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;


public class NewScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_screen);

        Intent intent = getIntent();

        String message = intent.getStringExtra("EXTRA_MESSAGE");
        String message2 = intent.getStringExtra("EXTRA_MESSAGE_2");

        TextView textView = findViewById(R.id.textView4);
        TextView textView2 = findViewById(R.id.textView5);
        textView.setText(message);
        textView2.setText(message2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Screen");

        Button mBtn = (Button)findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View view){
                startActivity(new Intent(NewScreen.this, LoginActivity.class));
            }

        });
    }
}
