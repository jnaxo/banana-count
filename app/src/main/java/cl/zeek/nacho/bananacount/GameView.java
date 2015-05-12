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


    public GameView(Context c, int items, List<ImageView> bananas_list, boolean random_amount){

        context = c;
        Random random = new Random();
        boolean draw_banana;
        boolean empty_panel = true;
      //  int rows_bananas = items * items;// calculo innecesario
        total_bananas = 0;
        media_thumb_views_ids = new Integer[items];

        if(bananas_list != null){
            this.bananas_list = bananas_list;
        }else {
            this.bananas_list = new ArrayList<>();
        }

        double items_sqrt = Math.sqrt(items);
        int int_part= (int) items_sqrt;
        double double_part = items_sqrt - int_part;

        if (double_part > 0) {
            items_sqrt++;
            items = (int)items_sqrt;
        }
        for (int i = 0; i < items; i++){
            if (random_amount) {
                draw_banana = random.nextBoolean();
            }else if(int_part >0) {
                draw_banana = true;
                items_sqrt--;
            }
            else {
                draw_banana = false;
            }
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
