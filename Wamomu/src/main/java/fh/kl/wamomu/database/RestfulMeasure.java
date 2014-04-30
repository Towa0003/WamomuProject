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

import fh.kl.wamomu.bluetooth.service;
import fh.kl.wamomu.meta.measurement;
import fh.kl.wamomu.model.MeasurementMdl;
import fh.kl.wamomu.model.MeasurementsMdl;
import fh.kl.wamomu.ui.Login;
import fh.kl.wamomu.ui.MeasurementFragment;
import fh.kl.wamomu.ui.UebersichtFragment;

/**
 * Created by Max on 11.04.2014.
 */
public class RestfulMeasure {


    public static MeasurementsMdl measures;
    public static MeasurementMdl meas;
    public static List<measurement> measurements = new ArrayList<measurement>();
    public static CheckUserID b;

    public RestfulMeasure(CheckUserID c) {
        b = c;
    }

    public static void GET_MEASURE(CheckUserID c) {
        b = c;
        measurements.clear();
        Log.d("RESTFULMEASURE", "getting by measure");

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("measurement/{measureid}");

        String url = baseUrl.toString();
        Log.d("MEASURE URL", url);

        new AsyncTask<String, Void, MeasurementMdl>() {

            @Override
            protected MeasurementMdl doInBackground(String... params) {

                HttpAuthentication authHeader = new HttpBasicAuthentication("remote", "remote");
                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> media = new ArrayList<MediaType>();
                media.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(media);
                requestHeaders.setAuthorization(authHeader);

                String measureid = String.valueOf(RestfulUser.activeUser.getId());
                Log.d("MEASUREID", "user name: " + measureid);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(
                        requestHeaders);

                String url = params[0];

                MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(jacksonConverter);

                try {

                    ResponseEntity<MeasurementsMdl> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, MeasurementsMdl.class, measureid);

                    measures = response.getBody();

                    meas = measures.getMeasurement().get(0);
                    if (null == meas) Log.d("REST_ID_ERROR", "no measurements found");
                    else Log.d("REST_SUCCESS", "" + response);
                    getMeasurements();

                    return meas;

                } catch (RestClientException rce) {
                    Log.d("REST_ERROR", " " + rce.getMostSpecificCause());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(MeasurementMdl mdl) {
            Log.e("ONPOSTEXECURE", "GETMEASURE");
                if (null == mdl) return;

                Comparator<Object> comparator = new Comparator<Object>() {
                    @Override
                    public int compare(Object lhs, Object rhs) {
                        if (lhs instanceof measurement && rhs instanceof measurement) {
                            int result = ((measurement) lhs).getDate().compareTo(((measurement) rhs).getDate());
                            if (result != 0) {
                                return result;
                            } else {
                                return ((measurement) lhs).getTime().compareTo(((measurement) rhs).getTime());
                            }
                        }
                        return -1;
                    }
                };
                Collections.sort(measurements, comparator);
                Collections.reverse(measurements);

                MeasurementFragment.swtch = 1;
                UebersichtFragment.swtch = 1;
                ((MeasurementReady)b).setMeasurementReady();
            }

        }.execute(url);

    }

    public static void getMeasurements(){

        for(int i = 0; i< measures.getMeasurement().size(); i++){
            meas = measures.getMeasurement().get(i);
            Log.d("MEASSSSURE", "" + meas.toString());
            long measurementID = meas.getMeasurementid();
            String strMvalue = meas.getMvalue();
            Double mvalue = Double.parseDouble(strMvalue);
            Date datestr = meas.getDate();
            Date timestr = meas.getTime();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //Date date = sdf.parse(datestr);

                sdf = new SimpleDateFormat("HH:mm:ss");
                //Date time = sdf.parse(timestr);

                Log.d("dbmeasure", "MeasurementID: " + measurementID
                        + "Measurement: " + mvalue
                        + " Datum: " + datestr
                        + " Zeit: " + timestr
                        + " UsersID: " + RestfulUser.activeUser.getId());

                measurements.add(new measurement(measurementID, mvalue, datestr, timestr));
                Log.d("MEASUREMENT", "ADDED");
            } catch (Exception pe) {
                Log.e("ParseException", "" + pe.toString());
            }

        }


    }


    public static void PUSH_MEASURE(final CheckUserID c) {

        Log.d("MEASURE", "sending measure");
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("measurement/");

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

                MeasurementMdl measure = new MeasurementMdl();
                measure.setMvalue(MeasurementFragment.getMesswert());
                measure.setDate(MeasurementFragment.getDatumPush());
                measure.setTime(MeasurementFragment.getZeit());
                measure.setUserid(RestfulUser.activeUser.getId());



                Log.d("MEASURE", measure.toString());

                HttpEntity<MeasurementMdl> requestEntity = new HttpEntity<MeasurementMdl>(
                        measure, requestHeaders);

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
                    Log.e("MEASUREERRROR", "Fehler  vorhanden. Bitte einen neuen eingeben");
                    return;

                }

                checkUserID(c);

            }

        }.execute(url);
    }

    public static void checkUserID(CheckUserID d){
        b = d;
        Log.e("CHECKUSERID", " " + service.count);
        if(service.count == 0) {
            GET_MEASURE(b);
        }
    }


    public static void AUTO_PUSH(final CheckUserID c) {
        b = c;
        Log.d("MEASURE", "sending measure");
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("http://");
        baseUrl.append("10.0.54.27");
        baseUrl.append(":8080");
        baseUrl.append("/glucotel/restful/");
        baseUrl.append("measurement/");

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

                MeasurementMdl measure = new MeasurementMdl();
                measure.setMvalue(service.getWert());
                measure.setDate(service.getMonatsqlt());
                measure.setTime(service.getZeit());
                measure.setUserid(RestfulUser.activeUser.getId());



                Log.d("MEASURE", measure.toString());

                HttpEntity<MeasurementMdl> requestEntity = new HttpEntity<MeasurementMdl>(
                        measure, requestHeaders);

                Log.d("MEASURE", requestEntity.toString());

                String url = params[0];

                MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(jacksonConverter);

                try {

                    ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
                    Log.d("RESPONSE", " " + response);
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
                    Log.d("SERVICE",  "Fehler  vorhanden. Bitte einen neuen eingeben");
                    return;

                }
                Log.e("ONPOSTEXECUTE", "WUT WUT");
                service.count--;
                checkUserID(b);
            }

        }.execute(url);
    }



}
