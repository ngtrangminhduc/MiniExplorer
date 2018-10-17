package com.example.ducnguyen.miniexplorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
    public void gotoLoginScreen (View view)
    {
        Intent intent = new Intent(MainMenu.this, LoginActivity.class);
        startActivity(intent);
    }
    public void gotoReadingScreen (View view)
    {
        Intent intent = new Intent(MainMenu.this, ReadingScreen.class);
        startActivity(intent);
    }
    public void gotoExplorerScreen (View view)
    {
        Intent intent = new Intent(MainMenu.this, ExplorerScreen.class);
        startActivity(intent);
    }
    public void gotoSettingScreen (View view)
    {
        Intent intent = new Intent(MainMenu.this, SettingScreen.class);
        startActivity(intent);
    }
    public void gotoAboutUsScreen (View view)
    {
        Intent intent = new Intent(MainMenu.this, AboutUsScreen.class);
        startActivity(intent);
    }
}
