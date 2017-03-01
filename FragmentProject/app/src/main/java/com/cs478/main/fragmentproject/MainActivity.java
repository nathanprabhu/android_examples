package com.cs478.main.fragmentproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


//This class handles the fragements for Chicago.
public class MainActivity extends AppCompatActivity implements MyListFragment.ListSelectionListener {

    private final MyDetailFragment myDetailFragment = new MyDetailFragment();
    private final MyListFragment myListFragment = new MyListFragment();
    private FragmentManager myFragmentManager;
    private FrameLayout myListFrameLayout, myDetailFrameLayout;

    public static String[] listArray;
    public static String[] linksArray;

    // A boolean variable that indicates whether 2 fragments have to be displayed.
    // In case of portrait, this is set to false.
    // In case of landscape, this is set to true.
    boolean dualPane = false;

    // A variable that indicates the current activity that is visible to the user..
    String currentActivity = "Chicago";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // get the arrays with the places of interest in Chicago and their corresponding website addresses.
        listArray = getResources().getStringArray(R.array.chicagolistitems);
        linksArray = getResources().getStringArray(R.array.chicagowebpages);

        //check the orientatation and set the value of dualPane to true if it is landscape.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            dualPane = true;
        }

        // Get toolbar object to set options menu
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.bringToFront();
        setSupportActionBar(toolbar);

        // Get references to the ListFragment and to the DetailsFragment
        myListFrameLayout = (FrameLayout) findViewById(R.id.listfragment);
        myDetailFrameLayout = (FrameLayout) findViewById(R.id.detailsfragment);


        // Get a reference to the FragmentManager
        myFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

        // Add the list fragment to the layout
        fragmentTransaction.add(R.id.listfragment, myListFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        myFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }


    //This method is to ensure that the correct fragments are displayed when the orientation changes.
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            //If the new orientation is Portrait, then check if the details fragment was added in the previous orientation.
            //If yes, then details fragment occupies the full screen.
            if(myDetailFragment.isAdded()){
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
            dualPane = false;
        }
        else if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            //If the new orientation is Landscape, then check if the details fragment was added in the previous orientation.
            //If yes, then the list fragment and details fragment occupy 1:2 of the screen.
            if(myDetailFragment.isAdded()){
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
            }
            dualPane = true;
        }
    }

    //This method is also to ensure that the correct fragments are displayed when the orientation changes.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        System.out.println(getResources().getConfiguration().orientation);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            //If the new orientation is Portrait, then check if the details fragment was added in the previous orientation.
            //If yes, then details fragment occupies the full screen.
            if(myDetailFragment.isAdded()){
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
            dualPane = false;
        }
        else if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            //If the new orientation is Landscape, then check if the details fragment was added in the previous orientation.
            //If yes, then the list fragment and details fragment occupy 1:2 of the screen.
            if(myDetailFragment.isAdded()){
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
            }
            dualPane = true;
        }
        super.onConfigurationChanged(newConfig);
    }

    // This method is to ensure that the detail fragment -> list fragment is handled in portrait mode.
    @Override
    public void onBackPressed() {
        if (myFragmentManager.getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //This method handles layout changes when the user selects an item from the list.
    private void setLayout() {
        if(dualPane) {
            System.out.println("LANDSCAPE");
            // Determine whether the Detail fragment has been added
            if (!myDetailFragment.isAdded()) {
                // If not added, the list fragment occupies the entire screen
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
            } else {
                // If already added, list and detail fragments occupy 1:2 of the screen.
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
            }
        }
        else{
            System.out.println("PORTRAIT");
            if (!myDetailFragment.isAdded()) {
                // If not added, the list fragment occupies the entire screen
                System.out.println("AAAAAAAAAAAA");
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
            } else {
                // If already added, the detail fragment occupies the entire screen
                System.out.println("BBBBBBBBBBBBB");
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
                myDetailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
        }
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {
            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

            if (!myDetailFragment.isAdded()) {
                // Add the details fragment to the layout
                fragmentTransaction.add(R.id.detailsfragment, myDetailFragment);
            }
            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            myFragmentManager.executePendingTransactions();

        if (myDetailFragment.getShownIndex() != index) {

            // Tell the DetailFragment to show the quote string at position index
            myDetailFragment.showWebPage(index);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //This method inflates the menu with the menu_main.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //This method handles options menu items clicks.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case (R.id.switchtochicago):{
                    if(currentActivity!="Chicago") {
                        Intent chicagoIntent = new Intent(MainActivity.this, MainActivity.class);
                        currentActivity = "Chicago";
                        startActivity(chicagoIntent);
                    }
                return true;
            }
            case (R.id.switchtoindiana):{
                if(currentActivity!="Indiana") {
                    Intent indianaIntent = new Intent(MainActivity.this, IndianaActivity.class);
                    currentActivity = "Indiana";
                    startActivity(indianaIntent);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}