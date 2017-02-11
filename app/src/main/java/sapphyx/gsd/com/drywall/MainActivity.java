package sapphyx.gsd.com.drywall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.florent37.awesomebar.AwesomeBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import de.psdev.licensesdialog.LicensesDialog;
import sapphyx.gsd.com.drywall.development.Body;
import sapphyx.gsd.com.drywall.fragments.CategoryFive;
import sapphyx.gsd.com.drywall.fragments.CategoryFour;
import sapphyx.gsd.com.drywall.fragments.CategoryOne;
import sapphyx.gsd.com.drywall.fragments.CategorySix;
import sapphyx.gsd.com.drywall.fragments.CategoryThree;
import sapphyx.gsd.com.drywall.fragments.CategoryTwo;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Drawer result = null;

    String email = "rgocal09@gmail.com";
    String subject = "Help & Feedback Drywall";
    String body = "";
    String chooserTitle = "Contact via";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AwesomeBar mToolbar = (AwesomeBar) findViewById(R.id.toolbar);

        mToolbar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
            }
        });

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.dark_grey));

        result = new DrawerBuilder(this)
                .withShowDrawerOnFirstLaunch(true)
                .withSelectedItem(-1)
                .withDrawerGravity(Gravity.START)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerLayout(R.layout.material_drawer_fits_not)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Wallpaper Picker").withIcon(R.drawable.ic_photo).withIdentifier(0),
                        new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings).withIdentifier(1),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.ic_timeline).withIdentifier(2),
                        new PrimaryDrawerItem().withName("License").withIcon(R.drawable.ic_alert).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Rate Application").withIcon(R.drawable.ic_favorite).withIdentifier(4),
                        new PrimaryDrawerItem().withName("Help & Feedback").withIcon(R.drawable.ic_supervisor).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long id = drawerItem.getIdentifier();
                        if (id == 1) {
                            Intent intent = new Intent(getBaseContext(), DrySettings.class);
                            startActivity(intent);

                        } else if (id == 2) {
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title("About")
                                    .content("Welcome to Drywall! We hope you enjoy your experience with this application and help us make it better by providing positive feedback at Google Play.")
                                    .positiveText("Developers")
                                    .negativeText("Github")
                                    .neutralText("Version")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            // TODO
                                            Intent intent = new Intent(getBaseContext(), Body.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            // TODO
                                            Toast.makeText(MainActivity.this, "1.00.00",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            // TODO
                                            Toast.makeText(MainActivity.this, "Coming Soon!",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .show();

                        } else if (id == 3) {
                            new LicensesDialog.Builder(MainActivity.this)
                                    .setNotices(R.raw.licenses)
                                    .build()
                                    .show();

                        } else if (id == 4) {
                            Toast.makeText(MainActivity.this, "Thankyou for using Drywall!",
                                    Toast.LENGTH_LONG).show();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=sapphyx.gsd.com.drywall"));
                            startActivity(browserIntent);

                        } else if (id == 5) {
                            sendEmail();

                        } else if (id == 0) {
                            Intent intent = new Intent(getBaseContext(), PickerActivity.class);
                            startActivity(intent);
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        spinner= (Spinner) findViewById(R.id.spinner_toolbar);
        ArrayAdapter<CharSequence> mArrayAdapter =  ArrayAdapter.createFromResource(this, R.array.items, R.layout.layout_drop_title);
        mArrayAdapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinner.setAdapter(mArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryOne.newInstance()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryTwo.newInstance()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryThree.newInstance()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryFour.newInstance()).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryFive.newInstance()).commit();
                        break;
                    case 5:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategorySix.newInstance()).commit();
                        break;

                    default:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryOne.newInstance()).commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sendEmail() {
        ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo(email)
                .setSubject(subject)
                .setText(body)
                //.setHtmlText(body) //If you are using HTML in your body text
                .setChooserTitle(chooserTitle)
                .startChooser();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
