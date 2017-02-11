package sapphyx.gsd.com.drywall.development;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

import sapphyx.gsd.com.drywall.R;

/**
 * Created by ry on 1/2/17.
 */
public class DevTwo extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static DevTwo newInstance(int sectionNumber) {
        DevTwo fragment = new DevTwo();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DevTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dev_two, container, false);
        setHasOptionsMenu(true);

        ImageView avatar_two = (ImageView) rootView.findViewById(R.id.avatar_two);
        ImageView banner_two = (ImageView) rootView.findViewById(R.id.banner_two);

        LinearLayout tim_twitter = (LinearLayout) rootView.findViewById(R.id.tim_twitter);
        LinearLayout tim_gplus = (LinearLayout) rootView.findViewById(R.id.tim_gplus);

        tim_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jahirfiquitiva.me/"));
                startActivity(intent);

            }
        });
        tim_gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/+JahirFiquitivaJDev"));
                startActivity(intent);

            }
        });

        Ion.with(avatar_two)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_alert)
                .animateLoad(R.anim.popup_enter)
                .animateIn(R.anim.popup_exit)
                .smartSize(true)
                .load("https://avatars0.githubusercontent.com/u/10360816?v=3&s=400");

        Ion.with(banner_two)
                .fitXY()
                .animateLoad(R.anim.popup_enter)
                .animateIn(R.anim.popup_exit)
                .smartSize(true)
                .load("https://lh3.googleusercontent.com/-LcHg3hPlxTY/WHpnMoV8TWI/AAAAAAAAV7E/3INwtUuqeOsjHciBULcmLgkmHS8xAXNDwCL0B/w256-h86-n-rw-no/JahirBannerV2.png");

        return rootView;
    }
}
