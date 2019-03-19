package com.example.ducnguyen.miniexplorer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class ReadingScreen extends AppCompatActivity {

    //Firebase Initialization
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;

    private static final String TAG = "ReadingScreen";
    ERDataStructure mData;
    private TextView textViewTemperatureNumber, textViewHumidityNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_screen);
        //Find Views
        textViewHumidityNumber = (TextView) findViewById(R.id.humidity_number);
        textViewTemperatureNumber = (TextView) findViewById(R.id.temperature_number);
        // Initiate Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

         // Non-working Reference, but code works
        //myRef = mFirebaseDatabase.getReference();
        //myRef = mFirebaseDatabase.getReference().child(userID);

         //Crashing Reference
          //myRef = mFirebaseDatabase.getReference().child(userID).child("ERDataStructure");
            myRef = mFirebaseDatabase.getReference().child("Environment");
        //
        retrieveData();
    }
    public void gotoMainMenu (View view)
    {
        Intent intent = new Intent(ReadingScreen.this, MainMenu.class);
        startActivity(intent);
    }
    private void retrieveData(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ERDataStructure ds = dataSnapshot.getValue(ERDataStructure.class);
                textViewTemperatureNumber.setText("" +ds.getTemperature());
                textViewHumidityNumber.setText(""+ds.getHumidity());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ERDataStructure ds = dataSnapshot.getValue(ERDataStructure.class);
                textViewTemperatureNumber.setText("" +ds.getTemperature());
                textViewHumidityNumber.setText(""+ds.getHumidity());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ERDataStructure> arraylist = new ArrayList<ERDataStructure>();
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //String userID = user.getUid();
                    for (DataSnapshot a : dataSnapshot.getChildren()){

                        ERDataStructure dataStructure = new ERDataStructure();
                        dataStructure.setTemperature(a.getValue(ERDataStructure.class).getTemperature());
                        dataStructure.setHumidity(a.getValue(ERDataStructure.class).getHumidity());
                        arraylist.add(dataStructure);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
