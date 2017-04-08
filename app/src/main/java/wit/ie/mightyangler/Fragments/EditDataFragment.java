package wit.ie.mightyangler.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import wit.ie.mightyangler.Activities.SplashActivity;
import wit.ie.mightyangler.R;


public class EditDataFragment extends Fragment implements View.OnClickListener {


    TextView speciesTitle, weightTitle, baitTitle, locationTitle, weatherTitle,
    dateTitle, speciesText,baitText, weatherText;
    EditText  weightText, locationText, dateText;
    Spinner editSpinner;
    Button editButton;
    ArrayAdapter selectAdapter;
    String editId;


    public EditDataFragment() {
        // Required empty public constructor
    }


    /*
    All bindings carried out and listeners set before view returned. Spinner also populated
    and set to 'instruction' entry for user to see.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_data, container, false);

        speciesTitle = (TextView) view.findViewById(R.id.speciesTitle);
        weightTitle = (TextView) view.findViewById(R.id.weightTitle);
        baitTitle = (TextView) view.findViewById(R.id.baitTitle);
        locationTitle = (TextView) view.findViewById(R.id.locationTitle);
        weatherTitle = (TextView) view.findViewById(R.id.weatherTitle);
        dateTitle = (TextView) view.findViewById(R.id.dateTitle);

        speciesText = (TextView) view.findViewById(R.id.speciesText);
        weightText = (EditText) view.findViewById(R.id.weightText);
        baitText = (TextView) view.findViewById(R.id.baitText);
        locationText = (EditText) view.findViewById(R.id.locationText);
        weatherText = (TextView) view.findViewById(R.id.weatherText);
        dateText = (EditText) view.findViewById(R.id.dateText);

        editSpinner = (Spinner) view.findViewById(R.id.editSpinner);
        editButton = (Button) view.findViewById(R.id.editButton);

        speciesText.setOnClickListener(this);
        baitText.setOnClickListener(this);
        weatherText.setOnClickListener(this);
        editButton.setOnClickListener(this);

        final Cursor cursor = SplashActivity.myDb.getAllData();

        selectAdapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item);
        selectAdapter.add("Select Record"); //instruction entry
        while (cursor.moveToNext()) {
            selectAdapter.add("("+cursor.getString(0) + ") " + cursor.getString(1) + " - " + cursor.getString(6));
        }
        selectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinner.setAdapter(selectAdapter);
        editSpinner.setSelection(0, false);
        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                makeVisible();

                if (cursor.moveToPosition(position - 1)) { //moves spinner back to 'instruction' entry

                    editId = cursor.getString(0);
                    speciesText.setText(cursor.getString(1));
                    weightText.setText(cursor.getString(2));
                    baitText.setText(cursor.getString(3));
                    locationText.setText(cursor.getString(4));
                    weatherText.setText(cursor.getString(5));
                    dateText.setText(cursor.getString(6));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    /*
    When the view is returned this code checks the string stored in NewViewFragment, if this is not
    0 it means that this fragment has not been called from the navigation drawer or main menu, it has
    been called via a dialog in the View, Search or Delete fragments. The number in string is the ID
    of the record to be edited, it is changed from a string to an integer and the spinner is set to
    that ID, filling in all the fields in this framgemmt. The editId is then set back to "0" to ensure
    this record won't be loaded again unless specified.
     */
    public void onViewCreated(View view, Bundle onSavedStateInstance){

        String loadId = NewViewFragment.editId;

        if(loadId != "0"){

            editSpinner.setSelection(Integer.parseInt(loadId));
            makeVisible();
            NewViewFragment.editId = "0";

        }

    }


    /*
    When the view is created the majority of the fields are invisible for a cleaner look,
    calling this method will make them visible, used when the user has set the spinner to a
    record.
     */
    private void makeVisible(){

        speciesTitle.setVisibility(View.VISIBLE);
        weightTitle.setVisibility(View.VISIBLE);
        baitTitle.setVisibility(View.VISIBLE);
        locationTitle.setVisibility(View.VISIBLE);
        weightTitle.setVisibility(View.VISIBLE);
        weatherTitle.setVisibility(View.VISIBLE);
        dateTitle.setVisibility(View.VISIBLE);
        speciesText.setVisibility(View.VISIBLE);
        weightText.setVisibility(View.VISIBLE);
        baitText.setVisibility(View.VISIBLE);
        locationText.setVisibility(View.VISIBLE);
        weatherText.setVisibility(View.VISIBLE);
        dateText.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);

    }



    /*
    The onClick method listening for events on the spinner, the date field and the save button.
    There is validation on the save button, ensuring no fields have been left empty. The species,
    bait used and weather TextViews launch dialogs when clicked, offering the user the chance to
    change the data currently displayed.
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.speciesText:

                final ArrayAdapter<CharSequence> speciesAdapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.species_array, android.R.layout.select_dialog_item);

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(getActivity());

                alertBuild.setAdapter(speciesAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        speciesText.setText(speciesAdapter.getItem(which));
                        dialog.dismiss();
                    }
                });

                alertBuild.show();

                break;


            case R.id.baitText:

                final ArrayAdapter<CharSequence> baitAdapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.bait_array, android.R.layout.select_dialog_item);

                AlertDialog.Builder alertBuild1 = new AlertDialog.Builder(getActivity());

                alertBuild1.setAdapter(baitAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        baitText.setText(baitAdapter.getItem(which));
                        dialog.dismiss();
                    }
                });

                alertBuild1.show();

                break;


            case R.id.weatherText:

                final ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.weather_array, android.R.layout.select_dialog_item);

                AlertDialog.Builder alertBuild2 = new AlertDialog.Builder(getActivity());

                alertBuild2.setAdapter(weatherAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        weatherText.setText(weatherAdapter.getItem(which));
                        dialog.dismiss();
                    }
                });

                alertBuild2.show();

                break;


            case R.id.dateText:

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;


            case R.id.editButton:

                /*
                The dialog launched by this button offers the user 3 choices, Confirm, Discard and Cancel.
                Discarding will replace the fragment with the main menu fragment, not saving any changes made.
                Cancelling will dismiss the dialog and allow the user to continue with modifications. Confirming
                will send the data to the editRecord() method and the record will be updated.
                 */
                final FragmentManager fragmentManager = getFragmentManager();
                if (speciesText.getText().toString().matches("Click to Select") || weightText.getText().toString().matches("") || baitText.getText().toString().matches("Click to Select") ||
                        locationText.getText().toString().matches("") || weatherText.getText().toString().matches("Click to Select") || dateText.getText().toString().matches("")) {
                    Toast message = Toast.makeText(getActivity(), "Please enter all details.", Toast.LENGTH_LONG);
                    message.show();
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Choose action:");
                    builder.setMessage("Cancel to continue editing, Discard to exit editor, or Confirm to save changes.");

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String speciesIn = speciesText.getText().toString();
                            String weightIn = weightText.getText().toString();
                            String baitIn = baitText.getText().toString();
                            String locationIn = locationText.getText().toString();
                            String weatherIn = weatherText.getText().toString();
                            String dateIn = dateText.getText().toString();

                            SplashActivity.myDb.editRecord(editId, speciesIn, weightIn, baitIn, locationIn, weatherIn, dateIn);
                            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new EditDataFragment()).addToBackStack("Edit Data").commit();
                            Toast message = Toast.makeText(getActivity(), "Record updated.", Toast.LENGTH_LONG);
                            message.show();

                        }
                    });


                    builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MainMenuFragment()).addToBackStack("Edit Data").commit();
                        }
                    });


                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();

                        }
                    });

                    builder.show();

                }
        }


    }




    Calendar myCalendar = Calendar.getInstance();

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

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        dateText.setText(sdf.format(myCalendar.getTime()));
    }
}
