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
import java.sql.SQLOutput;

import fh.kl.wamomu.R;

public class database extends Activity {
    protected static int usersID; //192.168.178.48

    public String getJsonResult() {
        return jsonResult;
    }

    //    private String url = "http://cpriyankara.coolpage.biz/employee_details.php";
    private String jsonResult;
    private String url = "http://192.168.178.36/wamomusql/users_details.php";
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

    public static int getUsersID() {
        return usersID;
    }

    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url});
        System.out.println("##########AccessWebService#########= database");
    }

    public boolean checkUser(String useruser, String userpassword) {
        boolean datatrue = false;

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            System.out.println("jsonresult= " + jsonResult);
            System.out.println("JSONObject= " + jsonResponse.toString());
            JSONArray jsonMainNode = jsonResponse.optJSONArray("users");
            System.out.println("jsonResponse.optJSONArray= " + jsonMainNode.toString());

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                System.out.println("Current User= " + jsonChildNode.toString());
                String number = jsonChildNode.optString("id");
                System.out.println("ID=  " + number);
                String user = jsonChildNode.optString("user");
                System.out.println("user: " + user);
                String password = jsonChildNode.optString("password");
                System.out.println("Password: " + password);

                if (useruser.equals(user) && userpassword.equals(password)) {
                    System.out.println("DATATRUE!!!!!!!!!!!!!!!!!!!");
                    datatrue = true;
                    setUsersID(Integer.parseInt(number));
                    System.out.println("USERID= " + getUsersID());
                    break;
                } else {
                    System.out.println("DATAFALSEE!!!!!!!!!!!!!!lllllllllllllllllllllllllllllllllllllllllllllll");
                }
            }
        } catch (JSONException e) {
            System.out.println(e.toString());
        }

        return datatrue;
    }

    public boolean checkPushUser(String useruser, String userpassword, String userPasswordRepeat) {
        boolean datatrue = false;
        System.out.println("°--------------------------------,,-------------------------------°");
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            System.out.println("jsonresult= " + jsonResult);
            System.out.println("JSONObject= " + jsonResponse.toString());
            JSONArray jsonMainNode = jsonResponse.optJSONArray("users");
            System.out.println("jsonResponse.optJSONArray= " + jsonMainNode.toString());

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                System.out.println("Current User= " + jsonChildNode.toString());

                String user = jsonChildNode.optString("user");
                System.out.println("user: " + user);
                String password = jsonChildNode.optString("password");
                System.out.println("Password: " + password);

                System.out.println("CHECK THIS OUT:" + userpassword + "" + userPasswordRepeat );
                System.out.println("PW STIMMEN NICHT ÜBEREIN " + !userpassword.equals(userPasswordRepeat));
                if (useruser.equals(user)) {
                    System.out.println("DATAFALSEE!!!!!!!!!!!!!!lllllllllllllllllllllllllllllllllllllllllllllll");
                    datatrue = false;
                    break;
                }
                else if(!userpassword.equals(userPasswordRepeat)){
                    System.out.println("DATAFALSEE!!!!!!!!!!!!!!lllllllllllllllllllllllllllllllllllllllllllllll");
                    datatrue = false;
                    break;
                }else{
                    System.out.println("DATATRUE!!!!!!!!!!!!!!22222222222222222222222222222222");
                    datatrue = true;
                }
            }
        } catch (JSONException e) {
            System.out.println(e.toString());
        }

        return datatrue;
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
                System.out.println("Test#########" + jsonResult);

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
}
