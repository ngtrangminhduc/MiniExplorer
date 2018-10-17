package com.example.ducnguyen.miniexplorer;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Main Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.user){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendCredentials (View view) {
        Intent intent = new Intent (LoginActivity.this, NewScreen.class);
        startActivity(intent);

        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText2);

        String message = editText.getText().toString();
        String message2 = editText2.getText().toString();

        intent.putExtra("EXTRA_MESSAGE",message);
        intent.putExtra("EXTRA_MESSAGE_2",message2);
        startActivity(intent);
    }

    public void gotoRegister (View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegistrationScreen.class);
        startActivity(intent);
    }
    public void gotoMainMenu (View view)
    {
        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(intent);
    }
}
