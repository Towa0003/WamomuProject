package fh.kl.wamomu.ui;

import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;

import java.util.Set;

import fh.kl.wamomu.R;
import fh.kl.wamomu.dialogs.custom_dialog;
import fh.kl.wamomu.dialogs.custom_dialog2;

/**
 * Created by Thundernator on 04.11.13.
 */
public class SettingsFragment extends Fragment {

    private TextView textview1;
    public TextView textview2;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings,
                container, false);
        getActivity().setTitle("Einstellungen");

        textview1 = (TextView) view.findViewById(R.id.pair_textView);
        textview2 = (TextView) view.findViewById(R.id.info_textView);

        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAdapter = BluetoothAdapter.getDefaultAdapter();
                Log.d("\nAdapter: ", "" + btAdapter);
                //textview1.append( + btAdapter);
                CheckBluetoothState();
            }
        });

        textview2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final custom_dialog2 dialog1 = new custom_dialog2(getActivity());

                dialog1.show();

//                // custom dialog
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.custom);
//                dialog.setTitle("Informationen");
//
//                // set the custom dialog components - text, image and button
//                TextView text = (TextView) dialog.findViewById(R.id.dia_text);
//                text.setText("Diese App wurde von der Superhyperultracoolen Truppe Wamomu für das obergeilste Megafach Ever genannt Studienprojekt gemacht!!!! 65 Wat ");
//                ImageView image = (ImageView) dialog.findViewById(R.id.image);
//                image.setImageResource(R.drawable.ic_launcher);
//
//                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//                // if button is clicked, close the custom dialog
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
            }
        });





        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBluetoothState();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void CheckBluetoothState() {
        // Checks for the Bluetooth support and then makes sure it is turned on
        // If it isn't turned on, request to turn it on
        // List paired devices
        if(btAdapter==null) {
            textview1.append("\nBluetooth NOT supported. Aborting.");
            return;
        } else {
            if (btAdapter.isEnabled()) {
                Log.d("Bluetooth is enabled...", "" );
                textview1.setText("");

                // Listing paired devices
                textview1.append("\nPaired Devices are:");
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices) {

                    textview1.append("\n  Device: " + device.getName() + ", " + device);
                }
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

}


