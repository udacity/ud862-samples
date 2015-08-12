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
package com.udacity.interpolationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnItemSelected.Callback.*;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.interpolator_spinner)Spinner interpolatorSpinner;
    @Bind(R.id.duration_spinner)Spinner duratorSpinner;
    @Bind(R.id.textView)TextView textView;

    private static final String PACKAGE = "android.view.animation.";
    private int duration;
    private Interpolator interpolator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

    @OnItemSelected({R.id.duration_spinner})
    void durationSelected(Spinner spinner, int position) {
        String durationString = (String) spinner.getAdapter().getItem(position);
        switch(durationString) {
            case "300ms":
                duration = 300;
                break;
            case "900ms":
                duration = 900;
                break;
            case "1500ms":
                duration = 1500;
                break;
            case "3000ms":
                duration = 3000;
                break;
            default:
                duration = 100;
                break;
        }
        // Kick off transition
        int item = interpolatorSpinner.getSelectedItemPosition();
        onItemSelected(interpolatorSpinner, position);
    }

    @OnItemSelected({R.id.interpolator_spinner})
    void onItemSelected(Spinner spinner, int position) {
        String interpolatorName = (String) spinner.getAdapter().getItem(position);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        textView.setTranslationY(metrics.heightPixels);

        try {
            interpolator = (Interpolator) Class.forName(PACKAGE + interpolatorName).newInstance();
            textView.animate().setInterpolator(interpolator)
                    .setDuration(duration)
                    .setStartDelay(500)
                    .translationYBy(-metrics.heightPixels)
                    .start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnItemSelected(value=R.id.interpolator_spinner, callback = OnItemSelected.Callback.NOTHING_SELECTED)
    void onNothingSelected() {

    }
}
