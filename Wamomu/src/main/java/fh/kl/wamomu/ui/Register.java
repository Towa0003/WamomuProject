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
import fh.kl.wamomu.database.databasePushUser;

public class Register extends Activity {

    private Button login;

    private EditText username;
    private static String strUsername;

    private EditText name;
    private static String strName;

    private EditText vname;
    private static String strVname;

    private EditText password;
    private static String strPassword;

    private EditText passwordRepeat;
    private static String strpasswordRepeat;

    public static databasePushUser dbPushUser;
    public static database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbPushUser = new databasePushUser();
        db = new database();
        db.accessWebService();

        login = (Button) findViewById(R.id.bt_login_register);
        username = (EditText) findViewById(R.id.et_username_register_edit);
        name = (EditText) findViewById(R.id.et_name_register_edit);
        vname = (EditText) findViewById(R.id.et_vname_register_edit);
        password = (EditText) findViewById(R.id.et_password1_register_edit);
        passwordRepeat = (EditText) findViewById(R.id.et_password2_register_edit);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUsername = username.getText().toString();
                setStrUsername(strUsername);
                strName = name.getText().toString();
                setStrName(strName);
                strVname = vname.getText().toString();
                setStrVname(strVname);
                strPassword = password.getText().toString();
                setStrPassword(strPassword);
                strpasswordRepeat = passwordRepeat.getText().toString();
                setStrpasswordRepeat(strpasswordRepeat);
                //Es wird überprüft, ob ein user schon vorhanden ist
                if(db.checkPushUser(getStrUsername(), getStrPassword(), getStrpasswordRepeat()) == false){
                    Toast.makeText(
                            Register.this,
                            "User already exists or pw is not equal" + "\n"
                                    + "Please check your input data",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    dbPushUser.accessWebService();
                    Toast.makeText(
                            Register.this,
                            "User erfolgreich registriert" + "\n"
                                    + "Bitte einloggen",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Register.this,Login.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.accessWebService();
    }

    /**
     * Getter und Setter für Username, Vorname, Nachname, Passwort und wiederholtes Passwort
     */
    public static String getStrUsername() {return strUsername;}

    public void setStrUsername(String strUsername) {this.strUsername = strUsername;}

    public static String getStrName() {return strName;}

    public void setStrName(String strName) {this.strName = strName;}

    public static String getStrVname() {return strVname;}

    public void setStrVname(String strVname) {this.strVname = strVname;}

    public static String getStrPassword() {return strPassword;}

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public static String getStrpasswordRepeat() {
        return strpasswordRepeat;
    }

    public void setStrpasswordRepeat(String strpasswordRepeat) {
        this.strpasswordRepeat = strpasswordRepeat;
    }

}
