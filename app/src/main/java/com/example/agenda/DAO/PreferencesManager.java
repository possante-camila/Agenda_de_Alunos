package com.example.agenda.DAO;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;


public class PreferencesManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_LOGGED = "logged";


    private SharedPreferences sharedPreferences;

    public void exit(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public boolean getLogged(){
        return sharedPreferences.getBoolean(KEY_LOGGED, false);
    }

    public void setLogged(boolean logged){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED, logged);
        editor.apply();
    }

}
