package com.zayvius.zs_ads.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.applovin.adview.AppLovinAdView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.zayvius.zs_ads.R;

public class ZayviusAdsBanner {
    /*Admob*/
    public static void BannerAdmob(Activity activity, RelativeLayout relativeLayout) {
        if (ZayviusAdsOnOff.ad_admob) {
            AdView adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(ZayviusAdsIDAdmob.Bannerx);
            relativeLayout.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    // Code to be executed when an ad request fails.
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "applovinmax":
                            BannerApplovinMAx(activity,relativeLayout);
                            break;
                        case "unity":
                            //BannerUnity(activity,relativeLayout);
                            break;
                        case "applovinzone":
                            BannerApplovinZone(activity,relativeLayout);
                            break;
                    }
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });
        }
    }

    /*ApplovinMax*/
    @SuppressLint("ResourceAsColor")
    public static void BannerApplovinMAx(Activity activity, RelativeLayout relativeLayout){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            MaxAdView adView = new MaxAdView(ZayviusAdsIDApplovinMax.Bannerx, activity);
            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {

                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            BannerAdmob(activity,relativeLayout);
                            break;
                        case "unity":
                            //BannerUnity(activity,relativeLayout);
                            break;
                        case "applovinzone":
                            BannerApplovinZone(activity,relativeLayout);
                            break;
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                }
            });
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);
            adView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
            adView.setBackgroundColor(R.color.black);
            relativeLayout.addView(adView);
            adView.loadAd();
        }
    }

    /*ApplovinZone*/
    public static void BannerApplovinZone(Activity activity,RelativeLayout relativeLayout){
        if (ZayviusAdsOnOff.ad_applovinzone) {
            AppLovinAdView adView = new AppLovinAdView(AppLovinAdSize.BANNER, ZayviusAdsIDApplovinZone.Zone_Bannerx, activity);
            relativeLayout.addView(adView);
            adView.loadNextAd();
            adView.setAdLoadListener(new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {

                }

                @Override
                public void failedToReceiveAd(int errorCode) {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            BannerAdmob(activity, relativeLayout);
                            break;
                        case "applovinmax":
                            BannerApplovinMAx(activity, relativeLayout);
                            break;
                        case "unity":
                            //BannerUnity(activity, relativeLayout);
                            break;
                    }
                }
            });

        }
    }

    /*Unity*/
    /*public static void BannerUnity(Activity activity,RelativeLayout relativeLayout){
        if (ZayviusAdsOnOff.ad_unity) {
            BannerView bannerView = new BannerView(activity, ZayviusAdsIDUnity.Bannerx, new UnityBannerSize(320, 50));
            bannerView.load();
            relativeLayout.addView(bannerView);
            bannerView.setListener(new BannerView.Listener() {
                @Override
                public void onBannerLoaded(BannerView bannerAdView) {

                }

                @Override
                public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            BannerAdmob(activity, relativeLayout);
                            break;
                        case "applovinmax":
                            BannerApplovinMAx(activity, relativeLayout);
                            break;
                        case "applovinzone":
                            BannerApplovinZone(activity, relativeLayout);
                            break;
                    }
                }

                @Override
                public void onBannerClick(BannerView bannerAdView) {

                }

                @Override
                public void onBannerLeftApplication(BannerView bannerAdView) {

                }
            });

        }
    }*/
}


