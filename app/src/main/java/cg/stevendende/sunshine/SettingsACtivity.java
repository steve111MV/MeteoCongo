package cg.stevendende.sunshine;

/**
 * Created by STEVEN on 02/01/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * A {@link PreferenceActivity} that presents a set of application settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */

public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private static final String PREFERENCE_LOCATION = "cg.stevendende.sunshine.pref.location";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Add 'general' preferences, defined in the XML file
            addPreferencesFromResource(R.xml.preference_screen);

            // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
            // updated when the preference changes.
            bindPreferenceSummaryToValue(getPreferenceScreen().findPreference(getString(R.string.pref_location_key)));
            bindPreferenceSummaryToValue(getPreferenceScreen().findPreference(getString(R.string.pref_units_key)));
        }

        /**
         * Attaches a listener so the summary is always updated with the preference value.
         * Also fires the listener once, to initialize the summary (so it shows up before the value
         * is changed.)
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(this);

            // Trigger the listener immediately with the preference's
            // current value.
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }

            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }

            return true;
        }


    public static String getUserLocation(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Resources res = context.getResources();

        return pref.getString(res.getString(R.string.pref_location_key), res.getString(R.string.pref_location_defaultvalue));
    }

    public static String getPreferedUnits(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Resources res = context.getResources();

        return pref.getString(res.getString(R.string.pref_units_key), res.getString(R.string.pref_units_metric));
    }

}
