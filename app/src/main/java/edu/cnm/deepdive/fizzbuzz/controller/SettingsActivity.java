package edu.cnm.deepdive.fizzbuzz.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SeekBarPreference;
import edu.cnm.deepdive.fizzbuzz.R;

/**
 * Activity hosting {@link SettingsFragment}, in order to display and allow user changes to
 * configuration settings.
 */
public class SettingsActivity extends AppCompatActivity {

  /**
   * Loads an instance of {@link SettingsFragment} (if not already loaded) to enable user
   * interaction with configuration settings.
   *
   * @param savedInstanceState activity state saved prior to {@link #onDestroy()}.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.settings_fragment_container, new SettingsFragment())
          .commit();
    }
  }

  /**
   * Fragment displaying and allowing user changes to configuration settings.
   */
  public static class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Inflates and presents contents of preference resource. Attributes not supported (by the
     * minimum API targeted) for definition in the preference XML are set after loading, based on
     * other resource values.
     * 
     * @param savedInstanceState state of preference UI widgets prior to {@link #onDestroy()}
     * (currently ignored, since changes are written immediately to {@link
     * android.content.SharedPreferences}).
     *
     * @param rootKey base key prefix for this app's preferences.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.preferences, rootKey);
      PreferenceScreen screen = getPreferenceScreen();
      SeekBarPreference numDigits =
          (SeekBarPreference) screen.findPreference(getString(R.string.num_digits_key));
      numDigits.setMin(1);
      SeekBarPreference gameTime =
          (SeekBarPreference) screen.findPreference(getString(R.string.game_time_key));
      gameTime.setMin(getResources().getInteger(R.integer.game_time_min));
      int gameIncrement = getResources().getInteger(R.integer.game_time_increment);
      gameTime.setOnPreferenceChangeListener((preference, newValue) -> {
        int roundedValue = Math.round((Integer) newValue / (float) gameIncrement) * gameIncrement;
        gameTime.setValue(roundedValue);
        return false;
      });
    }

  }

}
