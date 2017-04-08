package wit.ie.mightyangler.Fragments;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import wit.ie.mightyangler.Activities.SplashActivity;
import wit.ie.mightyangler.R;



public class NewDeleteFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    EditText deleteEditText;
    Button deleteAllButton;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    Cursor cursor;


    public NewDeleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_delete, container, false);


        return view;
    }


    /*
    All bindings and listeners added in, including a TextWatcher.
    We only make use of the afterTextChanged() method; after the user has added or removed a character
    the entered data is send to the query() method, which returns any results found. These are placed
    in a ListView. When next the user changes the text in EditText the current results are cleared to
    avoid replication and the search is run again.
     */
    public void onViewCreated(View view, Bundle savedInstanceState){


        deleteEditText = (EditText) getView().findViewById(R.id.deleteEditText);
        deleteAllButton = (Button) getView().findViewById(R.id.deleteButton);
        listView = (ListView) getView().findViewById(R.id.deleteList);

        deleteAllButton.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        deleteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                arrayList.clear();

                if (deleteEditText.getText().toString().isEmpty()) {
                    listView.setVisibility(View.INVISIBLE);
                } else {

                    listView.setVisibility(View.VISIBLE);
                    String searchTerm = deleteEditText.getText().toString();
                    cursor = SplashActivity.myDb.query(searchTerm);


                    while (cursor.moveToNext()) {
                        arrayList.add("(" + cursor.getString(0) + ") " + cursor.getString(1) + "  " + cursor.getString(4) + " (" + cursor.getString(6) + ")");
                    }


                    arrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            arrayList);
                    listView.setAdapter(arrayAdapter);

                }

            }
        });
    }


    /*
    This is the onItemClick method used with ListView listener. It determines which record to display
    in a dialog using the position of the user selection. The dialog offers two options; edit or delete.
    Delete will delete the record displayed and check if any records are left in the database. If there
    are none left the user is returned to the main menu, if there are records the ListView is reloaded
    with an up to date list of all records.

    If edit is selected a String is updated with ID of the selected record and the EditDataFragment
    is called. After creation it will look to this String, if it is anything other than "0" the number
    stored will be used as an ID to move the selection spinner to the position of the selected record.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        final FragmentManager fragmentManager = getFragmentManager();
        cursor.moveToPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Catch Info for ID "+ cursor.getString(0));
        builder.setMessage("Species:       " + cursor.getString(1) + "\nWeight:         " + cursor.getString(2) +
                "\nBait:              " + cursor.getString(3) + "\nLocation:     " + cursor.getString(4) + "\nWeather:       "
                + cursor.getString(5) + "\nDate:             " + cursor.getString(6));


        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                SplashActivity.myDb.deleteRecord(cursor.getString(0));
                Toast message = Toast.makeText(getActivity(), "Catch deleted.", Toast.LENGTH_SHORT);
                message.show();
                //fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewDeleteFragment()).addToBackStack("Delete Data").commit();

                Cursor recordCheck = SplashActivity.myDb.getAllData();
                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No more records exist.", Toast.LENGTH_LONG);
                    msg.show();
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).addToBackStack("Delete Data").commit();

                }else {

                    arrayList.clear();
                    String searchTerm = deleteEditText.getText().toString();
                    recordCheck = SplashActivity.myDb.query(searchTerm);

                    while (recordCheck.moveToNext()) {
                        arrayList.add("(" + recordCheck.getString(0) + ") " + recordCheck.getString(1) + "  " + recordCheck.getString(4) + " (" + cursor.getString(6) + ")");
                    }

                    listView.setAdapter(arrayAdapter);
                    //fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewSearchFragment()).addToBackStack("View Data").commit();
                }

            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                NewViewFragment.editId = cursor.getString(0);
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new EditDataFragment()).addToBackStack("Delete Data").commit();

            }
        });

        builder.show();

    }


    /*
    This is the onClick method for the listener attached to the Delete All button. It calls the
    deleteAll() method which removes all records from the database. Once carried out the user is
    returned to the main menu and a toast message is displayed.
     */
    @Override
    public void onClick(View v) {

        arrayList.clear();
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());

        confirmDialog.setMessage("Confirm delete all?");
        confirmDialog.setCancelable(true);

        confirmDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SplashActivity.myDb.deleteAll();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).addToBackStack("").commit();
                Toast message = Toast.makeText(getActivity(), "All records removed.", Toast.LENGTH_LONG);
                message.show();
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
}
