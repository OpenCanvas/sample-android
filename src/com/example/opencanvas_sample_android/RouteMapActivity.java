package com.example.opencanvas_sample_android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class RouteMapActivity extends FragmentActivity {

    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);

        // Get the id of the route that was selected
        route = getIntent().getParcelableExtra("route");
        
        // Set title
        setTitle(route.name);

        // Show the Up button in the action bar
        setupActionBar();

        // Add map fragment
        if (savedInstanceState == null) {
            RouteMapFragment frag = RouteMapFragment.newInstance(route);
            frag.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, frag, "map").commit();
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
