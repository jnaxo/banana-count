package cl.zeek.nacho.bananacount;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnTouchListener, MediaPlayer.OnPreparedListener{

    private GridView game_grid;
    private Resources resources;
    private TextView monkey_msg_text_view;
    private String banana_count;
    private Integer current_bananas, total_bananas, max_width_bananas;
    private Vibrator vibrator;
    private ImageView monkey_img;
    public boolean game_over, starting_game;
    private MediaPlayer bong_sound, bg_music;
    private boolean bananas_crashed[];

    String test = "fail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = "TEst!!!";
        this.max_width_bananas = 3; // by default
        this.resources = getResources();
        this.game_grid = (GridView) findViewById(R.id.game_grid_view);
        this.monkey_msg_text_view = (TextView) findViewById(R.id.bubble_text);
        this.monkey_img = (ImageView) findViewById(R.id.monkey_img);

        this.monkey_img.setOnTouchListener(this);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.bong_sound = MediaPlayer.create(this, R.raw.bong_sound);
        this.bg_music = MediaPlayer.create(this, R.raw.bg_music);
        this.bg_music.setVolume(100, 100);
        this.bg_music.setLooping(true);
        this.bg_music.setOnPreparedListener(this);
        try {
            this.bg_music.start();
        } catch (Exception e) {
            Log.e("onCreate music", "error: " + e.getMessage(), e);
        }
        this.starting_game = true;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);

        GridView game_grid = (GridView) findViewById(R.id.game_grid_view);
        ImageView monkey_img = (ImageView) findViewById(R.id.monkey_img);
        TextView monkey_text = (TextView) findViewById(R.id.bubble_text);


        game_grid.setNumColumns(this.game_grid.getNumColumns());
        game_grid.setOnItemClickListener(this.game_grid.getOnItemClickListener());
        monkey_img.setImageDrawable(this.monkey_img.getDrawable());
        monkey_img.setOnTouchListener(this);
        monkey_text.setText(this.monkey_msg_text_view.getText());

        this.monkey_img = monkey_img;
        this.monkey_msg_text_view = monkey_text;

        if(!starting_game) {
            List<ImageView> bananas_list = ((GameView) this.game_grid.getAdapter()).getBananas_list();
            GameView adapter;

            for (int i = 0; i < max_width_bananas * max_width_bananas; i++) {
                if (bananas_crashed[i]) {
                    Log.w("adapter at", i + " " + bananas_list.get(i).getTag());
                    bananas_list.get(i).setOnTouchListener(null);
                    bananas_list.get(i).setImageResource(R.drawable.empty);
                }
            }
            adapter = new GameView(this,max_width_bananas,bananas_list);
            game_grid.setAdapter(adapter);
        }
        this.game_grid = game_grid;
    }

    private void startGame(){
        this.current_bananas = 0;
        final GameView gameView = new GameView(this, max_width_bananas, null);
        this.addBanana();

        try {
            game_grid.removeAllViews();
        }catch (Exception e){
            Log.i("startGame","game grid is already empty");
        }
        //if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
          //  game_grid.setNumColumns(this.max_width_bananas);
        //} else{
        int items = max_width_bananas*max_width_bananas;
        bananas_crashed = new boolean[items];
        game_grid.setNumColumns(this.max_width_bananas);
        for (int i = 0; i < items; i++){
            bananas_crashed[i] = false;
        }
       // }

        total_bananas = gameView.getTotalBananas();
        game_grid.setAdapter(gameView);

        game_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view;
                String tag = imageView.getTag().toString();
                int type = Integer.parseInt(tag.substring(0, tag.lastIndexOf('-')));
                Log.e("tipo: ", type + " . b: " + R.drawable.banana);
                //int pos = Integer.parseInt(tag.substring(tag.lastIndexOf('-')+1));
                if (type == R.drawable.banana){
                    vibrator.vibrate(50);

                    try {
                        bong_sound.start();
                    } catch (Exception e) {
                        Log.e("Bong Sound otTouch", "error: " + e.getMessage(), e);
                    }
                    imageView.setImageResource(R.drawable.empty);
                    //bananas_crashed[pos] = true;
                    bananas_crashed[position] = true;
                    addBanana();
                    monkey_msg_text_view.setText(banana_count);
                    total_bananas--;
                    Log.i("total bananas", total_bananas + "");
                    if (total_bananas == 0) {
                        vibrator.vibrate(200);
                        game_over = true;
                        monkey_msg_text_view.setText("Press me to restart Game");
                        monkey_img.setImageResource(R.drawable.monkey_talking);
                    }
                }
                imageView.setOnClickListener(null);
            }
        });
        game_grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case 0x305:
                    case 0x405:
                        Log.v("touchlistener", "onTouch:ACTION_DOWN");
                        if (Build.VERSION.SDK_INT >= 5) {
                            int pointCount = event.getPointerCount();
                            for (int p = 0; p < pointCount; p++) {
                                int px = (int) event.getX(p);
                                int py = (int) event.getY(p);
                                clickItemView(px, py);
                            }
                        } else {
                            int px = (int) event.getX();
                            int py = (int) event.getY();
                            clickItemView(px, py);
                        }

                        break;
                }
                return true;
            }
        });

        this.game_over = false;
        this.monkey_msg_text_view.setText("Here we go!");
        this.monkey_img.setImageResource(R.drawable.monkey_talking2);
    }

    private void clickItemView(int px, int py) {
        int pv = game_grid.pointToPosition(px,py);
        long id = game_grid.pointToRowId(px,py);
        View lv = game_grid.getChildAt(pv);
        try{
            game_grid.performItemClick(lv, pv, id);
        }catch (Exception e){
            Log.w("PerformItemClick", "warning: "+e.getMessage(), e);
        }
    }

    private void addBanana(){
        this.banana_count = this.resources.getQuantityString(
                R.plurals.banana_name, current_bananas, current_bananas);
        this.current_bananas++;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(this.starting_game){
            Log.w("mainActivity onTouch", "Starting game!");
            this.startGame();
            this.starting_game = false;
        }
        else if (this.game_over) {
            this.startGame();
        }else{
            this.monkey_msg_text_view.setText("Touch bananas!");
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        try {
            this.bg_music.start();
        }catch (Exception e){
            Log.e("onPrepared media music", "error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.bg_music.stop();
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.bg_music.stop();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        this.bg_music.prepareAsync();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (this.bg_music != null) {
            this.bg_music.release();
            this.bg_music = null;
        }
        if(this.bong_sound != null){
            this.bong_sound.release();
            this.bong_sound = null;
        }
    }
}
