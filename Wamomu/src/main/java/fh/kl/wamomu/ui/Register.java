package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import fh.kl.wamomu.R;
import fh.kl.wamomu.database.CheckUserID;
import fh.kl.wamomu.database.RestfulUser;

public class Register extends Activity implements CheckUserID {

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


    public CheckUserID a;
    private View view;
    private boolean isNoUser = false;
    static Context contextobj;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        contextobj = getApplication();


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
                //db.accessWebService(Register.this, strUsername, strpasswordRepeat);

                //Es wird überprüft, ob ein user schon vorhanden ist
                checkUserID();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //db.accessWebService(Register.this, strUsername, strpasswordRepeat);
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

    @Override
    public void  checkUserID(){



        if(strPassword.equals(strpasswordRepeat)) {
            RestfulUser.CREATE_USER(view);
        }
        else {
            Toast.makeText(Register.this, "Passwort nicht gleich", Toast.LENGTH_SHORT).show();
        }

    }

    public static void startactivity(){
        Toast.makeText(
                contextobj,
                "User erfolgreich registriert" + "\n"
                        + "Bitte einloggen",
                Toast.LENGTH_LONG).show();
        Intent i = new Intent(contextobj, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contextobj.startActivity(i);

    }





}
