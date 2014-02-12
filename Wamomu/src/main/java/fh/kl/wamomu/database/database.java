package fh.kl.wamomu.database;


import android.os.AsyncTask;
import android.util.Log;


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


public class database  {
    protected static int usersID; //192.168.178.48

    static String ip = "192.168.1.5"; //Tobi
//    static String ip = "192.168.2.102"; //Max
  //  static String ip = "192.168.178.36"; // Chris

    public String getJsonResult() {
        return jsonResult;
    }

    private String jsonResult;
    private String url = "http://" + ip + "/wamomusql/users_details.php";



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
    }

    public boolean checkUser(String useruser, String userpassword) {
        boolean datatrue = false;

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("users");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String number = jsonChildNode.optString("id");
                String user = jsonChildNode.optString("user");
                String password = jsonChildNode.optString("password");

                if (useruser.equals(user) && userpassword.equals(password)) {
                    datatrue = true;
                    setUsersID(Integer.parseInt(number));
                    break;
                }
            }
        } catch (JSONException e) {
        }

        return datatrue;
    }

    public boolean checkPushUser(String useruser, String userpassword, String userPasswordRepeat) {
        boolean datatrue = false;
        Log.d("database", "User pushed");
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("users");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                String user = jsonChildNode.optString("user");
                String password = jsonChildNode.optString("password");

                if (useruser.equals(user)) {
                    datatrue = false;
                    break;
                } else if (!userpassword.equals(userPasswordRepeat)) {
                    datatrue = false;
                    break;
                } else {
                    datatrue = true;
                }
            }
        } catch (JSONException e) {
        }

        return datatrue;
    }

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
    }// end async task
}
