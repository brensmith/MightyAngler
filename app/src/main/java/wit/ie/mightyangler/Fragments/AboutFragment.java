package wit.ie.mightyangler.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wit.ie.mightyangler.R;


public class AboutFragment extends Fragment implements View.OnClickListener{

    Button blogButton;


    public AboutFragment() {
        // Required empty public constructor
    }


    /*
    On creation of the view the only button in this fragment goes through binding and has
    a listener set to it.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        blogButton = (Button) view.findViewById(R.id.blogButton);
        blogButton.setOnClickListener(this);
        return view;
    }




    /*
    When the button is clicked an Intent launches the devices default browser and automatically
    navigates to the specified website.
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.blogButton:

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mightyangler.wordpress.com/2016/04/09/blog-post-title/"));

                startActivity(browserIntent);
        }

    }
}
