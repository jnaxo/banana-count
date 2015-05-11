package cl.zeek.nacho.bananacount;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by nacho on 11-05-15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        Preference random = getPreferenceManager().findPreference("pref_key_random");
        Preference vibrate = getPreferenceManager().findPreference("pref_key_sound_vibrate");
        Preference amount = getPreferenceManager().findPreference("pref_key_bananas_number");
        Preference default_settings = getPreferenceManager().findPreference("pref_key_default");
        Preference save_button =  getPreferenceManager().findPreference("pref_key_save_button");

        if(save_button != null){
            save_button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return true;
                }
            });
        }
        if(default_settings != null){
            default_settings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return false;
                }
            });
        }
    }
}
