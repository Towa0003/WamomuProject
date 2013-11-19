package fh.kl.wamomu.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fh.kl.wamomu.R;

public class dialog_information extends Dialog {

    TextView t;
    ImageView image;
    Button dialogButton;

    public dialog_information(Context context) {
        super(context);
        setContentView(R.layout.dialog_informationen_settings);

        setTitle("Informationen");
         t = (TextView) findViewById(R.id.dia_text);
        t.setText("Diese App wurde von der Superhyperultracoolen Truppe Wamomu für das obergeilste Megafach Ever genannt Studienprojekt gemacht!!!! 65 Wat ");

       image = (ImageView) findViewById(R.id.iv_infoimage);
                image.setImageResource(R.drawable.ic_launcher);

        dialogButton = (Button) findViewById(R.id.bt_ok);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
        setCancelable(true);

    }


}
