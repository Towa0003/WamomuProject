package fh.kl.wamomu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import fh.kl.wamomu.R;

/**
 * Created by Thundernator on 04.11.13.
 */
public class NavigationDrawer extends Activity {

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

    final CharSequence[] items = {"Mahlzeit", "Messung"};

    private ArrayList<NavigationDrawerItem> navDrawerItems;
    private NavigationDrawerAdapter adapter;

    Fragment changeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        changeFragment = new UebersichtFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
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
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Create ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //return super.onOptionsItemSelected(item);

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                add();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        //Beim Klick auf ein Element, wird das zugehörige Fragment geöffnet
        private void displayView(int position) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch(position) {
                case 0:
                    changeFragment = new UebersichtFragment();
                    break;
                case 1:
                    changeFragment = new MeasurementFragment();
                    break;
                case 2:
                    changeFragment = new MealsFragment();
                    break;
                case 3:
                    changeFragment = new SettingsFragment();
                    break;
                case 4:
                    changeFragment = new StatistikFragment();
                    break;
                default:
                    break;
            }

            ft.replace(R.id.fl_content_frame, changeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

            // update selected item and title, then close the drawer
            drawerListView.setItemChecked(position,true);
            drawerListView.setSelection(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //wenn in der ActionBar das "+" gedrückt wird, öffnet sich ein Dialog mit der Auswahlmöglichkeit,
    //ob man eine Messung oder Mahlzeit hinzufügen möchte
    private void add() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hinzufügen").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (which == 0) {
                    changeFragment = new MealsFragment();
                    MealsFragment.meals = 1;
                } else if (which == 1) {
                    changeFragment = new MeasurementFragment();
                    MeasurementFragment.dia = 1;
                }

                ft.replace(R.id.fl_content_frame, changeFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        });

        builder.show();


    }

}
