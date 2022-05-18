package com.zayvius.zs_ads.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Map;

public class ZayviusAdsInitialize {

    /*Admob*/
    public static void Admob(Activity activity,boolean mediation ){
        if (ZayviusAdsOnOff.ad_admob){
            if (mediation){
                MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                        Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                        for (String adapterClass : statusMap.keySet()) {
                            AdapterStatus status = statusMap.get(adapterClass);
                            assert status != null;
                            Log.d("MyApp", String.format(
                                    "Adapter name: %s, Description: %s, Latency: %d",
                                    adapterClass, status.getDescription(), status.getLatency()));
                        }

                        // Start loading ads here...
                    }
                });
            }else {
                MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                    }
                });
            }
        }
    }

    /*Applovin Max*/
    public static void ApplovinMax(Activity activity,boolean mediation,boolean mediationdebugger){
        if (ZayviusAdsOnOff.ad_applovinmax){
            AppLovinSdk.getInstance( activity ).setMediationProvider( "max" );
            AppLovinSdk.initializeSdk( activity, new AppLovinSdk.SdkInitializationListener() {
                @Override
                public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
                {
                    // AppLovin SDK is initialized, start loading ads
                }
            } );
            if (mediation){
                AdSettings.setDataProcessingOptions( new String[] {} );
            }
            if (mediationdebugger){
                AppLovinSdk.getInstance( activity ).showMediationDebugger();
            }
        }
    }

    /*ApplovinZone*/
    public static void ApplovinZone(Activity activity){
        if (ZayviusAdsOnOff.ad_applovinzone) {
            AppLovinSdk.initializeSdk(activity);
        }
    }

    /*uUnity*/
    /*public static void Unity(Activity activity){
        if (ZayviusAdsOnOff.ad_unity) {
            UnityAds.initialize(activity, ZayviusAdsIDUnity.Id_Appx, ZayviusAdsIDUnity.Tes_Modex, new IUnityAdsInitializationListener() {
                @Override
                public void onInitializationComplete() {

                }

                @Override
                public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

                }
            });

        }
    }*/
}
