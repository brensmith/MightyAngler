package wit.ie.mightyangler.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/*
This class holds the constructor and methods required
for creating and managing the SQLite database used by the app.
*/

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Angler.db";
    public static final String TABLE_NAME = "fishing_table";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "SPECIES";
    public static final String COL_2 = "WEIGHT";
    public static final String COL_3 = "BAIT";
    public static final String COL_4 = "LOCATION";
    public static final String COL_5 = "WEATHER";
    public static final String COL_6 = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,SPECIES TEXT,WEIGHT DECIMAL,BAIT TEXT,LOCATION TEXT, WEATHER TEXT, DATE DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /*
    A method used to insert a record to the database, and check if the action was successful or
    otherwise.
     */
    public Boolean insertData (String species, String weight, String bait, String location, String weather, String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, species);
        contentValues.put(COL_2, weight);
        contentValues.put(COL_3,bait);
        contentValues.put(COL_4,location);
        contentValues.put(COL_5, weather);
        contentValues.put(COL_6, date);

        long Result = db.insert(TABLE_NAME,null,contentValues);
        if (Result == -1 )
            return false;
        else
            return true;
    }


    /*
    A method that gathers all the records in the database and returns a cursor to the point of call.
     */
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }



    /*
    A method for editing a record in the database, the relevant ID along with data to update the
    record is passed in, a cursor is used to find the record with that ID then update() is used.
     */
    public void editRecord(String id, String species, String weight, String bait, String location,
                             String weather, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM fishing_table WHERE ID = '" + id + "'", null);

        if (cursor.moveToFirst()) {
            ContentValues recordContent = new ContentValues();
            recordContent.put(COL_1, species);
            recordContent.put(COL_2, weight);
            recordContent.put(COL_3, bait);
            recordContent.put(COL_4, location);
            recordContent.put(COL_5, weather);
            recordContent.put(COL_6, date);
            db.update(TABLE_NAME, recordContent, "id = ?", new String[]{id});

        }
        cursor.close();
    }



    /*
    This method is used to find records matching user input, it takes the entered text and attempts to
    find all records which have the input character(s) in their ID, Species, Location, Bait, Weight or
    Weather column. A cursor with partial or exact matches is returned.
     */
    public Cursor query(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM fishing_table WHERE ID LIKE '%"+name+"%' OR SPECIES LIKE '%" + name + "%' " +
                "OR LOCATION LIKE '%"+name+"%' OR BAIT LIKE '%"+name+"%' OR WEIGHT LIKE '%"+name+"%' " +
                "OR WEATHER LIKE '%"+name+"%'", null);
        return cursor;
    }



    /*
    A method to remove all records from the database using a simple SQL statement execSQL()
     */
    public void deleteAll(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }



    /*
    A method to remove a single record from the database. The point of call passes in a specific ID
    for deletion, the method searches the ID column and deletes it when found.
     */
    public void deleteRecord(String id) {

        SQLiteDatabase theDatabase = this.getWritableDatabase();

        try {
            theDatabase.delete(TABLE_NAME, "id = ?", new String[]{id});
        } catch (Exception e) {
           e.printStackTrace();
        }

        theDatabase.close();

    }

}
