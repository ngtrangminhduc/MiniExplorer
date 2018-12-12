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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExplorerScreen extends AppCompatActivity {

    //Firebase Initialization
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    FirebaseDatabase mFirebaseDatabase;
    private static final String TAG = "ExplorerScreen";

    private ImageView arrow_up,arrow_down,arrow_left,arrow_right ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_screen);

        arrow_up = (ImageView) findViewById (R.id.arrow_up);
        arrow_left = (ImageView) findViewById (R.id.arrow_left);
        arrow_down = (ImageView) findViewById (R.id.arrow_down);
        arrow_right = (ImageView) findViewById (R.id.arrow_right);

        //Firebase stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

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

        //Writing directions to database
        arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                myRef.child(userID).child("Directions").setValue("Up");
            }
        });
        arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                myRef.child(userID).child("Directions").setValue("Down");
            }
        });
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                myRef.child(userID).child("Directions").setValue("Left");
            }
        });
        arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                myRef.child(userID).child("Directions").setValue("Right");
            }
        });
    }
    public void gotoMainMenu (View view)
    {
        Intent intent = new Intent(ExplorerScreen.this, MainMenu.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
