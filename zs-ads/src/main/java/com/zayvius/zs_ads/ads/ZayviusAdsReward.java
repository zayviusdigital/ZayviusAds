package com.zayvius.zs_ads.ads;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.concurrent.TimeUnit;

public class ZayviusAdsReward {

    /*Admob*/
    private static RewardedAd mRewardedAd;
    private static boolean loadingIklan=true;
    private static Integer hitung=0;

    /*ApplovinMax*/
    private static boolean loadingIklanapmax=true;
    private static Integer hitungapmax=0;
    private static MaxRewardedAd rewardedAd;
    private static int           retryAttempt;

    /*ApplovinZone*/
    private static boolean loadingIklanapzone=true;
    private static Integer hitungapzone=0;
    private static AppLovinIncentivizedInterstitial incentivizedInterstitial;

    /*Unity*/
    private static boolean loadingIklanunity=true;
    private static Integer hitungunity=0;

    /*Main Ads*/
    public static void RewardedAds(Activity activity, int interval_reward){
        switch (ZayviusAdsMain.main_ad) {
            case "admob":
                RewardedAdmob(activity,interval_reward);
                break;
            case "applovinmax":
                if (!ZayviusAdsIDApplovinMax.Rewardedx.equals("")) {
                    RewardedApplovinMax(activity, interval_reward);
                }
                break;
            case "applovinzone":
               RewardedApplovinZone(activity,interval_reward);
                break;
        }
    }

    private static void admob(Activity activity){
        if (ZayviusAdsOnOff.ad_admob) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(activity, ZayviusAdsIDAdmob.Rewardedx,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            mRewardedAd = null;
                            admob(activity);
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;

                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when ad is shown.

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    // Called when ad fails to show.

                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.

                                    mRewardedAd = null;
                                    admob(activity);
                                }
                            });

                        }
                    });

        }
    }


    private static void RewardedAdmob(Activity activity, int interval_reward) {
        if (ZayviusAdsOnOff.ad_admob) {
            hitung++;
            if (loadingIklan) {
                admob(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "applovinmax":
                        applovinmax(activity);
                        break;
                    case "unity":
                        //unity();
                        break;
                    case "applovinzone":
                        applovinzone(activity);
                        break;
                }
                loadingIklan = false;
            }
            if (hitung % interval_reward == 0) {
                if (mRewardedAd != null) {
                    mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                }else {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "applovinmax":
                            if (rewardedAd.isReady()) {
                                rewardedAd.showAd();
                            }
                            break;
                        case "unity":
                            /*UnityAds.show(activity, ZayviusAdsIDUnity.Rewardedx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
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
                            });*/
                            break;
                        case "applovinzone":
                            if(incentivizedInterstitial.isAdReadyToDisplay()){
                                incentivizedInterstitial.show(activity);
                            }
                            break;
                    }
                }
                loadingIklan = true;
            }
        }
    }

    /*ApplovinMax*/
    private static void applovinmax( Activity activity){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            rewardedAd = MaxRewardedAd.getInstance(ZayviusAdsIDApplovinMax.Rewardedx, activity);
            rewardedAd.setListener(new MaxRewardedAdListener() {
                @Override
                public void onRewardedVideoStarted(MaxAd ad) {

                }

                @Override
                public void onRewardedVideoCompleted(MaxAd ad) {

                }

                @Override
                public void onUserRewarded(MaxAd ad, MaxReward reward) {

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
                    rewardedAd.loadAd();
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
                            rewardedAd.loadAd();
                        }
                    }, delayMillis);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    rewardedAd.loadAd();
                }
            });

            rewardedAd.loadAd();

        }
    }

    private static void RewardedApplovinMax(Activity activity, int interval_reward) {
        if (ZayviusAdsOnOff.ad_applovinmax) {
            hitungapmax++;
            if (loadingIklanapmax) {
                applovinmax(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "admob":
                        admob(activity);
                        break;
                    case "unity":
                        //unity();
                        break;
                    case "applovinzone":
                        applovinzone(activity);
                        break;
                }
                loadingIklanapmax = false;
            }
            if (hitungapmax % interval_reward == 0) {
                if (rewardedAd.isReady()) {
                    rewardedAd.showAd();
                }else {
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            if (mRewardedAd != null) {
                                mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        // Handle the reward.
                                        int rewardAmount = rewardItem.getAmount();
                                        String rewardType = rewardItem.getType();
                                    }
                                });
                            }

                            break;
                        case "unity":
                            /*UnityAds.show(activity, ZayviusAdsIDUnity.Rewardedx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
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
                            });*/
                            break;
                        case "applovinzone":
                            if(incentivizedInterstitial.isAdReadyToDisplay()){
                                incentivizedInterstitial.show(activity);
                            }
                            break;
                    }
                }
                loadingIklanapmax = true;
            }
        }
    }

    /*ApplovinZone*/
    private static void applovinzone(Activity activity){
        if (ZayviusAdsOnOff.ad_applovinzone) {
            incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(ZayviusAdsIDApplovinZone.Zone_Rewardedx, AppLovinSdk.getInstance(activity));
            incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {

                }

                @Override
                public void failedToReceiveAd(int errorCode) {

                }
            });
        }
    }

    private static void RewardedApplovinZone(Activity activity, int interval_reward) {
        if (ZayviusAdsOnOff.ad_applovinzone) {
            hitungapzone++;
            if (loadingIklanapzone) {
                applovinzone(activity);
                switch (ZayviusAdsBackup.backup_ad) {
                    case "admob":
                        admob(activity);
                        break;
                    case "applovinmax":
                        applovinmax(activity);
                        break;
                    case "unity":
                        //unity();
                        break;
                }
                loadingIklanapzone = false;
            }
            if (hitungapzone % interval_reward == 0) {
                if(incentivizedInterstitial.isAdReadyToDisplay()){
                    incentivizedInterstitial.show(activity);
                }else{
                    switch (ZayviusAdsBackup.backup_ad) {
                        case "admob":
                            if (mRewardedAd != null) {
                                mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        int rewardAmount = rewardItem.getAmount();
                                        String rewardType = rewardItem.getType();
                                    }
                                });
                            }

                            break;
                        case "applovinmax":
                            if (rewardedAd.isReady()) {
                                rewardedAd.showAd();
                            }
                            break;
                        case "unity":
                           /* UnityAds.show(activity, ZayviusAdsIDUnity.Rewardedx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
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
                            });*/
                            break;
                    }
                }
                loadingIklanapzone = true;
            }
        }
    }

    /*Unity*/
   /* public static void unity(){
        if (ZayviusAdsOnOff.ad_unity) {
            UnityAds.load(ZayviusAdsIDUnity.Rewardedx, new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String s) {

                }

                @Override
                public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

                }
            });
        }
    }

    public static void RewardedUnity(Activity activity, int interval_reward) {
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
            if (hitungunity % interval_reward == 0) {
                UnityAds.show(activity, ZayviusAdsIDUnity.Rewardedx, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {
                        switch (ZayviusAdsBackup.backup_ad) {
                            case "admob":
                                if (mRewardedAd != null) {
                                    mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                                        @Override
                                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                            // Handle the reward.
                                            int rewardAmount = rewardItem.getAmount();
                                            String rewardType = rewardItem.getType();
                                        }
                                    });
                                }

                                break;
                            case "applovinmax":
                                if (rewardedAd.isReady()) {
                                    rewardedAd.showAd();
                                }
                                break;
                            case "applovinzone":
                                if(incentivizedInterstitial.isAdReadyToDisplay()){
                                    incentivizedInterstitial.show(activity);
                                }
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
    }*/
}
