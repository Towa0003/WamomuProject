package fh.kl.wamomu.database;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fh.kl.wamomu.R;
import fh.kl.wamomu.ui.MeasurementFragment;

public class databasePushMeasurement {
    private String jsonResult;

    protected int usersID;
    //    private String url = "http://192.168.1.5/wamomusql/addmeasurement.php";
    private String url = "http://" + database.ip + "/wamomusql/addmeasurement.php";


    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                HttpResponse response = httpclient.execute(httppost);
//                System.out.println("Test#############" + inputStreamToString(
//                        response.getEntity().getContent()).toString());
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine;
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                 e.printStackTrace();
            }
            return answer;
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url + "?messwert=" + MeasurementFragment.getMesswert() + "&datum=" + MeasurementFragment.getDatumPush()
                + "&zeit=" + MeasurementFragment.getZeit() + "&userid=" + MeasurementFragment.getUserid()});

    }
}
