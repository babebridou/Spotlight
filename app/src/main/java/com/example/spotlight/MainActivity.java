package com.example.spotlight;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wooplr.spotlight.SpotlightConfig;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.prefs.PreferencesManager;
import com.wooplr.spotlight.target.SmallCircleViewTarget;
import com.wooplr.spotlight.utils.SpotlightSequence;
import com.wooplr.spotlight.utils.Utils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    static {
//        AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
//    }

    private FloatingActionButton fab;
    private static final String INTRO_CARD = "fab_intro";
    private static final String INTRO_SWITCH = "switch_intro";
    private static final String INTRO_RESET = "reset_intro";
    private static final String INTRO_REPEAT = "repeat_intro";
    private static final String INTRO_CHANGE_POSITION = "change_position_intro";
    private static final String INTRO_SEQUENCE = "sequence_intro";
    private boolean isRevealEnabled = true;

    @BindView(R.id.switchAnimation)
    TextView switchAnimation;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.resetAndPlay)
    TextView resetAndPlay;
    @BindView(R.id.changePosAndPlay)
    TextView changePosAndPlay;
    @BindView(R.id.startSequence)
    TextView startSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Fab Clicked!", Toast.LENGTH_LONG).show();
            }
        });

        switchAnimation.setOnClickListener(this);
        reset.setOnClickListener(this);
        resetAndPlay.setOnClickListener(this);
        changePosAndPlay.setOnClickListener(this);
        startSequence.setOnClickListener(this);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showIntro(fab, INTRO_CARD);
            }
        }, 2400);
    }

    @Override
    public void onClick(View view) {
        PreferencesManager mPreferencesManager = new PreferencesManager(MainActivity.this);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        switch (view.getId()) {

            case R.id.switchAnimation:
                if (isRevealEnabled) {
                    switchAnimation.setText("Switch to Reveal");
                    isRevealEnabled = false;
                } else {
                    switchAnimation.setText("Switch to Fadein");
                    isRevealEnabled = true;
                }
                mPreferencesManager.resetAll();
                break;

            case R.id.reset:
                mPreferencesManager.resetAll();
                break;
            case R.id.resetAndPlay:
                mPreferencesManager.resetAll();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showIntro(fab, INTRO_CARD);
                    }
                }, 400);
                break;
            case R.id.changePosAndPlay:
                mPreferencesManager.resetAll();
                Random r = new Random();
                int right = r.nextInt((screenWidth - Utils.dpToPx(16)) - 16) + 16;
                int bottom = r.nextInt((screenHeight - Utils.dpToPx(16)) - 16) + 16;
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                params.setMargins(Utils.dpToPx(16), Utils.dpToPx(16), right, bottom);
                fab.setLayoutParams(params);
                break;
            case R.id.startSequence:
                mPreferencesManager.resetAll();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SpotlightConfig config = new SpotlightConfig();
                        config.setDismissOnBackpress(true);
                        config.setDismissOnTouch(true);
                        config.setFadingTextDuration(400);
                        config.setIntroAnimationDuration(400);
                        config.setRevealAnimationEnabled(true);
                        config.setHeadingTvColor(Color.parseColor("#82be00"));
                        config.setHeadingTvSize(32);
                        config.setLineAndArcColor(Color.parseColor("#82be00"));
                        config.setLineAnimationDuration(400);
                        config.setLineStroke(Utils.dpToPx(4));
                        config.setShowTargetArc(false);
                        config.setMaskColor(Color.parseColor("#bb000000"));
                        config.setPadding(20);
                        config.setPerformClick(false);
                        config.setSubHeadingTvColor(Color.parseColor("#f2feff"));
                        config.setSubHeadingTvSize(16);

                        SpotlightSequence.getInstance(MainActivity.this,config)
                                .addSpotlight(switchAnimation, SpotlightView.TargetMode.NormalRectangle, "Switch Animation", "Click to swtich the animation", INTRO_SWITCH)
                                .addSpotlight(reset, SpotlightView.TargetMode.SmallCenter, "Reset ", "Click here to reset preferences", INTRO_RESET)
                                .addSpotlight(resetAndPlay, SpotlightView.TargetMode.Normal, "Play Again", "Click here to play again", INTRO_REPEAT)
                                .addSpotlight(changePosAndPlay, "Change Position", "Click here to change position and replay", INTRO_CHANGE_POSITION)
                                .addSpotlight(startSequence, "Start sequence", "Well.. you just clicked here", INTRO_SEQUENCE)
                                .addSpotlight(fab,"Love", "Like the picture?\n" + "Let others know.", INTRO_CARD)
                                .startSequence();
                    }
                },400);
                break;
        }
    }

    private void showIntro(View view, String usageId) {


        Drawable testDrawable = this.getResources().getDrawable(android.R.drawable.ic_btn_speak_now);

        new SpotlightView.Builder(this)
                .introAnimationDuration(400)
                .enableRevealAnimation(isRevealEnabled)
                .performClick(true)
                .fadeinTextDuration(400)
                //.setTypeface(FontUtil.get(this, "RemachineScript_Personal_Use"))
                //.headingTvColor(Color.parseColor("#eb273f"))
                .headingTvColor(Color.parseColor("#82be00"))
                .headingTvSize(32)
                .headingTvText("Love")
                .subHeadingTvColor(Color.parseColor("#f2feff"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Like the picture?\nLet others know.")
                .maskColor(Color.parseColor("#bb000000"))
                .target(view, SpotlightView.TargetMode.Normal)
                .lineAnimDuration(400)
                //.lineAndArcColor(Color.parseColor("#eb273f"))
                .lineAndArcColor(Color.parseColor("#82be00"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(usageId) //UNIQUE ID
                //.subHeadingDrawable(testDrawable)
                .setCircleViewMode(SpotlightView.CircleViewMode.Rectangle)
                .show();
    }
}

