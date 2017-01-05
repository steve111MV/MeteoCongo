package cg.stevendende.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    private String forecastDetails;
    private ShareActionProvider mShareActionProvider;

    public DetailFragment() {
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
        View rootView  = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView tvDetails = (TextView) rootView.findViewById(R.id.tvDetails);

        Bundle data = getArguments();
        if(data!=null) {
            forecastDetails = data.getString(ForecastFragment.EXTRA_WEATHER_FORECAST);
            tvDetails.setText(forecastDetails);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            setShareIntent(createShareIntent());
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the item id for
        int id = item.getItemId();

        //We call the settings activity
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_share) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                startActivity(createShareIntent());
            }

            return true;
        }
        else if (id == R.id.action_email) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            setShareIntent(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Call to update the share intent
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    //called from both OS Version 14 & lesser
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, forecastDetails +" "+ getString(R.string.sunshine_hashtag));
        shareIntent.setType("text/plain");

        //it avoids the backPress to lead me into somewhere else
        // in the the app that's going to handle my Share
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        return shareIntent;
    }

}