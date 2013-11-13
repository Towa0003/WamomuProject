package fh.kl.wamomu.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.view.WindowManager;
import android.widget.TextView;

import fh.kl.wamomu.R;

public class custom_dialog extends Dialog {

    TextView t;

    public custom_dialog(Context context) {
        super(context);

        setTitle("Test");
         t = (TextView) findViewById(R.id.dia_text);
        t.setText("Diese App wurde von der Superhyperultracoolen Truppe Wamomu für das obergeilste Megafach Ever genannt Studienprojekt gemacht!!!! 65 Wat ");
        setContentView(R.layout.custom);
        setCancelable(true);

    }


}
