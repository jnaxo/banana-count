package cl.zeek.nacho.bananacount;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends Activity {

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
        //default options
        vibrate = true;
        random = true;
        bananas_amount = 9;

        play_button = (Button) findViewById(R.id.play_button);
        settings_button = (Button) findViewById(R.id.settings_button);

        play_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_POINTER_DOWN ) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

        settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
                    Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivityForResult(i, SETTINGS_RESULT);
                    Log.w("touchListener","foo");
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTINGS_RESULT)
        {
            Log.w("onActivityResult", "llamar funcion para rescater valores de preferencias");
            displayUserSettings();
        }

    }

    private void displayUserSettings(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        MenuActivity.setRandom(sharedPrefs.getBoolean("pref_key_random",false));
        MenuActivity.setVibrate(sharedPrefs.getBoolean("pref_key_sound_vibrate", false));
        Integer pref_bananas = sharedPrefs.getInt("pref_key_bananas_number",10);
        try {
            MenuActivity.setBananas_amount(pref_bananas);
        }catch (Exception e){

        }
    }

}
