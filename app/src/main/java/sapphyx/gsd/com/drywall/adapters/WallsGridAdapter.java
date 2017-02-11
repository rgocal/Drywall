package sapphyx.gsd.com.drywall.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.graphics.Palette;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.util.PaletteTransformation;

import static sapphyx.gsd.com.drywall.util.PaletteTransformation.PaletteCallback;


public class WallsGridAdapter extends BaseAdapter {

    public static final String NAME = "name";
    public static final String WALL = "wall";
    public static final String AUTHOR = "author";

    private final ArrayList<HashMap<String, String>> data;
    private final Context context;
    private final int numColumns;
    private boolean usePalette = true;

    public WallsGridAdapter(Context context, ArrayList<HashMap<String, String>> arraylist, int numColumns) {
        super();
        this.context = context;
        this.numColumns = numColumns;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        HashMap<String, String> jsondata = data.get(position);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int imageWidth = (width / numColumns);

        final WallsHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_wallpaper, parent, false);
            holder = new WallsHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (WallsHolder) convertView.getTag();

        }

        holder.name.setText(jsondata.get(NAME));
        holder.author.setText(jsondata.get(AUTHOR));
        final String wallurl = jsondata.get(WALL);
        holder.wall.startAnimation(anim);

        //noinspection SuspiciousNameCombination
        Picasso.with(context)
                .load(wallurl)
                .resize(imageWidth, imageWidth)
                .centerCrop()
                .noFade()
                .transform(PaletteTransformation.instance())
                .into(holder.wall,
                        new PaletteCallback(holder.wall) {
                            @Override
                            public void onSuccess(Palette palette) {
                                holder.progressBar.setVisibility(View.GONE);
                                if (usePalette) {
                                    if (palette != null) {
                                        Palette.Swatch wallSwatch = palette.getVibrantSwatch();
                                        if (wallSwatch != null) {
                                            holder.titleBg.setBackgroundColor(wallSwatch.getRgb());
                                            holder.titleBg.setAlpha(1);
                                            holder.name.setTextColor(wallSwatch.getTitleTextColor());
                                            holder.name.setAlpha(1);
                                            holder.author.setTextColor(wallSwatch.getTitleTextColor());
                                            holder.author.setAlpha(1);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError() {
                            }
                        });

        return convertView;
    }

    class WallsHolder {

        final ImageView wall;
        final TextView name, author;
        final ProgressBar progressBar;
        final LinearLayout titleBg;
        final MaterialRippleLayout content;

        WallsHolder(View v) {
            wall = (ImageView) v.findViewById(R.id.wall);
            name = (TextView) v.findViewById(R.id.name);
            author = (TextView) v.findViewById(R.id.author);
            progressBar = (ProgressBar) v.findViewById(R.id.progress);
            titleBg = (LinearLayout) v.findViewById(R.id.titlebg);
            content = (MaterialRippleLayout) v.findViewById(R.id.walls_ripple);
        }
    }
}
