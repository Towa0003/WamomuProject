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
    //LayoutVariablen
    private Button b_login, b_register;
    private EditText et_login, et_password;

    //Instanzvariablen
    public static database db;
    public static databaseMeals dbMeals;
    public static databaseMeasurements dbMeasurements;
    public static user activeUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");

        //Verbindung zur Datenbank wird hergestellt
        db = new database();
        dbMeals = new databaseMeals();
        dbMeasurements = new databaseMeasurements();
        //EditText und Buttons werden initialisiert
        et_login = (EditText) findViewById(R.id.et_username_edit);
        et_password = (EditText) findViewById(R.id.et_password_edit);

        b_login = (Button) findViewById(R.id.bt_login);

        //OnClickListener zum herstellen der Verbindung zur Datenbank mir Überprüfung, ob der eingegebene User vorhanden ist.
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.accessWebService();
                dbMeals.accessWebService();
                dbMeasurements.accessWebService();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(db.getJsonResult() == null){
                    Toast.makeText(Login.this, "Databaseconnection is NULL. Check if MySQL Server is running, change IP!", Toast.LENGTH_LONG).show();
                }
                else if (db.checkUser(et_login.getText().toString(), et_password.getText().toString())) {
                    int currentUserID = db.getUsersID();
                    dbMeals.checkMeal(database.getUsersID());       // alle Meals des jeweiligen Users ausgeben
                    dbMeasurements.checkMeasurment(currentUserID);  // alle Measurements des jeweiligen Users ausgeben
                    activeUser = new user(et_login.getText().toString(), et_password.getText().toString(),null,null);
                    Intent i = new Intent(Login.this, NavigationDrawer.class);
                    startActivity(i);
                    Toast.makeText(Login.this, "Connected", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Login.this, "User or password wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Register Button wird initialisiert, mit der Funktion zum Wechsen der Activity
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
