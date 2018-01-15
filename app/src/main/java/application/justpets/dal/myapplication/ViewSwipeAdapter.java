package application.justpets.dal.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Aniket on 08-11-2017.
 */

public class ViewSwipeAdapter extends PagerAdapter {

    private int[] img_group = {R.drawable.vs1,R.drawable.vs2,R.drawable.vs3,R.drawable.vs4,R.drawable.vs5};
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewSwipeAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pic_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView)pic_view.findViewById(R.id.imageview);
        imageView.setImageResource(img_group[position]);
        Log.d("s","Image added in imageview");
        container.addView(pic_view);
        return pic_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
