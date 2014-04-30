package fh.kl.wamomu.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.RestfulUser;

public class NavigationDrawer extends ActionBarActivity {


    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // nav drawer title
    private CharSequence drawerTitle;

    // used to store app title
    private CharSequence title;

    // slide menu items
    private String[] drawerListViewItems;
    private TypedArray navMenuIcons;
    Menu mMenu;
    private boolean wechseln = true;

    final CharSequence[] items = {"Mahlzeit", "Messung"};

    private ArrayList<NavigationDrawerItem> navDrawerItems;
    private NavigationDrawerAdapter adapter;

    Fragment changeFragment;
    private static List<Integer> backs = new ArrayList<Integer>();
    public static final String ACTIVITY_EXTRA = "activity";
    public static int highvalue = 150;
    public static int lowvalue = 30;
    public static int initialColorHigh = 0xffff0000;
    public static int initialColorLow = 0xffff8500;
    public static int lowColor = 0xffff8500;
    public static int highcolor = 0xffff0000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        if (getIntent().getStringExtra(ACTIVITY_EXTRA) != null) {
            displayView(1);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_content_frame, changeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }




        changeFragment = new UebersichtFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content_frame, changeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        title = drawerTitle = getTitle();

        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.nav_drawer_items);

        // get list icons from strings.xml
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        // Layout of Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // get ListView
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavigationDrawerItem>();
        // adding nav drawer items to array
        navDrawerItems.add(new NavigationDrawerItem(drawerListViewItems[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavigationDrawerItem(drawerListViewItems[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavigationDrawerItem(drawerListViewItems[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavigationDrawerItem(drawerListViewItems[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavigationDrawerItem(drawerListViewItems[4], navMenuIcons.getResourceId(4, -1)));


        navMenuIcons.recycle();

        // Set the adapter for the list view
        adapter = new NavigationDrawerAdapter(getApplicationContext(), navDrawerItems);
        drawerListView.setAdapter(adapter);

        // enable and show "up" arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Create ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(title);
                // calling onPrepareOptionsMenu() to show action bar icons
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(drawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                // invalidateOptionsMenu();
            }
        };

        // Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // styling option
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        // on first time display view for first nav item
        if (savedInstanceState == null) {
            new DrawerItemClickListener().displayView(0);
        }



    }

    @Override
    public void onBackPressed() {
        Log.e("SIZEEEE", "" + backs.size() );
        OverviewArrayAdapter.mSelectedItem = -1;
        if (backs.size() > 1) {
            int k = backs.get(backs.size() - 2);
            displayView(k);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_content_frame, changeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            backs.remove(backs.size() - 1);

        }else if(backs.size() == 0){
            backs.clear();
        }

        else{
            Log.e("ELSE", "BACK");
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }

    }

    //OnClick the Fragment opens up
    public void displayView(int position) {
        switch (position) {
            case 0:
                changeFragment = new UebersichtFragment();
                UebersichtFragment.swtch = 1;
                getSupportActionBar().setTitle("Übersicht");
                break;
            case 1:
                changeFragment = new MeasurementFragment();
                getSupportActionBar().setTitle("Messungen");
                break;
            case 2:
                changeFragment = new MealsFragment();
                getSupportActionBar().setTitle("Mahlzeiten");
                break;
            case 3:
                changeFragment = new SettingsFragment();
                getSupportActionBar().setTitle("Einstellungen");
                break;
            case 4:
                changeFragment = new StatistikFragment();
                getSupportActionBar().setTitle("Statistik");
                break;
            default:
                break;
        }

    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerLayout.closeDrawer(drawerListView);
            displayView(position);
        }

        //OnClick the Fragment opens up
        public void displayView(int position) {
            backs.add(position);
            switch (position) {
                case 0:
                    changeFragment = new UebersichtFragment();
                    UebersichtFragment.swtch = 1;
                    getSupportActionBar().setTitle("Übersicht");
                    break;
                case 1:
                    changeFragment = new MeasurementFragment();
                    MeasurementFragment.swtch = 1;
                    getSupportActionBar().setTitle("Messungen");
                    break;
                case 2:
                    changeFragment = new MealsFragment();
                    getSupportActionBar().setTitle("Mahlzeiten");
                    break;
                case 3:
                    changeFragment = new SettingsFragment();
                    getSupportActionBar().setTitle("Einstellungen");
                    break;
                case 4:
                    changeFragment = new StatistikFragment();
                    getSupportActionBar().setTitle("Statistik");
                    break;
                default:
                    break;
            }


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_content_frame, changeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();


            // update selected item and title, then close the drawer
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        this.mMenu = menu;
        mMenu.getItem(0).setTitle("Ø \n" + UebersichtFragment.outmg + " mg/dl");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                add();
                mMenu.getItem(1).setEnabled(false);
                return true;
            case R.id.action_logoff:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.logout)
                        .setTitle("Möchten Sie sich wirklich ausloggen?")
                        .setMessage(RestfulUser.activeUser.getName() + " möchten Sie sich wirklich ausloggen?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                                backs.clear();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();

                return true;
            case R.id.user_id_label:
                Log.e("KLICK", "ACTOINBAR");
                change();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);


    }

    //wenn in der ActionBar das "+" gedrückt wird, öffnet sich ein Dialog mit der Auswahlmöglichkeit,
    //ob man eine Messung oder Mahlzeit hinzufügen möchte
    private void add() {

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hinzufügen").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fragment newFragment = null;

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (which == 0) {

                    Log.e("CLick", "CLICKED");
                    newFragment = new MealsFragment();
                    MealsFragment.meals = 1;

                } else if (which == 1) {
                    newFragment = new MeasurementFragment();
                    MeasurementFragment.dia = 1;
                }

                ft.replace(R.id.fl_content_frame, newFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                mMenu.getItem(1).setEnabled(true);
            }

        });

        builder.show();


    }
    private void change(){
        if(wechseln) {
            mMenu.getItem(0).setTitle("HbA1c \n" + UebersichtFragment.outproz + " %");
            wechseln = false;
        }else if(!false){
            wechseln = true;
            mMenu.getItem(0).setTitle("Ø \n" + UebersichtFragment.outmg + " mg/dl");
        }
    }


}
