package com.zayvius.zs_ads;

import android.app.Activity;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

public class ZayviusAdsGDPR {
    public static ConsentInformation consentInformation;
    public static ConsentForm consentForm;
    public static void Admob(Activity activity){
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
}
