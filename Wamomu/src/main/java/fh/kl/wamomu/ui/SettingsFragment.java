package fh.kl.wamomu.ui;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import fh.kl.wamomu.R;
import fh.kl.wamomu.bluetooth.service;
import fh.kl.wamomu.dialogs.dialog_information;
import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsFragment extends Fragment {

    private TextView pairview, infoview, kalview, codeview, code, colorhigh, colorlow;
    private EditText codepin, highvalue, lowvalue;
    private Button searchButton, codeButton;
    private static final int REQUEST_ENABLE_BT = 1;
    private int parseInt = 0;
    BluetoothAdapter btAdapter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings,
                container, false);
        getActivity().setTitle("Einstellungen");



        pairview = (TextView) view.findViewById(R.id.tv_paired_devices);
        infoview = (TextView) view.findViewById(R.id.tv_info);
        kalview = (TextView) view.findViewById(R.id.tv_addGeraet);
        codeview = (TextView) view.findViewById(R.id.tv_kalibrierungscode);
        code = (TextView) view.findViewById(R.id.tv_code);
        colorhigh = (TextView) view.findViewById(R.id.tv_color_high);
        colorhigh.setBackgroundColor(NavigationDrawer.highcolor);
        colorlow = (TextView) view.findViewById(R.id.tv_color_low);
        colorlow.setBackgroundColor(NavigationDrawer.lowColor);




        code.setText(Integer.toString(service.code));

        lowvalue = (EditText) view.findViewById(R.id.et_value_low);


        highvalue = (EditText) view.findViewById(R.id.et_value_high);

        lowvalue.setText(Integer.toString(NavigationDrawer.lowvalue) + " mg/dl");

        lowvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog3 = new Dialog(getActivity());

                dialog3.setContentView(R.layout.dialog_change_value);
                dialog3.setTitle("Neue Untergrenze eingeben:");
                dialog3.setCancelable(true);



                codepin = (EditText) dialog3.findViewById(R.id.et_valueset);

                codeButton = (Button) dialog3.findViewById(R.id.bt_value);
                codeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(codepin.getText().toString().equals("") ) {
                            Toast.makeText(getActivity(),"Es wurde kein neuer Wert eingegeben", Toast.LENGTH_SHORT).show();

                            dialog3.dismiss();
                        }else{
                            parseInt = Integer.parseInt(codepin.getText().toString());
                        }


                        if(parseInt != 0 && Integer.parseInt(codepin.getText().toString()) >= NavigationDrawer.highvalue){
                            dialog3.dismiss();
                            Toast.makeText(getActivity(),"Der neue Wert ist höher als die Obergrenze", Toast.LENGTH_SHORT).show();
                        }else if(parseInt != 0){
                        lowvalue.setText(codepin.getText().toString() + " mg/dl");
                        NavigationDrawer.lowvalue =Integer.parseInt(codepin.getText().toString());
                        dialog3.dismiss();
                        }
                        parseInt = 0;
                    }

                });

                dialog3.show();
            }
        });

        highvalue.setText(Integer.toString(NavigationDrawer.highvalue) + " mg/dl");

        highvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog3 = new Dialog(getActivity());

                dialog3.setContentView(R.layout.dialog_change_value);
                dialog3.setTitle("Neue Obermodelgrenze eingeben:");
                dialog3.setCancelable(true);



                codepin = (EditText) dialog3.findViewById(R.id.et_valueset);

                codeButton = (Button) dialog3.findViewById(R.id.bt_value);
                codeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(codepin.getText().toString().equals("") ) {
                            Toast.makeText(getActivity(),"Es wurde kein neuer Wert eingegeben", Toast.LENGTH_SHORT).show();

                            dialog3.dismiss();
                        }else{
                            parseInt = Integer.parseInt(codepin.getText().toString());
                        }


                        if(parseInt != 0 && parseInt <= NavigationDrawer.lowvalue){
                            Toast.makeText(getActivity(),"Neuer Wert ist niedriger als Untergrenze", Toast.LENGTH_SHORT).show();
                            dialog3.dismiss();

                        }else if(parseInt != 0) {
                            highvalue.setText(codepin.getText().toString() + " mg/dl");
                            NavigationDrawer.highvalue = Integer.parseInt(codepin.getText().toString());
                            dialog3.dismiss();
                        }
                        parseInt = 0;
                    }
                });

                dialog3.show();
            }
        });

        colorhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), NavigationDrawer.initialColorHigh, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        colorhigh.setBackgroundColor(color);
                        NavigationDrawer.highcolor = color;
                        NavigationDrawer.initialColorHigh = color;
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });

                dialog.show();
            }
        });

        colorlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), NavigationDrawer.initialColorLow, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        colorlow.setBackgroundColor(color);
                        NavigationDrawer.lowColor = color;
                        NavigationDrawer.initialColorLow = color;
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });

                dialog.show();

            }
        });

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog3 = new Dialog(getActivity());

                dialog3.setContentView(R.layout.dialog_change_code);
                dialog3.setTitle("Kalibrierungscode ändern");
                dialog3.setCancelable(true);



                codepin = (EditText) dialog3.findViewById(R.id.et_codeset);

                codeButton = (Button) dialog3.findViewById(R.id.bt_code);
                codeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        code.setText("Code: " +codepin.getText().toString());
                        service.code =Integer.parseInt(codepin.getText().toString());
                        dialog3.dismiss();
                    }
                });

                dialog3.show();
            }
        });

        codeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog3 = new Dialog(getActivity());

                dialog3.setContentView(R.layout.dialog_change_code);
                dialog3.setTitle("Kalibrierungscode ändern");
                dialog3.setCancelable(true);



                codepin = (EditText) dialog3.findViewById(R.id.et_codeset);

                codeButton = (Button) dialog3.findViewById(R.id.bt_code);
                codeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       code.setText("Code: " +codepin.getText().toString());
                        service.code =Integer.parseInt(codepin.getText().toString());
                        dialog3.dismiss();
                    }
                });

                dialog3.show();
            }
        });


        kalview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog2 = new Dialog(getActivity());

                dialog2.setContentView(R.layout.dialog_add_geraet);
                dialog2.setTitle("Neues Messgerät hinzufügen");
                dialog2.setCancelable(true);




                searchButton = (Button) dialog2.findViewById(R.id.bt_Search);
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       dialog2.dismiss();
                        Intent discoverableIntent = new
                                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(discoverableIntent);
                   }
                });

                dialog2.show();
            }
        });

        pairview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAdapter = BluetoothAdapter.getDefaultAdapter();
                Log.d("\nAdapter: ", "" + btAdapter);

                CheckBluetoothState();
            }
        });

        infoview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final dialog_information dialog1 = new dialog_information(getActivity());

                dialog1.show();

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
            pairview.append("\nBluetooth wird nicht unterstützt.");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d("Bluetooth ist eingeschaltet...", "" );
                pairview.setText("");

                // Listing paired devices
                pairview.append("Gekoppelte Geräte sind:");
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices) {

                    pairview.append("\n" + device.getName());
                }
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

    }



}


