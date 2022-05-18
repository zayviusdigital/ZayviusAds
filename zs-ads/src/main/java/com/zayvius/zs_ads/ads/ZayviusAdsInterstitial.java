package com.zayvius.zs_ads.ads;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.util.concurrent.TimeUnit;

public class ZayviusAdsInterstitial {
    /*Admob*/
    public static InterstitialAd mInterstitialAd;
    public static boolean loadingIklan=true;
    public static Integer hitung=0;

    /*ApplovinMax*/
    public static MaxInterstitialAd interstitialAd;
    public static int retryAttempt;
    public static boolean loadingIklanapmax=true;
    public static Integer hitungapmax=0;

    /*ApplovinZone*/
    public static AppLovinAd currentAd;
    public static AppLovinInterstitialAdDialog interstitialAdZone;
    public static boolean loadingIklanapzone=true;
    public static Integer hitungapzone=0;

    /*Unity*/
    public static boolean loadingIklanunity=true;
    public static Integer hitungunity=0;

    public static void admob(Activity activity){
        if (ZayviusAdsOnOff.ad_admob) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity, ZayviusAdsIDAdmob.Interstitialx, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    interstitialAd.setFullScreenContentCallback(
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    mInterstitialAd = null;
                                    admob(activity);
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
                    admob(activity);

                }
            });
        }
    }

    public static void AdmobloadInterstitial(Activity activity, int interval_inter) {
        if (ZayviusAdsOnOff.ad_admob) {
            hitung++;
            if (loadingIklan) {
                admob(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "applovinmax":
                        applovinmax(activity);
                        break;
                    case "unity":
                        unity();
                        break;
                    case "applovinzone":
                        applovinzone(activity);
                        break;
                }
                loadingIklan = false;
            }
            if (hitung % interval_inter == 0) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(activity);
                }else {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "applovinmax":
                            show_applovinmax();
                            break;
                        case "unity":
                            UnityAds.show(activity, ZayviusAdsIDUnity.Interstitialx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                                @Override
                                public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {

                                }

                                @Override
                                public void onUnityAdsShowStart(String s) {

                                }

                                @Override
                                public void onUnityAdsShowClick(String s) {

                                }

                                @Override
                                public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {

                                }
                            });
                            break;
                        case "applovinzone":
                            show_applovinzone();
                            break;
                    }
                }
                loadingIklan = true;
            }
        }
    }

    public static void show_admob(Activity activity){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        }
    }

    /*ApplovinMax*/
    public static void applovinmax(Activity activity){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            interstitialAd = new MaxInterstitialAd(ZayviusAdsIDApplovinMax.Interstitialx, activity);
            interstitialAd.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {
                    retryAttempt = 0;
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    interstitialAd.loadAd();
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    retryAttempt++;
                    long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            interstitialAd.loadAd();
                        }
                    }, delayMillis);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    interstitialAd.loadAd();
                }
            });

            // Load the first ad
            interstitialAd.loadAd();

        }
    }

    public static void ApplovinMaxloadInterstitial(Activity activity , int interval_inter) {
        if (ZayviusAdsOnOff.ad_applovinmax) {
            hitungapmax++;
            if (loadingIklanapmax) {
                applovinmax(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "admob":
                        admob(activity);
                        break;
                    case "unity":
                        unity();
                        break;
                    case "applovinzone":
                        applovinzone(activity);
                        break;
                }
                loadingIklanapmax = false;
            }
            if (hitungapmax % interval_inter == 0) {
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }else {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(activity);
                            }
                            break;
                        case "unity":
                            UnityAds.show(activity, ZayviusAdsIDUnity.Interstitialx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                                @Override
                                public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {
                                }

                                @Override
                                public void onUnityAdsShowStart(String s) {

                                }

                                @Override
                                public void onUnityAdsShowClick(String s) {

                                }

                                @Override
                                public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {

                                }
                            });
                            break;

                        case "applovinzone":
                            show_applovinzone();
                            break;
                    }
                }
                loadingIklanapmax = true;
            }

        }
    }

    public static void show_applovinmax(){
        if (interstitialAd.isReady()) {
            interstitialAd.showAd();
        }
    }

    /*ApplovinZone*/
    public static void applovinzone(Activity activity){
        if (ZayviusAdsOnOff.ad_applovinzone) {
            interstitialAdZone = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
            AppLovinSdk.getInstance(activity).getAdService().loadNextAdForZoneId(ZayviusAdsIDApplovinZone.Zone_Interstitialx, new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {
                    currentAd = ad;
                }

                @Override
                public void failedToReceiveAd(int errorCode) {

                }
            });

        }
    }

    public static void ApplovinZoneloadInterstitial(Activity activity , int interval_inter) {
        if (ZayviusAdsOnOff.ad_applovinzone) {
            hitungapzone++;
            if (loadingIklanapzone) {
                applovinzone(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "admob":
                        admob(activity);
                        break;
                    case "unity":
                        unity();
                        break;
                    case "applovinmax":
                        applovinmax(activity);
                        break;
                }
                loadingIklanapzone = false;
            }
            if (hitungapzone % interval_inter == 0) {
                if ( currentAd != null ) {
                    interstitialAdZone.showAndRender( currentAd );
                }else {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(activity);
                            }
                            break;
                        case "unity":
                            UnityAds.show(activity, ZayviusAdsIDUnity.Interstitialx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                                @Override
                                public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {
                                }

                                @Override
                                public void onUnityAdsShowStart(String s) {

                                }

                                @Override
                                public void onUnityAdsShowClick(String s) {

                                }

                                @Override
                                public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {

                                }
                            });
                            break;
                        case "applovinmax":
                            show_applovinmax();
                            break;
                    }
                }
                loadingIklanapzone = true;
            }

        }
    }

    public static void show_applovinzone(){
        if ( currentAd != null ) {
            interstitialAdZone.showAndRender( currentAd );
        }
    }

    /*Unity*/
    public static void unity(){
        if (ZayviusAdsOnOff.ad_unity) {
            UnityAds.load(ZayviusAdsIDUnity.Interstitialx, new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String s) {

                }

                @Override
                public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

                }
            });
        }
    }

    public static void UnityloadInterstitial(Activity activity , int interval_inter) {
        if (ZayviusAdsOnOff.ad_unity) {
            hitungunity++;
            if (loadingIklanunity) {
                unity();
                switch (ZayviusAdsBackup.backup_ad) {
                    case "admob":
                        admob(activity);
                        break;
                    case "applovinmax":
                        applovinmax(activity);
                        break;
                    case "applovinzone":
                        applovinzone(activity);
                        break;
                }
                loadingIklanunity = false;
            }
            if (hitungunity % interval_inter == 0) {
                UnityAds.show(activity, ZayviusAdsIDUnity.Interstitialx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {
                        switch (ZayviusAdsBackup.backup_ad) {
                            case "admob":
                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(activity);
                                }
                                break;
                            case "applovinmax":
                                show_applovinmax();
                                break;
                            case "applovinzone":
                                show_applovinzone();
                                break;
                        }
                    }

                    @Override
                    public void onUnityAdsShowStart(String s) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String s) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {

                    }
                });
                loadingIklanunity = true;

            }

        }
    }


}
