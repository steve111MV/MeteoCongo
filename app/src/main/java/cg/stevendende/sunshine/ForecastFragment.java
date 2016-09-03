package cg.stevendende.sunshine;

import android.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by STEVEN on 03/09/2016.
 */

public class ForecastFragment extends Fragment {

    String weatherForecastJSON;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String forecastUrl = getActivity().getResources().getString(R.string.weather_url);

        new ServerAsyncTask(){
            @Override
            protected void onPostExecute(Object o) {
                weatherForecastJSON = (String) o;
            }
        }.execute(forecastUrl);


        return rootView;
    }
}