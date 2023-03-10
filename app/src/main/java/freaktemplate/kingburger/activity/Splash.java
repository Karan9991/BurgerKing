package freaktemplate.kingburger.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import freaktemplate.kingburger.R;
import freaktemplate.kingburger.utils.LanguageSelectore;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        LanguageSelectore.setLanguageIfAR( Splash.this );
        int SPLASH_DISPLAY_LENGTH = 3000;
        ImageView imgGif = findViewById(R.id.imgGif);
        Glide.with(Splash.this).load(R.drawable.ezgif).into(imgGif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, Home.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
