package fh.kl.wamomu.bluetooth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.bodytel.android.connection.BluetoothSensorConnectionHandler;
import com.bodytel.handler.glucotel.GlucoTelHandler;
import com.bodytel.handler.glucotel.InvalidCalibrationCodeException;
import com.bodytel.models.Event;
import com.bodytel.models.glucose.GlucoTelMeasurement;
import com.bodytel.models.glucose.GlucoseMeasurement;
import com.bodytel.sensor.ISensorHandler;
import com.bodytel.sensor.SensorHandlerCallbacks;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.CheckUserID;
import fh.kl.wamomu.database.MeasurementReady;
import fh.kl.wamomu.database.RestfulMeasure;
import fh.kl.wamomu.ui.MeasurementFragment;
import fh.kl.wamomu.ui.NavigationDrawer;
import fh.kl.wamomu.ui.UebersichtFragment;

/**
 * Created by Max on 10.03.14.
 */
public class service extends Service implements  CheckUserID, MeasurementReady{


    private BluetoothSensorConnectionHandler connectionHandler;
    public static String diawert, diadatum, diazeit, diaeinheit, diatyp;
    public static double diatemperatur;
    public static String hi, info,datum,  erhalten, wert, einheit, typ, temperatur, parseMonat,  year;
    public static Date Monatsqlt, zeit;
    Fragment changeFragment;
    public static int code = 818;
    public static int count = 0;
    private CheckUserID syncmessure, syncmeals;
    GlucoTelMeasurement gluco;

    public static String getWert() {
        return wert;
    }

    public static void setWert(String wert) {
        service.wert = wert;
    }

    public static Date getZeit() {
        return zeit;
    }

    public static void setZeit(Date zeit) {
        service.zeit = zeit;
    }

    public static Date getMonatsqlt() {
        return Monatsqlt;
    }

    public static void setMonatsqlt(Date monatsqlt) {
        Monatsqlt = monatsqlt;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("Service wurde gestartet", "LOS");




        if(connectionHandler == null) {
            SensorHandlerCallbacks callbacks = new GlucoTelSensorHandlerCallback();
            connectionHandler = new BluetoothSensorConnectionHandler(getApplicationContext());
            Set<ISensorHandler> sensorHandlerSet = new HashSet<ISensorHandler>();
            sensorHandlerSet.add(new GlucoTelHandler(callbacks));
            connectionHandler.setSensorHandlerSet(sensorHandlerSet);
            connectionHandler.start();
            Log.e("Service", "started");
        }



    }



    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("Service wurde beendet", "BOOOOP");
    }

    protected class GlucoTelSensorHandlerCallback implements SensorHandlerCallbacks {


        @Override
        public Date getCurrentDate() {
            return new Date();
        }

        @Override
        public int getGlucotelCalibrationCode() throws InvalidCalibrationCodeException {
            return code;
        }




        @Override
        public void storeEvent(final Event event) {

            if(event instanceof GlucoTelMeasurement){
                gluco = (GlucoTelMeasurement) event;
                Monatsqlt = gluco.getDate();
                wert = gluco.getGlucoseValue().getValue().toString();
                diatyp = gluco.getMeasurementType().toString();
                diadatum = gluco.getDate().toString();
                diaeinheit = gluco.getValueType().toString();
                diatemperatur = gluco.getTemperature();
                Log.e("GLUCOINSTANCE", " " + Monatsqlt+ " " + wert);
                setWert(wert);
                setMonatsqlt(Monatsqlt);
                setZeit(Monatsqlt);
                count ++;
                createNotification();
                checkUserID();
            }

        }


        public void storeEvents(List<Event> events) {
            if(events.size() > 0) {
                storeEvent(events.get(0));


            }

        }
    }



    private void createNotification(){

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Neue Messung erhalten!").setAutoCancel(true)
                        .setContentText("Wert: \t" + diawert + " " + diaeinheit + "\n"
                                + "Datum: \t" + diadatum + "\n"
                                + "Zeitpunkt: \t" + diazeit + "\n"
                                + "Art: \t" + diatyp + "\n"
                                + "Temperatur: \t" + diatemperatur);
        int NOTIFICATION_ID = 12345;



        Intent targetIntent = new Intent(this, NavigationDrawer.class)
                .putExtra(NavigationDrawer.ACTIVITY_EXTRA, MeasurementFragment.class.getName());


        int requestID = (int) System.currentTimeMillis();



        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());

    }



    @Override
    public void checkUserID(){


            RestfulMeasure.AUTO_PUSH(this);


    }

    @Override
    public void  setMeasurementReady(){


    }




}
