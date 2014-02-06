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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fh.kl.wamomu.R;
import fh.kl.wamomu.meta.measurement;

public class databaseMeasurements {
    private String jsonResult;

    //    private String url = "http://192.168.1.5/wamomusql/measurements_details.php";
    private String url = "http://" + database.ip + "/wamomusql/measurements_details.php";
    public static List<measurement> measurements = new ArrayList<measurement>();


    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                HttpResponse response = httpclient.execute(httppost);
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

        @Override
        protected void onPostExecute(String result) {
//            ListDrwaer();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url});

    }

    public boolean checkMeasurment(int currentUserID) {
        boolean datatrue = false;
        int currentID = currentUserID;

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("measurements");
            measurements.clear();

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String usersid = jsonChildNode.optString("users_id");
                // Dem jeweiligen user die entsprechenden Daten zuweisen
                if (currentID == Integer.parseInt(usersid)) {
                    int measurementID = jsonChildNode.optInt("measurement_id");
                    String strMvalue = jsonChildNode.optString("mvalue");
                    Double mvalue = Double.parseDouble(strMvalue);
                    String datestr = jsonChildNode.optString("date");
                    String timestr = jsonChildNode.optString("time");
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf.parse(datestr);

                        sdf = new SimpleDateFormat("HH:mm:ss");
                        Date time = sdf.parse(timestr);

                        System.out.println("MeasurementID: " + measurementID
                                + "Measurement: " + mvalue
                                + " Datum: " + date
                                + " Zeit: " + time
                                + " UsersID: " + usersid);

                        measurements.add(new measurement(measurementID, mvalue, date, time));
                    } catch (ParseException pe) {
                    }

                }
            }
        } catch (JSONException e) {
        }

        return datatrue;
    }
}
