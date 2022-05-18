package com.zayvius.adsall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zayvius.zs_ads.settings.Config;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout banner = findViewById(R.id.Banner);
        FrameLayout native_ads = findViewById(R.id.native_ads);
        Button inter = findViewById(R.id.bt_in);
        Config.AppInReview(this);
        /*Ads*/

        /* Ads End*/
        Button next_config = findViewById(R.id.bt_next);
        next_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

}