package wit.ie.mightyangler.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import wit.ie.mightyangler.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMapFragment extends android.app.Fragment implements OnMapReadyCallback {

    //GoogleMap Object
    private GoogleMap mMap;




    // Required empty public constructor
    public static MyMapFragment newInstance() {
        MyMapFragment fragment = new MyMapFragment();
        return fragment;

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mapFragment gets managed by the getChildFragmentManager()
        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        //Pulls down the map for google services
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*
        this is our default position of the map.
        these coordinates were chosen because they are in the centre of ireland
        and allow the map of ireland to be displayed in full
        */
        LatLng latlng = new LatLng(53.419456, -7.936307);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //adds gps to the map
        mMap.setMyLocationEnabled(true);
        //adds traffic details to the map
        mMap.setTrafficEnabled(true);
        //adds zoom in/out buttons to the map
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7));
        //on Long Click will add a marker to the map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                mMap.addMarker(new MarkerOptions().position(point).title("").snippet(""));
            }
        });



        // Add a marker in Dublin and move the camera
        //LatLng ireland = new LatLng(53.350140, -6.266155);
        //mMap.addMarker(new MarkerOptions().position(ireland).title("Dublin").snippet("Snippet"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ireland));

        /*Hard Coded Points on the map. LatLang holds a set of coordinates.
        Below are latLang's  for fishing locations in ireland.
        mMap.addMarker(new MarkerOptions().position(LatLang) adds a maker at the latLang coordinate.
        .title() .snippet() adds a title and a description to the marker
         */
        LatLng carrigavantry = new LatLng(52.172621, -7.196225);
        mMap.addMarker(new MarkerOptions().position(carrigavantry).title("Carrigavantry Lake").snippet("Brown & Rainbow Trout to 20 lbs permit 25 euro"));

        LatLng ballyscanlon = new LatLng(52.177570, -7.209293);
        mMap.addMarker(new MarkerOptions().position(ballyscanlon).title("Ballyscannlon Lake").snippet("Brown & Rainbow Trout to 20 lbs permit 10 euro"));

        LatLng adaire = new LatLng(52.306981, -7.227214);
        mMap.addMarker(new MarkerOptions().position(adaire).title("Adaire Springs Lake").snippet("Brown & Rainbow Trout to 20 lbs permit 25 euro"));

        LatLng knockaderry = new LatLng(52.216367, -7.237313);
        mMap.addMarker(new MarkerOptions().position(knockaderry).title("Knockaderry").snippet("Brown & Rainbow Trout to 20 lbs permit 25 euro"));

        LatLng aderra = new LatLng(51.914918, -8.090127);
        mMap.addMarker(new MarkerOptions().position(aderra).title("Lough Aderra").snippet("Carp & Bream to 15 lbs permit 20 euro"));



    }
}
