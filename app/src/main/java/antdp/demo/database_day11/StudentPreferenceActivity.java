package antdp.demo.database_day11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import java.util.prefs.Preferences;

public class StudentPreferenceActivity extends PreferenceActivity
            implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.student_preference);
        SharedPreferences sharedPreferences =
                getSharedPreferences("antdp.demo.database_day11_preferences", MODE_PRIVATE);
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            processCategory(getPreferenceScreen().getPreference(i), sharedPreferences);
        }
    }

    private void processCategory(Preference p, SharedPreferences sharedPreferences){
        if(p instanceof PreferenceCategory){
            PreferenceCategory category = (PreferenceCategory) p;
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                processCategory(category.getPreference(i), sharedPreferences);
            }
        }else {
            updatePref(p, sharedPreferences);
        }
    }

    private void updatePref(Preference p, SharedPreferences sharedPreferences) {
        if(p instanceof EditTextPreference){
            EditTextPreference edt = (EditTextPreference) p;
            p.setSummary(edt.getText());
        }else if(p instanceof CheckBoxPreference){
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) p;
            String gender = "Female";
            if(checkBoxPreference.isChecked()){
                gender = "Male";
            }
            p.setSummary(gender);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePref(findPreference(key), sharedPreferences);
    }


}