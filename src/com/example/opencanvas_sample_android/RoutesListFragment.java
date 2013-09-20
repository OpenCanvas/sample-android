package com.example.opencanvas_sample_android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.opencanvas_sample_android.Route.GetRoutesResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RoutesListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListShown(false);
        setEmptyText("Nothing here yet :(");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create url
        String url = "http://api.opencanvas.co/v1.0/routes";

        // Create request
        Request<List<Route>> request = new Request<List<Route>>(Method.GET, url,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setEmptyText(error.getMessage());
                        setListShown(true);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Required header
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-Mobzili-App-Key", getString(R.string.opencanvas_api_key));
                return headers;
            }

            @Override
            protected Response<List<Route>> parseNetworkResponse(NetworkResponse response) {
                try {
                    Gson gson = new Gson();
                    String json = new String(response.data);
                    GetRoutesResponse data = gson.fromJson(json, GetRoutesResponse.class);
                    return Response.success(data.routes, null);
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(List<Route> response) {
                ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        response);
                setListAdapter(adapter);
                setListShown(true);
            }
        };

        // Make request
        Volley.newRequestQueue(getActivity()).add(request);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Route route = (Route) l.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), RouteMapActivity.class);
        intent.putExtra("route", route);
        startActivity(intent);
    }

}
