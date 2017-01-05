package cg.stevendende.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            DetailFragment detailsFragment = new DetailFragment();
            detailsFragment.setArguments(data);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .commit();
        }

    }



}