package fh.kl.wamomu;


import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

public class custom_dialog extends Dialog {



    public custom_dialog(Context context) {
        super(context);

        setTitle("Test");
        setContentView(R.layout.custom);
        setCancelable(true);

    }


}
