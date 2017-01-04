package cg.stevendende.sunshine;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();

            Log.e("on create __ null", "1st creation");
        }

        Log.e("on create __ ", "default");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //We call the settings activity
        if (id == R.id.action_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_prefered_location) {
            showPreferedLocationOnMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPreferedLocationOnMap(){
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);

        //Get location in SharedPreferences
        String locationName = SettingsActivity.getUserLocation(this);

        //More about MAP queries here https://developers.google.com/maps/documentation/android-api/intents
        Uri geoUri = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",locationName).build();
        mapIntent.setData(geoUri);

        if(mapIntent.resolveActivity(getPackageManager()) != null){
            startActivity(mapIntent);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("on restore", "___instanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e("on save", "__outstate");
    }
}
