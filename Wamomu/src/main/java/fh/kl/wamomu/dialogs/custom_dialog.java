package fh.kl.wamomu.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import fh.kl.wamomu.R;

public class custom_dialog extends Dialog {



    public custom_dialog(Context context) {
        super(context);

        setTitle("Test");
        setContentView(R.layout.custom);
        setCancelable(true);

    }


}
