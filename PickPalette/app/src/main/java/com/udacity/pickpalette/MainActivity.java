/*
 * Copyright 2015 Udacity, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.pickpalette;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.fab)FloatingActionButton fab;
    SwatchAdapter swatchAdapter;
    @InjectView(R.id.grid_view)GridView gridView;
    @InjectView(R.id.tool_bar)Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbar.setTitle(getString(R.string.app_name));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void click(View view) {
        Snackbar.make(findViewById(R.id.fragment), "Clicked FAB.", Snackbar.LENGTH_LONG)
                //.setAction("Action", this)
                .show();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PickerFragment pickerFragment = new PickerFragment();
        pickerFragment.show(getFragmentManager(), "dialog");
        ft.commit();


    }

    public void createPalette(Uri imageUri) {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.with(this).load(imageUri).into(imageView);

        // Do this async on activity
        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    HashMap<String, Integer> swatches = processPalette(palette);
                    Object[] entries = swatches.entrySet().toArray();
                    swatchAdapter = new SwatchAdapter(getApplicationContext(), entries);

                    gridView.setAdapter(swatchAdapter);
                }
            });



        } catch (Exception ex) {
            Log.e("MainActivity", "error in creating palette");
        }
    }

    HashMap<String,Integer> processPalette (Palette p) {
        HashMap<String, Integer> map = new HashMap<>();

        if (p.getVibrantSwatch() != null)
            map.put("Vibrant", p.getVibrantSwatch().getRgb());
        if (p.getDarkVibrantSwatch() != null)
            map.put("DarkVibrant", p.getDarkVibrantSwatch().getRgb());
        if (p.getLightVibrantSwatch() != null)
            map.put("LightVibrant", p.getLightVibrantSwatch().getRgb());

        if (p.getMutedSwatch() != null)
            map.put("Muted", p.getMutedSwatch().getRgb());
        if (p.getDarkMutedSwatch() != null)
            map.put("DarkMuted", p.getDarkMutedSwatch().getRgb());
        if (p.getLightMutedSwatch() != null)
            map.put("LightMuted", p.getLightMutedSwatch().getRgb());

        return map;
    }
}
