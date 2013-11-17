package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.test2;

/**
 * Created by Thundernator on 04.11.13.
 */
public class Login extends Activity {

    private Button b_login;
    private Button b_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");

        b_login = (Button) findViewById(R.id.login_button);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,NavigationDrawer.class);
                startActivity(i);
            }
        });
        b_register = (Button) findViewById(R.id.register_button);
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Login.this,Register.class);
                Intent i = new Intent(Login.this,test2.class);

                startActivity(i);
            }
        });
    }

}
