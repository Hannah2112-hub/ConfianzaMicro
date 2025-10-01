package com.example.confianzamicro.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.confianzamicro.R;
import com.example.confianzamicro.ui.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logo);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fade);

        fade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation a) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
            public void onAnimationStart(Animation a) {}
            public void onAnimationRepeat(Animation a) {}
        });
    }
}