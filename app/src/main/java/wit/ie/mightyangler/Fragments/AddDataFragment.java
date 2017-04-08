package wit.ie.mightyangler.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import wit.ie.mightyangler.Activities.SplashActivity;
import wit.ie.mightyangler.R;


public class AddDataFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    EditText editSpecies, editWeight, editBait, editLocation, editWeather, editDate;
    Button buttonAdd;
    Button buttonListAll;
    View view;
//Create Calender Object
    Calendar myCalendar = Calendar.getInstance();
//Set onDateListener to the DatePickerDialogue
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };



    public AddDataFragment() {
        // Required empty public constructor
    }


    /*
    On creation of the view bindings will be carried out and listeners set to buttons
    and spinners, and spinners have adapters set.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_add_data, container, false);


        buttonAdd = (Button) view.findViewById(R.id.button_Add);
        buttonListAll = (Button) view.findViewById(R.id.button_ListAll);

        buttonAdd.setOnClickListener(this);
        buttonListAll.setOnClickListener(this);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) view.findViewById(R.id.spinner3);

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.species_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.bait_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(),
                R.array.weather_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner3.setAdapter(adapter3);

        return view;
    }




    /*
     The remaining bindings that could not previously be carried out are done so in
     onViewCreated, and an additional listener is set.
     */
    public void onViewCreated(View view, Bundle savedInstanceState){

        editSpecies = (EditText) getView().findViewById(R.id.edit_Species);
        editSpecies.setVisibility(View.INVISIBLE);

        editWeight = (EditText) getView().findViewById(R.id.edit_Weight);
        editBait = (EditText) getView().findViewById(R.id.edit_Bait);
        editBait.setVisibility(View.INVISIBLE);
        editLocation = (EditText) getView().findViewById(R.id.edit_Location);
        editWeather = (EditText) getView().findViewById(R.id.edit_Weather);
        editWeather.setVisibility(View.INVISIBLE);
        editDate = (EditText) getView().findViewById(R.id.edit_Date);
        editDate.setOnClickListener(this);

    }



    /*
    This is a method attached to the onClick listeners, it uses a switch statement linked to resource id's
    to determine which action to perform. If all data fields have been modified the Add button takes the entered
    data and passes it to the insertData() method, which creates a new record to store it. If any fields
    have been left unfilled or spinners unselected error messages will appear to prompt the user to complete
    the entry.

    The List All button is a remnant of our old 'view' features. If any records exist in the database an alert dialog
    is created which displays all the data neatly, but as a single string. If no data is stored a toast message
    informs the user. This should be changed to replace the current fragment with the NewViewFragment, or
    using a listview in the dialog instead of the current method.

    The edit date function launches a calender set to todays date, the user can accept this or choose
    their own date. Once selected the date is returned to the date field.
     */
    public void onClick(View v) {


        switch(v.getId()){

            case R.id.button_Add:

                if( editSpecies.getText().toString().equals("Click to Select") || (editWeight.getText().toString().length() == 0 ) || editBait.getText().toString().equals("Click to Select") || ( editLocation.getText().toString().length() == 0 ) || ( editWeather.getText().toString().length() == 0 ) || ( editDate.getText().toString().isEmpty() )) {
                    editWeight.setError("Please Enter Weight!");
                    editLocation.setError("Please Enter Location!");
                    Toast.makeText(getActivity(), "Please check fields, Data not inserted!", Toast.LENGTH_LONG).show();
                }

                else if (editWeight.getText().toString().length() > 0 || ( editLocation.getText().toString().length() > 0 )) {
                    SplashActivity.myDb.insertData(editSpecies.getText().toString(),
                            editWeight.getText().toString(),
                            editBait.getText().toString(),
                            editLocation.getText().toString(),
                            editWeather.getText().toString(),
                            editDate.getText().toString());
                    Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new AddDataFragment()).commit();
                }

                break;

            case R.id.button_ListAll:
                 //Checks to see is there any entries stored in the database, if yes cursor reads all entries into the StringBuilder called buffer and displays all information in alert dialogue window thats created in the show message method
                Cursor res = SplashActivity.myDb.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    showMessage("Error", "no data found");
                    res.moveToFirst();
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("SPECIES :" + res.getString(1) + "\n");
                    buffer.append("WEIGHT :" + res.getString(2) + "\n");
                    buffer.append("BAIT :" + res.getString(3) + "\n");
                    buffer.append("LOCATION :" + res.getString(4) + "\n");
                    buffer.append("WEATHER :" + res.getString(5) + "\n");
                    buffer.append("DATE :" + res.getString(6) + "\n\n");
                }


                //show all the data
                showMessage("Data", buffer.toString());

           break;

            case R.id.edit_Date:
                new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    }



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner)
        {
            //do this
            editSpecies.setText(item);

        }
        else if(spinner.getId() == R.id.spinner2)
        {
            //do this
            editBait.setText(item);
        }
        else if (spinner.getId() == R.id.spinner3)
        {
            //do this
            editWeather.setText(item);
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


// This method displays a dialogue window with all entries stored in the database when the user selects view data from the add data screen

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
