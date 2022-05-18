package com.zayvius.zs_ads.ads;

import android.app.Activity;

import com.applovin.sdk.AppLovinPrivacySettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.unity3d.ads.metadata.MetaData;

public class ZayviusAdsGDPR {
    public static ConsentInformation consentInformation;
    public static ConsentForm consentForm;

    /*Admob*/
    public static void Admob(Activity activity){
        if (ZayviusAdsOnOff.ad_admob) {
            ConsentRequestParameters params = new ConsentRequestParameters
                    .Builder()
                    .setTagForUnderAgeOfConsent(false)
                    .build();

            consentInformation = UserMessagingPlatform.getConsentInformation(activity);
            consentInformation.requestConsentInfoUpdate(
                    activity,
                    params,
                    () -> {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm(activity);
                        }
                    },
                    formError -> {
                        // Handle the error.
                    });
        }
    }

    public static void loadForm(Activity activity){
        UserMessagingPlatform.loadConsentForm(
                activity,
                consentForm -> {
                    if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                activity,
                                formError -> {
                                    // Handle dismissal by reloading form.
                                    loadForm(activity);
                                });

                    }

                },
                formError -> {
                    /// Handle Error.
                }
        );
    }

    /*ApplovinMax*/
    public static void ApplovinMax(Activity activity, boolean children ){
        if (ZayviusAdsOnOff.ad_applovinmax) {
            AppLovinPrivacySettings.setHasUserConsent(true, activity);
            AppLovinPrivacySettings.setIsAgeRestrictedUser(children, activity);
            AppLovinPrivacySettings.setDoNotSell(false, activity);
        }
    }

    /*ApplovinZone*/
    public static void ApplovinZone(Activity activity, boolean children ){
        if (ZayviusAdsOnOff.ad_applovinzone) {
            AppLovinPrivacySettings.setHasUserConsent(true, activity);
            AppLovinPrivacySettings.setIsAgeRestrictedUser(children, activity);
        }
    }

    /*Unity*/
    public static void Unity(Activity activity){
        if (ZayviusAdsOnOff.ad_unity) {
            MetaData gdprMetaData = new MetaData(activity);
            gdprMetaData.set("gdpr.consent", true);
            gdprMetaData.commit();
        }
    }
}
