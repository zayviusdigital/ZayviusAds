package com.zayvius.zs_ads.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.google.android.gms.ads.MuteThisAdListener;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.zayvius.zs_ads.R;

import java.util.Objects;

public class ZayviusAdsNative {

    /*ApplovinMax*/
    private static MaxNativeAdLoader nativeAdLoader;
    private static MaxAd loadedNativeAd;
    private static MaxNativeAdLoader nativeAdLoadersmall;
    private static MaxAd loadedNativeAdsmall;

    /*Admob*/
    private static NativeAd nativeads;
    private static NativeAd nativeadssmall;

    /*Click Native*/
    public static boolean Disable_Click_ONOFF=false;
    public static boolean IntervalNativeMethod1=false;


    public static void NativeAds(Activity activity, FrameLayout frameLayout,boolean disable_click, boolean size_small){
        switch (ZayviusAdsMain.main_ad) {
            case "admob":
                if (size_small){
                    NativeAdmobSmall(activity,frameLayout,disable_click);
                }else {
                    NativeAdmob(activity,frameLayout,disable_click);
                }
                break;
            case "applovinmax":
                if (!ZayviusAdsIDApplovinMax.Nativex.equals("")){
                    if (size_small){
                        NativeApplovinMaxManualSmall(activity,frameLayout,disable_click);
                    }else {
                        NativeApplovinMaxManual(activity,frameLayout,disable_click);
                    }
                }
                break;
        }
    }
    /*Admob*/
    private static void NativeAdmob(Activity activity,FrameLayout frameLayout,boolean disable_click){
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
                if (disable_click){
                    populateNativeAdView_disable_click(nativeAd, adView);
                }else {
                    populateNativeAdView(nativeAd, adView);
                }
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
                        NativeApplovinMaxManual(activity,frameLayout,disable_click);
                    }
                }
                @Override
                public void onAdClicked() {
                    Disable_Click_ONOFF = true;
                    IntervalNativeMethod1 = true;
                }
            }).build();
            AdRequest adRequest = new AdRequest.Builder().build();
            adLoader.loadAd(adRequest);
        }
    }

    /*Admob Small*/
    private static void NativeAdmobSmall(Activity activity,FrameLayout frameLayout, boolean disable_click){
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
                if (disable_click){
                    populateNativeAdView_disable_click_small(nativeAd, adView);
                }else {
                    populateNativeAdViewsmall(nativeAd, adView);
                }
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
                        NativeApplovinMaxManualSmall(activity,frameLayout,disable_click);
                    }
                }
                @Override
                public void onAdClicked() {
                    Disable_Click_ONOFF = true;
                    IntervalNativeMethod1 = true;
                }
            }).build();
            AdRequest adRequest = new AdRequest.Builder().build();
            adLoader.loadAd(adRequest);
        }
    }


    /*ApplovinMax*/
    private static void NativeApplovinMaxManual(Activity activity,FrameLayout frameLayout, boolean disable_click){
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
                        NativeAdmob(activity,frameLayout,disable_click);
                    }
                }
                @Override
                public void onNativeAdClicked(MaxAd maxAd) {
                    super.onNativeAdClicked(maxAd);
                }
            });
            nativeAdLoader.loadAd(createNativeAdView(activity));
        }
    }

    /*ApplovinMax Small*/
    private static void NativeApplovinMaxManualSmall(Activity activity,FrameLayout frameLayout,boolean disable_click){
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
                        NativeAdmobSmall(activity,frameLayout,disable_click);
                    }
                }
                @Override
                public void onNativeAdClicked(MaxAd maxAd) {
                    super.onNativeAdClicked(maxAd);
                }
            });
            nativeAdLoadersmall.loadAd(createNativeAdViewsmall(activity));
        }
    }

    /*Admob*/
    private static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView( adView.findViewById(R.id.ad_media));
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }
    }

    private static void populateNativeAdView_disable_click(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView( adView.findViewById(R.id.ad_media));
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.findViewById(R.id.ad_media).setEnabled(false);
        adView.findViewById(R.id.ad_headline).setEnabled(false);
        adView.findViewById(R.id.ad_body).setEnabled(false);
        adView.findViewById(R.id.ad_call_to_action).setEnabled(false);
        adView.findViewById(R.id.ad_app_icon).setEnabled(false);
        adView.findViewById(R.id.ad_price).setEnabled(false);
        adView.findViewById(R.id.ad_stars).setEnabled(false);
        adView.findViewById(R.id.ad_store).setEnabled(false);
        adView.findViewById(R.id.ad_advertiser).setEnabled(false);

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }
    }

    private static void populateNativeAdViewsmall(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        //Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }




        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }
    }

    private static void populateNativeAdView_disable_click_small(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.findViewById(R.id.ad_headline).setEnabled(false);
        adView.findViewById(R.id.ad_body).setEnabled(false);
        adView.findViewById(R.id.ad_call_to_action).setEnabled(false);
        adView.findViewById(R.id.ad_app_icon).setEnabled(false);

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        //Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }




        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }
    }

    /*ApplovinMax*/
    private static MaxNativeAdView createNativeAdView(Activity context) {
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
    private static MaxNativeAdView createNativeAdViewsmall(Activity context) {
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
