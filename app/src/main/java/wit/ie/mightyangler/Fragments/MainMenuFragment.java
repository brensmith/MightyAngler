package wit.ie.mightyangler.Fragments;


import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import wit.ie.mightyangler.Activities.SplashActivity;
import wit.ie.mightyangler.R;



public class MainMenuFragment extends Fragment implements View.OnClickListener {


    Button buttonAdd, buttonView, buttonAbout, buttonDelete, buttonSearch, buttonMap;


    public MainMenuFragment() {
        // Required empty public constructor
    }


    /*
    All binding carried out and listeners set on buttons.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        buttonAdd = (Button) view.findViewById(R.id.menu_add_button);
        buttonView = (Button) view.findViewById(R.id.menu_view_button);
        buttonAbout = (Button) view.findViewById(R.id.menu_about_button);
        buttonDelete = (Button) view.findViewById(R.id.menu_delete_button);
        buttonSearch = (Button) view.findViewById(R.id.menu_search_button);
        buttonMap = (Button) view.findViewById(R.id.menu_map_button);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMap.setOnClickListener(this);

        return view;
    }


    /*
    Just like the navigation drawer method this allows the user to navigate through the different fragments. The
    user selects an option by pressing a button, the app registers the selection and replaces the current
    fragment with one of the users choosing. Before certain fragments are loaded (view, search, delete, edit)
    a check is carried out on the database to ensure records exist, if none do a toast message is
    displayed and the transaction is stopped.
     */
    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getFragmentManager();
        Cursor recordCheck = SplashActivity.myDb.getAllData();

        switch(v.getId()){

            case R.id.menu_add_button:

                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new AddDataFragment()).addToBackStack("").commit();

                break;

            case R.id.menu_view_button:

                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No records exist.", Toast.LENGTH_LONG);
                    msg.show();
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewViewFragment()).addToBackStack("").commit();
                }

                break;

            case R.id.menu_delete_button:

                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No records exist.", Toast.LENGTH_LONG);
                    msg.show();
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewDeleteFragment()).addToBackStack("").commit();
                }
                break;

            case R.id.menu_search_button:

                if(recordCheck.getCount() == 0){
                    Toast msg = Toast.makeText(getActivity(), "No records exist.", Toast.LENGTH_LONG);
                    msg.show();
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new NewSearchFragment()).addToBackStack("").commit();
                }

                break;

            case R.id.menu_map_button:

                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new MyMapFragment()).addToBackStack("").commit();

                break;

            case R.id.menu_about_button:

                fragmentManager.beginTransaction().replace(R.id.fragmentFrame, new AboutFragment()).addToBackStack("").commit();

                break;

        }

    }
}
