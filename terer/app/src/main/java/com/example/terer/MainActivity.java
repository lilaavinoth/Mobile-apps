package com.example.terer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        public static final String PREFERENCE_DELETE = "delete";
        public static final String PREFERENCE_FORMAT = "format";
        public static final String PREFERENCE_CALL = "call";
        public static final String PREFERENCE_OPTIMIZATION = "optimization";
        public static final String PREFERENCE_DETAILS_CONTACT = "_contact";
        public static final String PREFERENCE_DETAILS_CALL = "_call";
        public static final String PREFERENCE_SOURCE = "source";
        public static final String PREFERENCE_FILTER_IN = "filter_in";
        public static final String PREFERENCE_FILTER_OUT = "filter_out";
        public static final String PREFERENCE_DONE_NOTIFICATION = "done_notification";
        public static final String PREFERENCE_MIXERPATHS = "mixer_paths";

        public static final String CALL_OUT = "out";
        public static final String CALL_IN = "in";

        @Override
        public void onCreate() {
            super.onCreate();
            PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        }

        @Override
        public int getUserTheme() {
            return getTheme(this, R.style.RecThemeLight, R.style.RecThemeDark);
        }

        public static String getContact(Context context, Uri f) {
            final SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            String p = getFilePref(f) + PREFERENCE_DETAILS_CONTACT;
            return shared.getString(p, null);
        }

        public static void setContact(Context context, Uri f, String id) {
            final SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            String p = getFilePref(f) + PREFERENCE_DETAILS_CONTACT;
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(p, id);
            editor.commit();
        }

        public static String getCall(Context context, Uri f) {
            final SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            String p = getFilePref(f) + PREFERENCE_DETAILS_CALL;
            return shared.getString(p, null);
        }

        public static void setCall(Context context, Uri f, String id) {
            final SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            String p = getFilePref(f) + PREFERENCE_DETAILS_CALL;
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(p, id);
            editor.commit();
        }

    }
}