package cl.zeek.nacho.bananacount;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by nacho on 11-05-15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        final CheckBoxPreference random = (CheckBoxPreference) getPreferenceManager()
                .findPreference("pref_key_random");
        final CheckBoxPreference vibrate = (CheckBoxPreference) getPreferenceManager()
                .findPreference("pref_key_sound_vibrate");
        final NumberPickerDialog amount = (NumberPickerDialog) getPreferenceManager()
                .findPreference("pref_key_bananas_number");
        Preference default_settings = getPreferenceManager().findPreference("pref_key_default");
        Preference save_button =  getPreferenceManager().findPreference("pref_key_save_button");
        Log.w("save btn", " save key: " + save_button.getKey());
        save_button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().finish();
                return true;
            }
        });
        default_settings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.w("defaul settings", " click default listener");
                random.setChecked(true);
                vibrate.setChecked(true);
                amount.setValue(10);
                return true;
            }
        });

    }
}
