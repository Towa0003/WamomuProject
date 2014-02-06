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
        t.setText("Der Kalibrierungs-Pin befindet sich auf den Teststreifen. \n " +
                "Diese App wurde als Studienprojekt von Tobias Walter, Maximilian Mock, Christian Murlowski erstellt. " );

       image = (ImageView) findViewById(R.id.iv_infoimage);
                image.setImageResource(R.drawable.einstellung);

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
