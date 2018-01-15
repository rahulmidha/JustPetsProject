package application.justpets.dal.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;

    Animation transition, transition1;
    ImageView logoImg;
    ImageView logoNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoImg = (ImageView) findViewById(R.id.logoImage);
        logoNm = (ImageView) findViewById(R.id.logoName);
        transition = AnimationUtils.loadAnimation(this,R.anim.transition);
        transition1 = AnimationUtils.loadAnimation(this,R.anim.transition1);
        logoImg.setAnimation(transition1);
        logoNm.setAnimation(transition1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this, SwipeActivity.class);
                startActivity(homeIntent);
                finish();
                overridePendingTransition(R.anim.transition,R.anim.fade_out);
            }
        },SPLASH_TIME_OUT);


    }
}
