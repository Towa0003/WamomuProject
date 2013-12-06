package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.database;
import fh.kl.wamomu.database.databaseMeals;
import fh.kl.wamomu.database.databaseMeasurements;
import fh.kl.wamomu.meta.user;

/**
 * Created by Thundernator on 04.11.13.
 */
public class Login extends Activity {

    private Button b_login;
    private Button b_register;
    private EditText et_login;
    private EditText et_password;

    public static database db;
    public static databaseMeals dbMeals;
    public static databaseMeasurements dbMeasurements;
    public static user activeUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");

        db = new database();
        dbMeals = new databaseMeals();
        dbMeasurements = new databaseMeasurements();
        db.accessWebService();
        dbMeals.accessWebService();
        dbMeasurements.accessWebService();

        et_login = (EditText) findViewById(R.id.et_username_edit);
        et_password = (EditText) findViewById(R.id.et_password_edit);

        b_login = (Button) findViewById(R.id.bt_login);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(  "Teseghwqzhwzghhjrfdbnw    kut" +  et_login.getText().toString() + et_password.getText().toString()  );
                if (db.checkUser(et_login.getText().toString(), et_password.getText().toString())) {
                    int currentUserID = db.getUsersID();
                    dbMeals.checkMeal(currentUserID);        // alle Meals des jeweiligen Users ausgeben
                    dbMeasurements.checkMeasurment(currentUserID); // alle Measurements des jeweiligen Users ausgeben
                    activeUser = new user(et_login.getText().toString(), et_password.getText().toString(),null,null);
                    Intent i = new Intent(Login.this, NavigationDrawer.class);
                    startActivity(i);
                    Toast.makeText(Login.this, "Connected", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Login.this, "User or password wrong", Toast.LENGTH_LONG).show();
                }
//                Intent i = new Intent(Login.this, NavigationDrawer.class);
//                startActivity(i);
            }
        });
        b_register = (Button) findViewById(R.id.bt_register);
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
    }

}
