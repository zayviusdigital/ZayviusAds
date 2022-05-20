package com.zayvius.zs_ads.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.zayvius.zs_ads.R;

public class ZayviusAdsNative {

    /*ApplovinMax*/
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd loadedNativeAd;
    public static MaxNativeAdLoader nativeAdLoadersmall;
    public static MaxAd loadedNativeAdsmall;

    /*Admob*/
    public static NativeAd nativeads;
    public static NativeAd nativeadssmall;

    public static void NativeAds(Activity activity, FrameLayout frameLayout, boolean size_small){
        switch (ZayviusAdsMain.main_ad) {
            case "admob":
                if (size_small){
                    NativeAdmobSmall(activity,frameLayout);
                }else {
                    NativeAdmob(activity,frameLayout);
                }
                break;
            case "applovinmax":
                if (size_small){
                    NativeApplovinMaxManualSmall(activity,frameLayout);
                }else {
                    NativeApplovinMaxManual(activity,frameLayout);
                }
                break;
        }
    }
    /*Admob*/
    public static void NativeAdmob(Activity activity,FrameLayout frameLayout){
        if (ZayviusAdsOnOff.ad_admob) {
            AdLoader.Builder builder = new AdLoader.Builder(activity, ZayviusAdsIDAdmob.Nativex);
            // OnLoadedListener implementation.
            builder.forNativeAd(nativeAd -> {
                if (nativeads != null) {
                    nativeads.destroy();
                }
                nativeads = nativeAd;
                @SuppressLint("InflateParams") NativeAdView adView =
                        (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);
                NativeAdsConfigAdmob.populateNativeAdView(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            });

            VideoOptions videoOptions = new VideoOptions.Builder().build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    if ("applovinmax".equals(ZayviusAdsBackup.backup_ad)) {
                        NativeApplovinMaxManual(activity,frameLayout);
                    }
                }
            }).build();
            AdRequest adRequest = new AdRequest.Builder().build();
            adLoader.loadAd(adRequest);
        }
    }

    /*Admob Small*/
    public static void NativeAdmobSmall(Activity activity,FrameLayout frameLayout){
        if (ZayviusAdsOnOff.ad_admob) {
            AdLoader.Builder builder = new AdLoader.Builder(activity, ZayviusAdsIDAdmob.Nativex);
            // OnLoadedListener implementation.
            builder.forNativeAd(nativeAd -> {
                if (nativeadssmall != null) {
                    nativeadssmall.destroy();
                }
                nativeadssmall = nativeAd;
                @SuppressLint("InflateParams") NativeAdView adView =
                        (NativeAdView) activity.getLayoutInflater().inflate(R.layout.nativeadmob_small, null);
                NativeAdsConfigAdmobSmall.populateNativeAdView(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            });

            VideoOptions videoOptions = new VideoOptions.Builder().build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    if ("applovinmax".equals(ZayviusAdsBackup.backup_ad)) {
                        NativeApplovinMaxManualSmall(activity,frameLayout);
                    }
                }
            }).build();
            AdRequest adRequest = new AdRequest.Builder().build();
            adLoader.loadAd(adRequest);
        }
    }


    /*ApplovinMax*/
    public static void NativeApplovinMaxManual(Activity activity,FrameLayout frameLayout){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            nativeAdLoader = new MaxNativeAdLoader(ZayviusAdsIDApplovinMax.Nativex, activity);
            nativeAdLoader.setRevenueListener(new MaxAdRevenueListener() {
                @Override
                public void onAdRevenuePaid(MaxAd ad) {

                }
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                    super.onNativeAdLoaded(maxNativeAdView, maxAd);
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    if (loadedNativeAd != null) {
                        nativeAdLoader.destroy(loadedNativeAd);
                    }

                    // Save ad for cleanup.
                    loadedNativeAd = maxAd;
                    frameLayout.removeAllViews();
                    frameLayout.addView(maxNativeAdView);
                }
                @Override
                public void onNativeAdLoadFailed(String s, MaxError maxError) {
                    if ("admob".equals(ZayviusAdsBackup.backup_ad)) {
                        NativeAdmob(activity,frameLayout);
                    }
                }
            });
            nativeAdLoader.loadAd(createNativeAdView(activity));
        }
    }

    /*ApplovinMax Small*/
    public static void NativeApplovinMaxManualSmall(Activity activity,FrameLayout frameLayout){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            nativeAdLoadersmall = new MaxNativeAdLoader(ZayviusAdsIDApplovinMax.Nativex, activity);
            nativeAdLoadersmall.setRevenueListener(new MaxAdRevenueListener() {
                @Override
                public void onAdRevenuePaid(MaxAd ad) {

                }
            });
            nativeAdLoadersmall.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                    super.onNativeAdLoaded(maxNativeAdView, maxAd);
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    if (loadedNativeAdsmall != null) {
                        nativeAdLoadersmall.destroy(loadedNativeAdsmall);
                    }

                    // Save ad for cleanup.
                    loadedNativeAdsmall = maxAd;
                    frameLayout.removeAllViews();
                    frameLayout.addView(maxNativeAdView);
                }
                @Override
                public void onNativeAdLoadFailed(String s, MaxError maxError) {
                    if ("admob".equals(ZayviusAdsBackup.backup_ad)) {
                        NativeAdmobSmall(activity,frameLayout);
                    }
                }
            });
            nativeAdLoadersmall.loadAd(createNativeAdViewsmall(activity));
        }
    }

    public static MaxNativeAdView createNativeAdView(Activity context) {
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(R.layout.native_ap)
                .setTitleTextViewId( R.id.title_text_view )
                .setBodyTextViewId( R.id.body_text_view )
                .setAdvertiserTextViewId( R.id.advertiser_textView )
                .setIconImageViewId( R.id.icon_image_view )
                .setMediaContentViewGroupId( R.id.media_view_container )
                .setOptionsContentViewGroupId( R.id.ad_options_view )
                .setCallToActionButtonId( R.id.cta_button )
                .build();

        return new MaxNativeAdView( binder,context );
    }
    public static MaxNativeAdView createNativeAdViewsmall(Activity context) {
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(R.layout.native_ap_small)
                .setTitleTextViewId( R.id.title_text_view )
                .setBodyTextViewId( R.id.body_text_view )
                //.setAdvertiserTextViewId( R.id.advertiser_textView )
                .setIconImageViewId( R.id.icon_image_view )
                //.setMediaContentViewGroupId( R.id.media_view_container )
                //.setOptionsContentViewGroupId( R.id.ad_options_view )
                .setCallToActionButtonId( R.id.cta_button )
                .build();

        return new MaxNativeAdView( binder,context );
    }
}
