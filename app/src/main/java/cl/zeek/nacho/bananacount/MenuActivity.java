package cl.zeek.nacho.bananacount;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends Activity implements View.OnTouchListener{

    private static boolean vibrate, random;
    private static Integer bananas_amount;
    private static final int SETTINGS_RESULT = 1;
    private Button play_button, settings_button;

    public static boolean isVibrate(){
        return vibrate;
    }

    public static void setVibrate(boolean option){
        vibrate = option;
    }

    public static boolean isRandom() {
        return random;
    }

    public static void setRandom(boolean random) {
        MenuActivity.random = random;
    }

    public static Integer getBananas_amount(){
        return bananas_amount;
    }

    public static void setBananas_amount(Integer amount){
        bananas_amount = amount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        loadSettings();

        play_button = (Button) findViewById(R.id.play_button);
        settings_button = (Button) findViewById(R.id.settings_button);

        play_button.setOnTouchListener(this);
        settings_button.setOnTouchListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTINGS_RESULT)
        {
            Log.w("onActivityResult", "llamar funcion para rescater valores de preferencias");
            loadSettings();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_menu);

        Button play = (Button)findViewById(R.id.play_button);
        Button settings = (Button)findViewById(R.id.settings_button);

        play.setOnTouchListener(this);
        settings.setOnTouchListener(this);
        play_button = play;
        settings_button = settings;
    }

    private void loadSettings(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        MenuActivity.setRandom(sharedPrefs.getBoolean("pref_key_random",true));
        MenuActivity.setVibrate(sharedPrefs.getBoolean("pref_key_sound_vibrate", true));
        Integer pref_bananas = sharedPrefs.getInt("pref_key_bananas_number",10);
        try {
            MenuActivity.setBananas_amount(pref_bananas);
        }catch (Exception e){

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_POINTER_DOWN ) {
            Intent i;
            Log.w("onTouch", v.getTag().toString());
            if(v.getTag().toString().equals("play")){
                Log.w("if","press play");
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            }else if (v.getTag().equals("settings")){
                Log.w("if","press settings");
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(i, SETTINGS_RESULT);
                return true;
            }
        }
        return false;
    }
}
