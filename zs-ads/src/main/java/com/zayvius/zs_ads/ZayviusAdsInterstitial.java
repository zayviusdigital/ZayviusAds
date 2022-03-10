package com.zayvius.zs_ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ZayviusAdsInterstitial {
    public static InterstitialAd mInterstitialAd;
    public static boolean loadingIklan=true;
    public static Integer hitung=0;
    public static   void ad_inter(Activity activity, String id){

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity,id, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                mInterstitialAd = null;
                                ad_inter(activity, id);
                            }


                            @Override
                            public void onAdShowedFullScreenContent() {

                            }
                        });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;

            }
        });
    }

    public static void loadInterstitial(Activity activity, String id, int interval_inter) {
        hitung++;
        if (loadingIklan){ ;
            ZayviusAdsInterstitial.ad_inter(activity, id);
            loadingIklan=false;
        }
        if (hitung%interval_inter==0){
            if (mInterstitialAd != null) {
                mInterstitialAd.show(activity);
                loadingIklan = true;
            }
        }
    }
}
