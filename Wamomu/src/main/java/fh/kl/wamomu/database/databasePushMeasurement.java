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
import fh.kl.wamomu.ui.MealsFragment;
import fh.kl.wamomu.ui.MeasurementFragment;

public class databasePushMeasurement extends Activity {
    private String jsonResult;
//    private String url = "http://cpriyankara.coolpage.biz/employee_details.php";

    protected int usersID;
    private String url = "http://192.168.1.5/wamomusql/addmeasurement.php";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        accessWebService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            System.out.println("injsonReadTask rly?" + params[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                HttpResponse response = httpclient.execute(httppost);
//                System.out.println("Test#############" + inputStreamToString(
//                        response.getEntity().getContent()).toString());
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                System.out.println("Test######### DATABASE PUSHMEAL: " + jsonResult);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Error: " + e.toString());
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url + "?messwert=" + MeasurementFragment.getMesswert() + "&datum=" + MeasurementFragment.getDatumPush()
                                      + "&zeit=" + MeasurementFragment.getZeit() + "&userid=" + MeasurementFragment.getUserid()});
        System.out.println("##########AccessWebService######### DATABASEPUSH MEASUREMENT ");

    }
}
