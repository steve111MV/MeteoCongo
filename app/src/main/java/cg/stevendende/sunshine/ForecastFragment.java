package cg.stevendende.sunshine;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by STEVEN on 03/09/2016.
 */

public class ForecastFragment extends Fragment {

    String weatherForecastJSON;
    String userTownName;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_forecast, container, false);

        userTownName = "Brazzaville";

        new ServerAsyncTask(){
            @Override
            protected void onPostExecute(String s) {
                weatherForecastJSON = s;
                Log.e("forecast_JSON", weatherForecastJSON);
            }
        }.execute(userTownName);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh:

                new ServerAsyncTask(){
                    @Override
                    protected void onPostExecute(String s) {
                        weatherForecastJSON = s;
                        Log.e("forecast_JSON", weatherForecastJSON);
                    }
                }.execute(userTownName);
                break;
            default:

        }
        return true;
    }
}