package com.example.ducnguyen.miniexplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExplorerScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_screen);
    }
    public void gotoMainMenu (View view)
    {
        Intent intent = new Intent(ExplorerScreen.this, MainMenu.class);
        startActivity(intent);
    }

}
