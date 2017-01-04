package cg.stevendende.sunshine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by STEVEN on 03/09/2016.
 */

public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> adapter;

    private ListView weatherList;

    public static String EXTRA_WEATHER_FORECAST = "cg.stevendende.sunshine.extra.weather";
    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate fragment menu in Activity
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_forecast, container, false);

        weatherList = (ListView) rootView.findViewById(R.id.list_view_forecast);

        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                new ArrayList<String>());

        weatherList.setAdapter(adapter);

        weatherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(EXTRA_WEATHER_FORECAST, ((TextView)view).getText());
                startActivity(intent);
            }
        });

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
                updateWeather();
                break;

            default:
                break;

        }
        return true;
    }

    private void updateWeather(){
        String location = SettingsActivity.getUserLocation(getActivity());
        String units = SettingsActivity.getPreferedUnits(getActivity());

        new ServerAsyncTask(){
            @Override
            protected void onPostExecute(String[] forecastArray) {
                updateList(forecastArray);
            }
        }.execute(location, units);
    }

    private void updateList(String[] forecastArray){

        if (forecastArray==null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            adapter.addAll(forecastArray);
        else
        {
            adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    forecastArray);
            weatherList.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

        updateWeather();
    }
}