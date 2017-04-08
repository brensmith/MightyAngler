package wit.ie.mightyangler.Activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import wit.ie.mightyangler.Fragments.AboutFragment;
import wit.ie.mightyangler.Fragments.AddDataFragment;
import wit.ie.mightyangler.Fragments.EditDataFragment;
import wit.ie.mightyangler.Fragments.MainMenuFragment;
import wit.ie.mightyangler.Fragments.MyMapFragment;
import wit.ie.mightyangler.Fragments.NewDeleteFragment;
import wit.ie.mightyangler.Fragments.NewSearchFragment;
import wit.ie.mightyangler.Fragments.NewViewFragment;
import wit.ie.mightyangler.R;

/*
This activity will display the various fragments the app will use, the user
navigates between them through the use of a FragmentManager
 */
public class MainActivityNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FragmentManager fragmentManager = getFragmentManager();

    /*
    When the activity is created it will bind to the relevant XML layout file,
    create the navigation drawer and carry out a fragment transaction; replacing
    the empty frame with the main menu fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    /*
    When the hardware back button is pressed the app checks if the navigation drawer
    is open, if so the back button closes it, if not the app calls the last used fragment
    back from the BackStack of previously used fragments.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        getFragmentManager().popBackStackImmediate();
    }


    /*
    Setting up the settings menu at top right of app, will be used to
    offer an 'Exit App' option.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_navigation, menu);
        return true;
    }


    /*
    Deals with the item selected from the settings menu, in this case there is only one
    option, exit app. When the app registers the selection a dialog window opens asking the
    user to confirm or cancel, confirming calls finish() and the app shuts down.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit_app) {

            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
            confirmDialog.setMessage("Confirm close app?");
            confirmDialog.setCancelable(true);
            confirmDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                    finish();
                }
            });

            confirmDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );

            AlertDialog showDialog = confirmDialog.create();
            showDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    This allows the user to navigate through the different fragments using the navigation drawer. The
    user selects an option from the drawer, the app registers the selection and replaces the current
    fragment with one of the users choosing. Before certain fragments are loaded (view, search, delete, edit)
    a check is carried out on the database to ensure records exist, if none do a toast message is
    displayed and the transaction is stopped.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Cursor recordCheck = SplashActivity.myDb.getAllData();

        if (id == R.id.nav_view_data) {

            if(recordCheck.getCount() == 0){
                Toast msg = Toast.makeText(this, "No records exist.", Toast.LENGTH_LONG);
                msg.show();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewViewFragment()).addToBackStack("View Data").commit();
            }
        }
        else if (id == R.id.nav_add_data) {
            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new AddDataFragment()).addToBackStack("Add Data").commit();
        }
        else if (id == R.id.nav_search_data) {
            if(recordCheck.getCount() == 0){
                Toast msg = Toast.makeText(this, "No records exist.", Toast.LENGTH_LONG);
                msg.show();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewSearchFragment()).addToBackStack("").commit();
            }
        }
        else if (id == R.id.nav_delete_data) {
            if(recordCheck.getCount() == 0){
                Toast msg = Toast.makeText(this, "No records exist.", Toast.LENGTH_LONG);
                msg.show();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewDeleteFragment()).addToBackStack("").commit();
            }
        }
        else if (id == R.id.nav_map) {
            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MyMapFragment()).addToBackStack("Map").commit();
        }
        else if (id == R.id.nav_about) {
            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new AboutFragment()).addToBackStack("About").commit();
        }
        else if (id == R.id.nav_edit_data) {
            if(recordCheck.getCount() == 0){
                Toast msg = Toast.makeText(this, "No records exist.", Toast.LENGTH_LONG);
                msg.show();
            }else {
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new EditDataFragment()).addToBackStack("").commit();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
