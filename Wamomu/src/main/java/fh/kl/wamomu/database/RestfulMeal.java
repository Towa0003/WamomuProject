package fh.kl.wamomu.database;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fh.kl.wamomu.meta.meal;
import fh.kl.wamomu.model.MealMdl;
import fh.kl.wamomu.model.MealsMdl;
import fh.kl.wamomu.ui.Login;
import fh.kl.wamomu.ui.MealsFragment;

/**
 * Created by Max on 11.04.2014.
 */
public class RestfulMeal {

    public static List<meal> mahlzeit = new ArrayList<meal>();

    public static MealsMdl meals;
    public static MealMdl ml;

    public static CheckUserID b;

    public RestfulMeal(CheckUserID c) {
        b = c;
    }

    public static void GET_MEAL(CheckUserID c) {
        b = c;
        mahlzeit.clear();
        Log.d("RESTFULMEAL", "getting by meal");

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("meals/{mealid}");

        String url = baseUrl.toString();
        Log.d("URL", url);

        new AsyncTask<String, Void, MealMdl>() {

            @Override
            protected MealMdl doInBackground(String... params) {

                HttpAuthentication authHeader = new HttpBasicAuthentication("remote", "remote");
                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> media = new ArrayList<MediaType>();
                media.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(media);
                requestHeaders.setAuthorization(authHeader);

                String mealid = String.valueOf(RestfulUser.activeUser.getId());
                Log.d("MEALID", "user name: " + mealid);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(
                        requestHeaders);

                String url = params[0];

                MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(jacksonConverter);

                try {

                    ResponseEntity<MealsMdl> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, MealsMdl.class, mealid);

                    meals = response.getBody();
                    ml = meals.getMeal().get(0);
                    Log.d("MEAAAL", "" + ml.toString());
                    if (null == ml) Log.d("REST_ID_ERROR", "user not found");
                    else Log.d("REST_SUCCESS", " " + response);
                    getMeals();
                    return ml;
                } catch (RestClientException rce) {
                    Log.d("REST_ERROR", " " + rce.getMostSpecificCause());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(MealMdl mdl) {

                if (null == mdl) return;

                Comparator<Object> comparator = new Comparator<Object>() {
                    @Override
                    public int compare(Object lhs, Object rhs) {
                        if (lhs instanceof meal && rhs instanceof meal) {
                            int result = ((meal) lhs).getDate().compareTo(((meal) rhs).getDate());
                            if (result != 0) {
                                return result;
                            } else {
                                return ((meal) lhs).getTime().compareTo(((meal) rhs).getTime());
                            }
                        }
                        return -1;
                    }
                };
                Collections.sort(mahlzeit, comparator);
                Collections.reverse(mahlzeit);


                ((MealReady)b).setMealReady();

            }

        }.execute(url);

    }


    public static void getMeals(){

        for(int k = 0; k < meals.getMeal().size(); k++) {
            ml = meals.getMeal().get(k);
            Long mealID = ml.getMealid();
            String mealkind = ml.getMealkind();
            String meal = ml.getMeal();
            String datestr = ml.getDate();
            String timestr = ml.getTime();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(datestr);

                sdf = new SimpleDateFormat("HH:mm");
                Date time = sdf.parse(timestr);

                System.out.println("MealID: " + mealID
                        + "Mealkind: " + mealkind
                        + "Meal: " + meal
                        + " Datum: " + date
                        + " Zeit: " + time
                        + " UsersID: " + RestfulUser.activeUser.getId());

                mahlzeit.add(new fh.kl.wamomu.meta.meal(mealID, mealkind, meal, date, time));
            } catch (ParseException pe) {
            }
        }
    }

    public static void PUSH_MEAL(final CheckUserID c) {

        Log.d("MEASURE", "sending measure");
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("meals/");

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

                MealMdl meal = new MealMdl();
                meal.setMeal(MealsFragment.getEssen());
                meal.setMealkind(MealsFragment.getEssenszeit());
                meal.setTime(MealsFragment.getZeit());
                meal.setDate(MealsFragment.getDatumPush());
                meal.setUserid(RestfulUser.activeUser.getId());



                Log.d("MEASURE", meal.toString());

                HttpEntity<MealMdl> requestEntity = new HttpEntity<MealMdl>(
                        meal, requestHeaders);

                Log.d("MEASURE", requestEntity.toString());

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
                   Log.e("ERROR", "Fehler vorhanden. Bitte einen neue Mahlzeit eingeben");
                    return;

                }

                checkUserID(c);
            }

        }.execute(url);
    }


    public static void checkUserID(CheckUserID d){

        GET_MEAL(d);

    }



}
