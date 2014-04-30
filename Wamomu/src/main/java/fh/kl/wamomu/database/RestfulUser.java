package fh.kl.wamomu.database;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import fh.kl.wamomu.bluetooth.service;
import fh.kl.wamomu.meta.user;
import fh.kl.wamomu.model.UserMdl;
import fh.kl.wamomu.model.UsersMdl;
import fh.kl.wamomu.ui.Login;
import fh.kl.wamomu.ui.Register;

/**
 * Created by Max on 23.04.2014.
 */
public class RestfulUser {
    public static user activeUser;

    public static void CREATE_USER(View view) {


        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("user/");

        String url = baseUrl.toString();


        new AsyncTask<String, Void, ResponseEntity<Void>>() {

            @Override
            protected ResponseEntity<Void> doInBackground(String... params) {

                HttpAuthentication authHeader = new HttpBasicAuthentication(
                        "remote", "remote");
                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> media = new ArrayList<MediaType>();
                media.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(media);
                requestHeaders.setAuthorization(authHeader);

                UserMdl user = new UserMdl();
                user.setVorname(Register.getStrVname());
                user.setNachname(Register.getStrName());
                user.setUser(Register.getStrUsername());
                user.setPassword(Register.getStrPassword());

                DateTime date = new DateTime(1394578800000L);

                user.setGeburtstag(date);




                Log.d("REGISTER", user.toString());

                HttpEntity<UserMdl> requestEntity = new HttpEntity<UserMdl>(
                        user, requestHeaders);

                Log.d("REGISTER", requestEntity.toString());

                String url = params[0];

                MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(jacksonConverter);

                try {

                    ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
                    return response;
                } catch (Exception rce) {

                    if (rce instanceof RestClientException){
                        Log.d("EXCEPTION", "An error occurred while calling api/user/account API endpoint: " + rce.getMessage());
                    } else {
                        Log.d("EXCEPTION", "An other Exception appeared");
                    }
                    Log.e("REST_ERROR", rce.toString());


                }
                return null;


            }

            @Override
            protected void onPostExecute(ResponseEntity<Void> response) {

                if (null == response){
                    Log.e(" test",  "Username ist bereits vorhanden. Bitte einen neuen eingeben");
                    return;

                }



                Register.startactivity();


            }

        }.execute(url);
    }


    public static void GET_USER(final CheckUserID c) {



        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("user/user/{vn}");

        String url = baseUrl.toString();
        Log.d("URL: ", url);

        new AsyncTask<String, Void, UserMdl>() {

            @Override
            protected UserMdl doInBackground(String... params) {

                HttpAuthentication authHeader = new HttpBasicAuthentication("remote", "remote");
                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> media = new ArrayList<MediaType>();
                media.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(media);
                requestHeaders.setAuthorization(authHeader);

                String user1 = Login.getUser();
                Log.d("USER", "user name: " + user1);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(
                        requestHeaders);

                String url = params[0];

                MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(jacksonConverter);

                try {

                    ResponseEntity<UsersMdl> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UsersMdl.class, user1);

                    UsersMdl users = response.getBody();
                    UserMdl user = users.getUsers().get(0);
                    Log.d("UUUUUUUSER", "" + user.toString());
                    if (null == user) {
                        Login.dismiss();

                    }
                    else Log.e("REST_SUCCESS", "" + response);
                    return user;
                } catch (RestClientException rce) {
                    Log.e("REST_ERROR", "" +  rce.getMostSpecificCause());

                    return null;
                }
            }

            @Override
            protected void onPostExecute(UserMdl user) {

                if (null == user) return;
                Log.d("IIIIIIFFFFFFFF", "DAVOOOOOR" + user.getPassword());
                if(user.getPassword().equals(Login.getPassword())){
                    activeUser = new fh.kl.wamomu.meta.user(user.getUser(), user.getPassword(), null, null, user.getId());
                    checkUserID(c);

                }else{
                    Log.e("Login",  "USER ODER PASSWORT FALSCH");
                }
                Log.d("IIIIIIFFFFFFFF", "DANAAAAAAch");

            }

        }.execute(url);

    }

    public static void checkUserID(CheckUserID d){

        RestfulMeasure.GET_MEASURE(d);
        RestfulMeal.GET_MEAL(d);
    }


}
