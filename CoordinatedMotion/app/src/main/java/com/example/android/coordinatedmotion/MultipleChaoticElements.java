package com.example.android.coordinatedmotion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.Random;

public class MultipleChaoticElements extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_elements);
        animateViewsIn();
    }

    private void animateViewsIn() {
        // setup random initial state
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        float maxWidthOffset = 2f * getResources().getDisplayMetrics().widthPixels;
        float maxHeightOffset = 2f * getResources().getDisplayMetrics().heightPixels;
        Interpolator interpolator =
                AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        Random random = new Random();
        int count = root.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.85f);
            float xOffset = random.nextFloat() * maxWidthOffset;
            if (random.nextBoolean()) {
                xOffset *= -1;
            }
            view.setTranslationX(xOffset);
            float yOffset = random.nextFloat() * maxHeightOffset;
            if (random.nextBoolean()) {
                yOffset *= -1;
            }
            view.setTranslationY(yOffset);

            // now animate them back into their natural position
            view.animate()
                    .translationY(0f)
                    .translationX(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000)
                    .start();
        }
    }

    public void containerClick(View view) {
        animateViewsIn();
    }
}
