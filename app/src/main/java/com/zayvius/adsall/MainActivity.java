package com.zayvius.adsall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.zayvius.zs_ads.ads.ZayviusAdsNative;
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
        next_config.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ZayviusAdsNative.Disable_Click_ONOFF){
            Toast.makeText(this, "disable klik on", Toast.LENGTH_SHORT).show();
        }

    }

}