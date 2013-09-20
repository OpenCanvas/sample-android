package com.example.opencanvas_sample_android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.opencanvas_sample_android.Place.GetPlacesResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.maps.android.PolyUtil;

public class RouteMapFragment extends SupportMapFragment {

    public static RouteMapFragment newInstance(Route route) {
        RouteMapFragment frag = new RouteMapFragment();
        frag.route = route;
        return frag;
    }

    Route route;
    List<Place> places;

    @Override
    public void onResume() {
        super.onResume();
        GoogleMap map = getMap();
        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GoogleMap map = getMap();
        if (map != null) {
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create url
        String url = "http://api.opencanvas.co/v1.0/routes/" + route.id + "/places";

        // Create request
        Request<List<Place>> request = new Request<List<Place>>(Method.GET, url, null) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Required header
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-Mobzili-App-Key", getString(R.string.opencanvas_api_key));
                return headers;
            }

            @Override
            protected Response<List<Place>> parseNetworkResponse(NetworkResponse response) {
                try {
                    Gson gson = new Gson();
                    String json = new String(response.data);
                    GetPlacesResponse data = gson.fromJson(json, GetPlacesResponse.class);
                    return Response.success(data.places, null);
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(List<Place> response) {
                places = response;
                loadMap();
            }
        };

        // Make request
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public void loadMap() {
        // Get map
        final GoogleMap map = getMap();

        // If the map is null, log and return
        if (map == null) {
            Log.w(getTag(), "Map is not ready.");
            return;
        }

        // Create a map bounds builder
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();

        // Draw polyline on the map
        List<LatLng> poly = PolyUtil.decode(route.polyline);
        if (poly.size() > 0) {
            PolylineOptions options = new PolylineOptions()
                    .addAll(poly)
                    .width(6)
                    .color(Color.parseColor("#7fb82c"));
            map.addPolyline(options);
        }

        // Add polyline locations in the map bounds
        for (LatLng latlng : poly) {
            bounds.include(latlng);
        }

        // Create marker for each place
        for (Place place : places) {
            // Create new LatLng
            LatLng latlng = new LatLng(place.lat, place.lon);

            // Add place's LatLng to bounds
            bounds.include(latlng);

            // Add place marker
            MarkerOptions options = new MarkerOptions()
                    .title(place.name)
                    .position(latlng);
            map.addMarker(options);
        }

        // Center map based on calculated bounds
        int padding = (int) (36 * getResources().getDisplayMetrics().density);
        try {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), padding));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
