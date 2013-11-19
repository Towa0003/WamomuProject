package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.database;

/**
 * Created by Thundernator on 04.11.13.
 */
public class Login extends Activity {

    private Button b_login;
    private Button b_register;
    private EditText et_login;
    private EditText et_password;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");

//        db = new database();
//        db.accessWebService();

        et_login = (EditText) findViewById(R.id.et_username_edit);
        et_password = (EditText) findViewById(R.id.et_password_edit);

        b_login = (Button) findViewById(R.id.bt_login);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (db.checkUser(et_login.getText().toString(), et_password.getText().toString())) {
//                    Intent i = new Intent(Login.this, NavigationDrawer.class);
//                    startActivity(i);
//                    Toast.makeText(Login.this, "Connected", Toast.LENGTH_LONG).show();
//
//                }else{
//                    Toast.makeText(Login.this, "User or password wrong", Toast.LENGTH_LONG).show();
//                }
                Intent i = new Intent(Login.this, NavigationDrawer.class);
                startActivity(i);
            }
        });
        b_register = (Button) findViewById(R.id.bt_register);
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Login.this,Register.class);
                Intent i = new Intent(Login.this, database.class);

                startActivity(i);
            }
        });
    }

}
