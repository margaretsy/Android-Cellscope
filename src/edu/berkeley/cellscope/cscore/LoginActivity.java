package edu.berkeley.cellscope.cscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("hello world");
        
        // Setup ORM
        // Dao<CSPicture, String> accountDao =
        //		  DaoManager.createDao(connectionSource, CSPicture.class);
        // How do we fix this?
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public void doLogin(View v) {
    	// Dummy login
    	Intent intent = new Intent(this,MenuActivity.class);
    	startActivity(intent);
    }
}
