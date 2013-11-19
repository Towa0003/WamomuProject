package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fh.kl.wamomu.R;

/**
 * Created by Thundernator on 04.11.13.
 */
public class Register extends Activity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        login = (Button) findViewById(R.id.bt_login_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,NavigationDrawer.class);
                startActivity(i);
            }
        });
    }
}
