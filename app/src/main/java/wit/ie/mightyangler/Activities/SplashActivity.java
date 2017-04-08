package wit.ie.mightyangler.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import wit.ie.mightyangler.Database.DatabaseHelper;
import wit.ie.mightyangler.R;

/**
 * Created by brendan on 25/02/2016.
 */
public class SplashActivity extends AppCompatActivity{

    public static DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent startMainScreen = new Intent(getApplicationContext(),MainActivityNavigation.class);
                    startActivity(startMainScreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        /*
        The following code checks to see if any records exist in the database, and if not it inserts
        12 unique records for testing purposes.
         */
        Cursor cursor = myDb.getAllData();

        if(cursor.getCount()==0) {
            db.execSQL("DELETE FROM sqlite_sequence WHERE name='fishing_table'");

            myDb.insertData("Rainbow Trout", "9", "Baited Hook", "The Lake, Waterford",
                    "Raining", "03/25/16");
            myDb.insertData("Cod", "8", "Lure", "Carnsore, Wexford", "Raining",
                    "03/25/16");
            myDb.insertData("Carp", "3", "Fly", "Curracloe, Wexford", "Sunny", "03/25/16");
            myDb.insertData("Tuna", "15", "Baited Hook", "The Quay, Waterford",
                    "Windy", "03/25/16");
            myDb.insertData("Rainbow Trout", "5", "Lures", "Templemore, Waterford",
                    "Raining", "03/25/16");
            myDb.insertData("Cod", "12", "Baited Hook", "Enniscorthy, Wexford", "Windy",
                    "03/25/16");
            myDb.insertData("Cod", "9", "Lure", "Curracloe, Wexford", "Sunny", "03/25/16");
            myDb.insertData("Rainbow Trout", "15", "Baited Hook", "The Quay, Waterford",
                    "Windy", "03/25/16");
            myDb.insertData("Flounder", "15", "Lures", "The Lake, Waterford",
                    "Overcast", "03/25/16");
            myDb.insertData("Mullet", "8", "Baited Hook", "Carnsore, Wexford", "Windy",
                    "03/25/16");
            myDb.insertData("Shark", "3", "Baited Hook", "Curracloe, Wexford", "Sunny", "03/30/16");
            myDb.insertData("Salmon", "15", "Fly", "The Quay, Waterford",
                    "Windy", "02/28/16");
            myDb.insertData("Carp", "5", "Lures", "Templemore, Waterford",
                    "Windy", "01/15/16");
            myDb.insertData("Mullet", "12", "Baited Hook", "Enniscorthy, Wexford", "Sunny",
                    "12/13/15");
            myDb.insertData("Bass", "9", "Fly", "Curracloe, Wexford", "Raining", "03/25/16");
            myDb.insertData("Brown Trout", "15", "Baited Hook", "The Quay, Waterford",
                    "Raining", "10/11/15");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the splash_activity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
