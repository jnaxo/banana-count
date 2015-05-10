package cl.zeek.nacho.bananacount;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends BaseAdapter {

    private Context context;
    private Integer[] media_thumb_views_ids;
    private int total_bananas;
    private List<ImageView> bananas_list;


    public GameView(Context c, int max_width_bananas, List<ImageView> bananas_list){

        context = c;
        Random random = new Random();
        boolean draw_banana;
        boolean empty_panel = true;
        int items = max_width_bananas * max_width_bananas;
        total_bananas = 0;
        media_thumb_views_ids = new Integer[items];

        if(bananas_list != null){
            this.bananas_list = bananas_list;
        }else {
            this.bananas_list = new ArrayList<>();
        }

        for (int i = 0; i < items; i++){
            draw_banana = random.nextBoolean();
            if(draw_banana) {
                media_thumb_views_ids[i] = R.drawable.banana;
                total_bananas++;
                empty_panel = false;
            }else {
                media_thumb_views_ids[i] = R.drawable.empty;
            }
        }
        if (empty_panel){
            media_thumb_views_ids[0] = R.drawable.banana;
        }
    }

    @Override
    public int getCount() {
        return media_thumb_views_ids.length;
    }

    @Override
    public Object getItem(int position) {
        if(null != bananas_list){
            try {
                return bananas_list.get(position);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
    }

    public List<ImageView> getBananas_list(){
        return this.bananas_list;
    }

    public void setItemAt(int position, ImageView imageView){
        bananas_list.set(position, imageView);
    }

    @Override
    public long getItemId(int position) {
        return media_thumb_views_ids[position];
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView banana_img;
        try{
            return bananas_list.get(position);
        }catch (Exception e) {

            if (convertView == null) {
                banana_img = new ImageView(context);
            } else {
                banana_img = (ImageView) convertView;
            }
            banana_img.setPadding(5, 5, 5, 5);
            banana_img.setScaleType(ImageView.ScaleType.CENTER);
            banana_img.setImageResource(media_thumb_views_ids[position]);
            banana_img.setTag(media_thumb_views_ids[position] + "-" + position);
            bananas_list.add(position, banana_img);
        }
        return banana_img;
    }

    public int getTotalBananas(){
        return total_bananas;
    }
}
