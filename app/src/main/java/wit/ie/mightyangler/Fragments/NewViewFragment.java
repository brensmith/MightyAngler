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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import wit.ie.mightyangler.Activities.SplashActivity;
import wit.ie.mightyangler.R;




public class NewViewFragment extends Fragment implements AdapterView.OnItemClickListener {


    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView listView;
    View view;
    Cursor cursor;
    EditText refineEditText;

    static String editId = "0"; //The ID that's used to store the ID of any record selected for editing
                                //via a dialog box


    public NewViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_view, container, false);

        return view;
    }



    /*
    Initially populates the ListView with all records stored in the database, but offers an EditText
    the user may enter search terms into to refine the results displayed.
     */
    public void onViewCreated(View view, Bundle savedInstanceState){

        listView = (ListView)getView().findViewById(R.id.viewAllList);
        cursor = SplashActivity.myDb.getAllData();

        while(cursor.moveToNext()){
            arrayList.add("("+cursor.getString(0)+") "+cursor.getString(1)+"  "+cursor.getString(4)+" ("+cursor.getString(6)+")");
        }


        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        refineEditText = (EditText) getView().findViewById(R.id.refineEditText);
        refineEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    arrayList.clear();

                    String searchTerm = refineEditText.getText().toString();
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
        });

    }


    /*
    The onItemClick method for the ListView listener, displays dialog showing all record details and
    offers edit and delete options.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

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
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewViewFragment()).addToBackStack("View Data").commit();
                Cursor recordCheck = SplashActivity.myDb.getAllData();
                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No more records exist.", Toast.LENGTH_LONG);
                    msg.show();
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).addToBackStack("View Data").commit();
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewViewFragment()).addToBackStack("View Data").commit();
                }

            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                editId = cursor.getString(0);
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new EditDataFragment()).addToBackStack("View All").commit();

            }
        });

        builder.show();

    }
}
