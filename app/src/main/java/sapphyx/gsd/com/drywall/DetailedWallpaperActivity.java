package sapphyx.gsd.com.drywall;

import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailedWallpaperActivity extends AppCompatActivity {

    public String wall;
    private String saveWallLocation, picName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        postponeEnterTransition();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detailed_wallpaper);

        Button set = (Button) findViewById(R.id.set_button);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetWallDialog();
            }
        });

        Button finish = (Button) findViewById(R.id.exit_button);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveWallLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + getResources().getString(R.string.walls_save_location);
        picName = getResources().getString(R.string.walls_prefix_name);

        ImageView image = (ImageView) findViewById(R.id.bigwall);
        wall = getIntent().getStringExtra("wall");
        Picasso.with(this)
                .load(wall)
                .into(image, new Callback.EmptyCallback() {
                            @Override
                            public void onSuccess() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    startPostponedEnterTransition();
                            }
                        }
                );
    }

    private final com.squareup.picasso.Target wallTarget = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        WallpaperManager wm = WallpaperManager.getInstance(DetailedWallpaperActivity.this);
                        wm.setBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            showNoPicDialog();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private final com.squareup.picasso.Target wallCropTarget = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageView wall = (ImageView) findViewById(R.id.bigwall);
                        Uri wallUri = getLocalBitmapUri(wall);
                        if (wallUri != null) {
                            Intent setWall = new Intent(Intent.ACTION_ATTACH_DATA);
                            setWall.setDataAndType(wallUri, "image/*");
                            setWall.putExtra("png", "image/*");
                            startActivityForResult(Intent.createChooser(setWall, getString(R.string.set_as)), 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            showNoPicDialog();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private String convertWallName(String link) {
        return (link
                .replaceAll("png", "")                   // Deletes png extension
                .replaceAll("jpg", "")                   // Deletes jpg extension
                .replaceAll("jpeg", "")                  // Deletes jpeg extension
                .replaceAll("bmp", "")                   // Deletes bmp extension
                .replaceAll("[^a-zA-Z0-9\\p{Z}]", "")    // Remove all special characters and symbols
                .replaceFirst("^[0-9]+(?!$)", "")        // Remove all leading numbers unless they're all numbers
                .replaceAll("\\p{Z}", "_"))              // Replace all kinds of spaces with underscores
        ;
    }

    public void showSetWallDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.set_wall_title)
                .content(R.string.set_wall_content)
                .positiveText(R.string.set_it)
                .neutralText(R.string.crop_wall)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        showSettingWallDialog(false);
                        Picasso.with(DetailedWallpaperActivity.this)
                                .load(wall)
                                .into(wallTarget);

                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        Picasso.with(DetailedWallpaperActivity.this)
                                .load(wall)
                                .into(wallCropTarget);
                    }
                }).show();
    }

    public void showSettingWallDialog(boolean indeterminate) {
        if (indeterminate) {
            new MaterialDialog.Builder(this)
                    .title(R.string.setting_wall_title)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .show();
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.setting_wall_title)
                    .content(R.string.please_wait)
                    .progress(false, 60)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            final MaterialDialog dialog = (MaterialDialog) dialogInterface;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (dialog.getCurrentProgress() != dialog.getMaxProgress()) {
                                        if (dialog.isCancelled())
                                            break;
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.incrementProgress(1);
                                            }
                                        });
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.setTitle(getString(R.string.done));
                                            dialog.setContent(getString(R.string.set_as_wall_done));
                                            dialog.setActionButton(DialogAction.NEGATIVE, R.string.close);
                                        }
                                    });
                                }
                            }).start();
                        }
                    }).show();
        }
    }

    private void showNoPicDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.error)
                .content(R.string.wall_error)
                .positiveText(android.R.string.ok)
                .show();
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp;
        if (drawable instanceof BitmapDrawable)
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        else
            return null;
        Uri bmpUri = null;
        try {
            File file = new File(saveWallLocation, picName + convertWallName(wall) + ".png");
            file.getParentFile().mkdirs();
            file.delete();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
