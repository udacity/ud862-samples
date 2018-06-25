/**
 * By Claudio "iClaude" Agostini.
 * This app uses the code explained in the video lessons of Udacity's course on Material
 * Design.
 */

package test.instructivemotion;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final int startScrollPos = getResources().getDimensionPixelSize(R.dimen.init_scroll_up_distance);
        Animator animator = ObjectAnimator.ofInt(scrollView, "scrollY", startScrollPos).setDuration(300);
        animator.start();
    }
}
