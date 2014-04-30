package fh.kl.wamomu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fh.kl.wamomu.R;
import fh.kl.wamomu.bluetooth.service;
import fh.kl.wamomu.database.CheckUserID;
import fh.kl.wamomu.database.MealReady;
import fh.kl.wamomu.database.MeasurementReady;
import fh.kl.wamomu.database.RestfulUser;

public class Login extends Activity implements CheckUserID, MeasurementReady, MealReady {
    /**
     * Layoutvariablen
     */
    private Button b_login, b_register;
    private EditText et_login, et_password;

    /**
     * Instanzvariablen für die Datenbank und den aktiven User
     */

    private static String user;
    private static String password;
    private View view;
    private static ProgressDialog dialog;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Login.password = password;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Login.user = user;
    }

    private boolean isMealReady = false, isMeasurementsReady = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");
        dialog = new ProgressDialog(Login.this);

        final Intent inte = new Intent(Login.this, service.class );
        startService(inte);

        //EditText und Buttons werden initialisiert
        et_login = (EditText) findViewById(R.id.et_username_edit);
        et_password = (EditText) findViewById(R.id.et_password_edit);
        b_login = (Button) findViewById(R.id.bt_login);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUserID();

                setUser(et_login.getText().toString());
                setPassword(et_password.getText().toString());

                dialog.setTitle("Bitte warten!");
                dialog.setMessage("Daten werden heruntergeladen");
                dialog.show();


            }
        });



        //Register Button wird initialisiert, mit der Funktion zum Wechseln der Activity
        b_register = (Button) findViewById(R.id.bt_register);
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
    }



    @Override
    public void onBackPressed(){

    }

    @Override
    public void checkUserID(){

        RestfulUser.GET_USER(this);


    }



    @Override
    public void setMeasurementReady(){
        isMeasurementsReady = true;
        startIntent();
    }

    @Override
    public void setMealReady(){
        isMealReady = true;
        startIntent();
    }



    private void startIntent(){

                if(isMeasurementsReady && isMealReady) {
                    Intent i = new Intent(Login.this, NavigationDrawer.class);
                    startActivity(i);
                    Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();

                }

    }

    public static void dismiss(){
        dialog.dismiss();
        Login log = new Login();
        Toast.makeText(log, "user not found", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }
}
