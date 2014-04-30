package fh.kl.wamomu.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.MeasurementReady;
import fh.kl.wamomu.database.RestfulMeal;
import fh.kl.wamomu.database.RestfulMeasure;
import fh.kl.wamomu.database.RestfulUser;
import fh.kl.wamomu.meta.meal;
import fh.kl.wamomu.meta.measurement;

public class UebersichtFragment extends Fragment implements MeasurementReady {

    private ListView overview_listview;
    int size = (RestfulMeal.mahlzeit.size() + RestfulMeasure.measurements.size());

    String[] art = new String[size];
    String[] gericht =  new String[size];
    String[] datum = new String[size];
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    int h1 = 0;
    int count = 0;
    double h1a = 0;
    int h1count = 0;
    public static double h1schnitt = 0;
    public static double schnitt = 0;
    Date date = null, date2 = null;
    Long festjodadate = null, aktuelljodadate = null;
    DateTime festjodaTime, aktuelljodaTime;

    public static String userid, outmg, outproz;
    public static List<Object> combined = new ArrayList<Object>();
    public static int swtch = 0;
    ScheduledExecutorService scheduleTaskExecutor;
    Fragment changeFragment;
    FragmentTransaction ft;
    FragmentManager fm;
    Menu mMenu;





    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.overview,
                container, false);
        getActivity().setTitle("Übersicht");

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 10 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.e("RUUUUUUN", "" + swtch);
                if(swtch == 1) {
                    refresh();
                }
            }
        }, 5, 10, TimeUnit.SECONDS);

        ft = getFragmentManager().beginTransaction();
        fm = getActivity().getSupportFragmentManager();

        overview_listview = (ListView) view.findViewById(R.id.lv_meals);

        OverviewArrayAdapter adapter = new OverviewArrayAdapter(getActivity() ,art ,gericht, datum);
        overview_listview.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        combined.clear();
        combined.addAll(RestfulMeal.mahlzeit);
        combined.addAll(RestfulMeasure.measurements);

        userid = String.valueOf(RestfulUser.activeUser.getId());
        MeasurementFragment.setUserid(userid);


        Comparator<Object> comparator = new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                if(lhs instanceof meal && rhs instanceof  meal){
                    int result = ((meal)lhs).getDate().compareTo(((meal)rhs).getDate());
                    if(result != 0){
                        return result;
                    }else{
                        return ((meal)lhs).getTime().compareTo(((meal)rhs).getTime());
                    }


                }else if(lhs instanceof meal && rhs instanceof measurement){

                            int result = ((meal)lhs).getDate().compareTo(((measurement)rhs).getDate());
                    if(result != 0){
                        return result;
                    }else{
                        return ((meal)lhs).getTime().compareTo(((measurement)rhs).getTime());
                    }

                }else if(lhs instanceof measurement && rhs instanceof measurement){
                   int result =  ((measurement)lhs).getDate().compareTo(((measurement)rhs).getDate());
                    if(result != 0){
                        return result;
                    }else{
                        return ((measurement)lhs).getTime().compareTo(((measurement)rhs).getTime());
                    }

                }else if(lhs instanceof measurement && rhs instanceof meal){
                    int result = ((measurement)lhs).getDate().compareTo(((meal)rhs).getDate());
                    if(result != 0){
                        return result;
                    }else{
                        return ((measurement)lhs).getTime().compareTo(((meal)rhs).getTime());
                    }

                }
             return -1;
            }
        };
        Collections.sort(combined, comparator);
        Collections.reverse(combined);

        //Die Datenbankelemente werden durchlaufen und in die TextViews gefüllt
        Iterator<Object> it = combined.iterator();
        int index = 0;
        while(it.hasNext()){
            Object tmp = it.next();
            if(tmp instanceof meal){
                meal mt = (meal) tmp;
                art[index] = mt.getFoodkind();
                gericht[index] = mt.getFood();
                datum[index] = sdfDate.format(mt.getDate()) + "/" + sdfTime.format(mt.getTime());
                Log.d("Inhalt:" , "" + mt);


            }else if(tmp instanceof measurement){
                measurement ms = (measurement) tmp;
                art[index] = "Messung";
                gericht[index] = ms.getmvalue() + " mg/dl";
                datum[index] = sdfDate.format(ms.getDate()) + "/" + sdfTime.format(ms.getTime());
                Log.d("Inhalt:" , "" + ms);


                String hoechstdatum = datum[0];
                String aktueldatum = datum[index];
                Log.d("DAAAAAAAASL", hoechstdatum);
                SimpleDateFormat sdfDestination = new SimpleDateFormat("dd.MM");
                try{
                date = sdfDestination.parse(hoechstdatum);
                festjodadate = date.getTime();

                date2 = sdfDestination.parse(aktueldatum);
                aktuelljodadate = date2.getTime();

                    Log.e("SDFDESTINATION", "" + date + " " + date2 + " " + aktuelljodadate);
                    festjodaTime = new DateTime(festjodadate);
                    aktuelljodaTime = new DateTime(aktuelljodadate);

                }catch(ParseException e){
                e.printStackTrace();
                }

                DateTime minusjodatime3 = festjodaTime.minusMonths(3);
                Log.e("MINUSJODA", " " + minusjodatime3 + " " + minusjodatime3.getMillis() + " " + aktuelljodaTime + " " + aktuelljodaTime.getMillis());
                if(aktuelljodaTime.getMillis() >= minusjodatime3.getMillis()){
                    Log.e("PHEW", "PFFFFRZ");
                    h1a += ms.getmvalue() * 0.0555;
                    h1count++;
                }

            }
            index++;
        }

        Log.d("HLASCHNITT", " " + h1a +" " + h1count);
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        h1schnitt = h1a * 0.09148 + 2.152;
        outproz = formatter.format(h1schnitt);

        schnitt = (h1schnitt - 2.152) * 10.931;
        outmg = formatter.format(schnitt);



        Log.e("h1schnitt", " " + h1schnitt+ " " + h1count);

        Log.d("UebersichtFragment", "SIZE: " + size);
        Log.d("SIZE"," " + combined.size() + " meal" + RestfulMeal.mahlzeit.size() + "measure" + RestfulMeasure.measurements.size());
    }
    public void refresh() {

        Log.e("RESUMED", "OK");

        Log.e("schedulTASK", "" + scheduleTaskExecutor.isShutdown());


        changeFragment = new UebersichtFragment();
        ft.replace(R.id.fl_content_frame, changeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        scheduleTaskExecutor.shutdownNow();
        swtch = 0;

    }



    @Override
    public void onStop() {
        super.onPause();
        Log.e("STOPPED", "LUCKY?");
        scheduleTaskExecutor.shutdownNow();
    }


    @Override
    public void setMeasurementReady() {
        Log.d("MEASUREMENTREADY", "UEBERSICHT");
        refresh();
    }
}