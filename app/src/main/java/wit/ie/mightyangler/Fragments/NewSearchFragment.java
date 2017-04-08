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



public class NewSearchFragment extends Fragment implements AdapterView.OnItemClickListener {


    EditText searchSpecies;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    Cursor cursor;



    public NewSearchFragment() {
        // Required empty public constructor
    }


    /*
    Bindings done and TextWatcher added to the EditText.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_new_search, container, false);
        searchSpecies = (EditText) view.findViewById(R.id.speciesSearch);
        listView = (ListView)view.findViewById(R.id.searchList);


        searchSpecies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                arrayList.clear();

                if(searchSpecies.getText().toString().isEmpty()){
                    listView.setVisibility(View.INVISIBLE);
                }
                else {

                    listView.setVisibility(View.VISIBLE);
                    String searchTerm = searchSpecies.getText().toString();
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

        return view;

    }


    public void onViewCreated(View view, Bundle savedInstanceState){

        listView.setOnItemClickListener(this);
    }


    /*
    onItemClick method for listView listener. Retrieves record selected by the user and displays
    it in a dialog with the option to edit or delete it.
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
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewViewFragment()).addToBackStack("View Data").commit();
                Cursor recordCheck = SplashActivity.myDb.getAllData();
                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No more records exist.", Toast.LENGTH_LONG);
                    msg.show();
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).addToBackStack("View Data").commit();
                }else {
                    //fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewSearchFragment()).addToBackStack("View Data").commit();
                }

            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                NewViewFragment.editId = cursor.getString(0);
                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new EditDataFragment()).addToBackStack("View All").commit();

            }
        });

        builder.show();

    }



}
