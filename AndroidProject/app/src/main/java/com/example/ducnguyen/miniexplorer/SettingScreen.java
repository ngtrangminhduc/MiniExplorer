package com.example.ducnguyen.miniexplorer;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class SettingScreen extends AppCompatActivity {
    //Firebase Initialization
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    FirebaseDatabase mFirebaseDatabase;

    private Button buttonApply;
    private EditText editTextLowBattery, editTextAutoShutdown, editTextScanIntervals;
    private static final String TAG = "SettingScreen";

    private String englishLanguageCode = "en";
    private Button buttonEnglish, buttonFrench;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        buttonEnglish = (Button) findViewById (R.id.buttonEnglish);
        buttonFrench = (Button) findViewById(R.id.buttonFrench);
        buttonApply = (Button) findViewById(R.id.buttonApply);
        editTextLowBattery = (EditText) findViewById(R.id.editTextLowBattery);
        editTextAutoShutdown = (EditText) findViewById(R.id.editTextAutoShutdown);
        editTextScanIntervals = (EditText) findViewById(R.id.editTextScanIntervals);
        //Firebase stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // Not actually required, need to test
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        // Read from the database, need to test if needed
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // Values adding to database

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                String newLowBattery = editTextLowBattery.getText().toString();
                String newScanIntervals = editTextScanIntervals.getText().toString();
                String newAutoShutdown = editTextAutoShutdown.getText().toString();
                if(!newLowBattery.equals("") || !newScanIntervals.equals("") || !newAutoShutdown.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    myRef.child(userID).child("Settings").child("Low Battery Alert").setValue(newLowBattery);
                    myRef.child(userID).child("Settings").child("Scan Intervals").setValue(newScanIntervals);
                    myRef.child(userID).child("Settings").child("Auto Shutdown").setValue(newAutoShutdown);
                    toastMessage("Adding new settings to database...");
                    //reset the text

                }

            }
        });

        //Change English to French when user clicked the button.
        buttonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Apply english", Toast.LENGTH_LONG).show();
                String languageToLoad  = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

            }
        });
        buttonFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Apply french", Toast.LENGTH_LONG).show();
                String languageToLoad  = "fr";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());


            }
        });


    }


    public void gotoMainMenu (View view)
    {
        Intent intent = new Intent(SettingScreen.this, MainMenu.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //add a toast to show when successfully signed in
    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

