package sapphyx.gsd.com.drywall.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import java.util.List;

import sapphyx.gsd.com.drywall.R;

/**
 * Created by ry on 1/27/17.
 */

public class Wallpapers extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.papers);
        populateWallpaperTypes();
    }

    private void populateWallpaperTypes() {
        final Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> rList = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        final PreferenceScreen parent = getPreferenceScreen();
        parent.setOrderingAsAdded(false);
        for (ResolveInfo info : rList) {
            Preference pref = new Preference(getActivity());
            pref.setLayoutResource(R.layout.preference_wallpaper_type);
            Intent prefIntent = new Intent(intent);
            prefIntent.setComponent(new ComponentName(
                    info.activityInfo.packageName, info.activityInfo.name));
            pref.setIntent(prefIntent);
            CharSequence label = info.loadLabel(pm);
            if (label == null) label = info.activityInfo.packageName;
            pref.setTitle(label);
            pref.setIcon(info.loadIcon(pm));
            parent.addPreference(pref);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Wallpapers f = (Wallpapers) getFragmentManager()
                .findFragmentById(R.id.fragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    protected PackageManager getPackageManager() {
        return getActivity().getPackageManager();
    }
}